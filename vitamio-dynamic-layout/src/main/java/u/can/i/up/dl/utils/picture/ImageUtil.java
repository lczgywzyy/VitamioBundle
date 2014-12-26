package u.can.i.up.dl.utils.picture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import u.can.i.up.dl.utils.Variables;

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

    /**
     * 图片拖拽边际检查(包含X轴和Y轴)
     *
     * @param imageLength
     * @param imageStart
     * @param imageEnd
     * @param parentLayoutStart
     * @param parentLayoutEnd
     * @param moveDistance
     * @return
     */
    public static float boundaryCheck(float imageLength, float imageStart, float imageEnd, int parentLayoutStart, int parentLayoutEnd,
                                      float moveDistance) {
        if (imageLength <= parentLayoutEnd - parentLayoutStart) {//image is smaller than parentlayout
            if (moveDistance < 0 && imageStart + moveDistance < parentLayoutStart) {//move left or up
                moveDistance = parentLayoutStart - imageStart;
            } else if (moveDistance > 0 && imageEnd + moveDistance > parentLayoutEnd) {//move right or down
                moveDistance = parentLayoutEnd - imageEnd;
            }
        } else {//image is bigger than parentlayout
            if (moveDistance > 0 && imageStart + moveDistance > parentLayoutStart) {//move right or down
                moveDistance = parentLayoutStart - imageStart;
            } else if (moveDistance < 0 && imageEnd + moveDistance < parentLayoutEnd) {//move left or up
                moveDistance = parentLayoutEnd - imageEnd;
            }
        }
        return moveDistance;
    }

    /**
     *
     * @param imageLength
     * @param imageStart
     * @param imageEnd
     * @param parentLayoutLength
     * @param parentStart
     * @param parentEnd
     * @return
     */
    public static float setCenter(float imageLength, float imageStart, float imageEnd, int parentLayoutLength, int parentStart, int parentEnd) {
        float delta = 0;
        // 图片小于布局大小，则居中显示。大于布局，上方留空则往上移，下方留空则往下移
        if (imageLength < parentLayoutLength) {
            delta = (parentLayoutLength - imageLength) / 2 - imageStart;
        } else if (imageStart > parentStart) {
            delta = -imageStart;
        } else if (imageEnd < parentEnd) {
            delta = parentEnd - imageEnd;
        }
        return delta;
    }
}
