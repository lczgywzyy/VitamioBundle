package u.can.i.up.dl.utils.picture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.view.MotionEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by tinyyard on 2014/12/22.
 */
public class ImageUtil {

    /**
     * load local picture
     *
     * @param path
     * @return
     */
    public static Bitmap getLoacalImageBitmap(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * compute distance between 2 points
     */
    public static float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * compute mid point between 2 points
     */
    public static PointF getMidPoint(MotionEvent event) {
        PointF point = new PointF();
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
        return point;
    }
}
