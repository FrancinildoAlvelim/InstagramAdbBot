import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Run {



    public static void main(String[] args) throws InterruptedException {
        boolean calibrate = false;

        if(calibrate){
            while (true){
                availableToFollow(calibrate);
            }
        }else {
            int i = 0;
            do {
                if(availableToFollow(calibrate)){
                    roll();
                    click();
                    System.out.println("follow: "+i );
                    i++;
                }else {
                    roll();
                    System.out.println("just roll");
                }

               // Thread.sleep(3000);
            }while (i<300);
        }
    }

    private static BufferedImage cropImage(BufferedImage src, Rectangle rect) {
        BufferedImage dest = src.getSubimage(460, 350, rect.width, rect.height);
        return dest;
    }

    private static boolean availableToFollow(boolean calibrate){

        try {
            Process process = Runtime.getRuntime().exec("cmd /c adb shell /system/bin/screencap -p /sdcard/img.png &  adb pull /sdcard/img.png C:\\insta\\img.png & adb shell rm /sdcard/img.png");
            process.waitFor();
            File targetFile = new File("c:\\insta\\img.png");
            BufferedImage in = ImageIO.read(targetFile);
            BufferedImage crop = cropImage(in, new Rectangle(0, 0, 230, 68));

            if(calibrate){
                File outputfile = new File("c:\\insta\\crop.png");
                ImageIO.write(crop, "png", outputfile);
            }


            boolean canProced = false;
            for (int i = 0; i < crop.getWidth(); i++) {
                for (int j = 0; j < crop.getHeight(); j++) {
                    int clr=  crop.getRGB(i,j);
                    int  red   = (clr & 0x00ff0000) >> 16;
                    int  green = (clr & 0x0000ff00) >> 8;
                    int  blue  =  clr & 0x000000ff;

                    if(red == 56 && green == 151 && blue == 240){
                        canProced = true;
                        break;
                    }
                }
            }
            new Thread(targetFile::delete).start();
            return canProced;

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static void roll(){
        try {
            Runtime.getRuntime().exec("C:\\Users\\frana\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe shell input roll 0 1").waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
    private static void click(){
        try {
            Runtime.getRuntime().exec("C:\\Users\\frana\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe shell input tap 600 300").waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
