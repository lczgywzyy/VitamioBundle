package u.can.i.up.dl.utils.picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import u.can.i.up.dl.utils.Variables;

/**
 * Created by tinyyard on 2014/12/22.
 */
public class UImageView extends ImageView {

    /**
     * 位图对象
     */
    private Bitmap bitmap = null;
    /**
     * 屏幕的分辨率
     */
    private DisplayMetrics dm;

    private GestureDetector mGestureDetector;

    private int myMode = Variables.MODE_NONE;

    private PointF prev = new PointF();
    private PointF midPoint = new PointF();
    private float dist = 1f;

    private Matrix savedMatrix = new Matrix();
    private Matrix matrix = new Matrix();

    public UImageView(Context context) {
        super(context);
        setupView();
    }

    public UImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    public void setupView() {

        Context context = getContext();
        mGestureDetector = new GestureDetector(context, new PictureGestureListener());
        //获取屏幕分辨率,需要根据分辨率来使用图片居中
        dm = context.getResources().getDisplayMetrics();

        //根据MyImageView来获取bitmap对象
        BitmapDrawable bd = (BitmapDrawable) this.getDrawable();
        if (bd != null) {
            bitmap = bd.getBitmap();
        }

        //设置ScaleType为ScaleType.MATRIX，这一步很重要
        this.setScaleType(ScaleType.MATRIX);
        this.setImageBitmap(bitmap);

        //bitmap为空就不调用center函数
//        if (bitmap != null) {
//            center(true, true);
//        }
        this.setImageMatrix(matrix);
        this.setOnTouchListener(new PictureViewTouchListener());


    }

    private class PictureViewTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent e) {
            switch (e.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    savedMatrix.set(matrix);
                    myMode = Variables.MODE_DRAG;
                    prev.set(e.getX(), e.getY());
                    Log.i(Variables.TINYYARD_LOG_TAG, "down point : " + e.getX() + ":" + e.getY());
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    dist = ImageUtil.spacing(e);
                    if (dist > 10f) {
                        savedMatrix.set(matrix);
                        midPoint = ImageUtil.getMidPoint(e);
                        myMode = Variables.MODE_ZOOM;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    switch (myMode) {
                        case Variables.MODE_DRAG:
                            imageDrag(e);
                            break;
                        case Variables.MODE_ZOOM:
                            imageZoom(e);
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    myMode = Variables.MODE_NONE;
                    break;
            }
            mGestureDetector.onTouchEvent(e);
            return true;
        }
    }

    private class PictureGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (myMode == Variables.MODE_NONE) {

            }
            return true;
        }


    }

    protected void center(boolean horizontal, boolean vertical) {
        Matrix m = new Matrix();
        m.set(matrix);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = 0, deltaY = 0;

        if (vertical) {
            // 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下方留空则往下移
            int screenHeight = dm.heightPixels;
            if (height < screenHeight) {
                deltaY = (screenHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < screenHeight) {
                deltaY = this.getHeight() - rect.bottom;
            }
        }

        if (horizontal) {
            int screenWidth = dm.widthPixels;
            if (width < screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < screenWidth) {
                deltaX = screenWidth - rect.right;
            }
        }
        matrix.postTranslate(deltaX, deltaY);
    }

    private boolean imageDrag(MotionEvent e) {
        //     Log.i(LOGTAG, "move point : " + e.getX() + ":" + e.getY());
        matrix.set(savedMatrix);
        matrix.postTranslate(e.getX() - prev.x, e.getY() - prev.y);
        this.setImageMatrix(matrix);
        return true;
    }

    private boolean imageZoom(MotionEvent e) {
        Log.i(Variables.TINYYARD_LOG_TAG, "image zoom");
        if (e.getPointerCount() == 2) {
            float newDistance = ImageUtil.spacing(e);
            //    if (newDistance > 10f) {
            matrix.set(savedMatrix);
            float tScale = newDistance / dist;
            Log.i(Variables.TINYYARD_LOG_TAG, "image zoom scale : " + tScale);
            matrix.postScale(tScale, tScale, midPoint.x, midPoint.y);
            this.setImageMatrix(matrix);
        }
        //}
        return true;
    }
}
