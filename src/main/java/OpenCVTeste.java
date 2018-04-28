import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
public class OpenCVTeste {
    public static void main(String[] args){
        System.load("C:\\Program Files\\opencv\\build\\java\\x64\\opencv_java341.dll");
        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load("C:\\Program Files\\opencv\\build\\java\\x64\\haarcascade_frontalface_alt.xml");
        Mat image = Imgcodecs.imread("C:\\Program Files\\opencv\\build\\java\\x64\\teste.jpg");
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        System.out.println(faceDetections.toArray().length);
    }
}
