package u.can.i.up.dl.utils.picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

    private PointF downPoint = new PointF();
    private PointF midPoint = new PointF();
    private float dist = 1f;

    private Matrix savedMatrix = new Matrix();
    private Matrix matrix = new Matrix();

    private ViewGroup layout;
    private int parentLayoutTop = -1;
    private int parentLayoutBottom = -1;
    private int parentLayoutLeft = -1;
    private int parentLayoutRight = -1;

    private int imageWidth = -1;
    private int imageHeight = -1;
    private int savedImageWidth = -1;
    private int savedImageHeight = -1;
    private int originWidth = -1;
    private int originHeight = -1;

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
        if (bitmap != null) {
            // center(true, true);
            imageWidth = bitmap.getWidth();
            imageHeight = bitmap.getHeight();
            Log.i(Variables.TINYYARD_LOG_TAG, "image width : " + imageWidth);
            Log.i(Variables.TINYYARD_LOG_TAG, "image height : " + imageHeight);
        }
        this.setImageMatrix(matrix);
        this.setOnTouchListener(new PictureViewTouchListener());


    }

    private class PictureViewTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent e) {
            if (parentLayoutBottom == -1) {
                parentLayoutBottom = layout.getBottom();
            }
            if (parentLayoutTop == -1) {
                parentLayoutTop = layout.getTop();
            }
            if (parentLayoutLeft == -1) {
                parentLayoutLeft = layout.getLeft();

            }
            if (parentLayoutRight == -1) {
                parentLayoutRight = layout.getRight();
            }
            switch (e.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    savedMatrix.set(matrix);
                    myMode = Variables.MODE_DRAG;
                    downPoint.set(e.getX(), e.getY());
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    dist = ImageUtil.spacing(e);
                    if (dist > 10f) {
                        savedMatrix.set(matrix);
                        savedImageWidth = imageWidth;
                        savedImageHeight = imageHeight;
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
                    if (myMode == Variables.MODE_ZOOM) {
                        adjustSize();
                        center(true, true, imageWidth, imageHeight, parentLayoutRight - parentLayoutLeft, parentLayoutBottom - parentLayoutTop);
                    }
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

    private boolean imageDrag(MotionEvent upEvent) {
        this.setScaleType(ScaleType.MATRIX);
        matrix.set(savedMatrix);
        float[] matrixValues = new float[9];
        matrix.getValues(matrixValues);
        float imageLeft = matrixValues[2], imageTop = matrixValues[5];
        float imageRight = imageLeft + this.imageWidth, imageBottom = imageTop + this.imageHeight;
        float moveDistanceX = upEvent.getX() - downPoint.x;
        float moveDistanceY = upEvent.getY() - downPoint.y;
        moveDistanceX = ImageUtil.boundaryCheck(this.imageWidth, imageLeft, imageRight, this.parentLayoutLeft, this.parentLayoutRight, moveDistanceX);
        moveDistanceY = ImageUtil.boundaryCheck(this.imageHeight, imageTop, imageBottom, this.parentLayoutTop, this.parentLayoutBottom, moveDistanceY);
        matrix.postTranslate(moveDistanceX, moveDistanceY);
        this.setImageMatrix(matrix);
        return true;
    }

    private boolean imageZoom(MotionEvent e) {
        this.setScaleType(ScaleType.MATRIX);
        if (e.getPointerCount() == 2) {
            float newDistance = ImageUtil.spacing(e);
            //    if (newDistance > 10f) {
            matrix.set(savedMatrix);
            this.imageWidth = savedImageWidth;
            this.imageHeight = savedImageHeight;
            float tScale = newDistance / dist;

            float[] matrixValues = new float[9];
            matrix.getValues(matrixValues);
            float pivotX = 0f;
            float pivotY = 0f;
            if (this.imageWidth > this.parentLayoutRight - this.parentLayoutLeft) {
                pivotX = midPoint.x;
            } else {
                pivotX = this.imageWidth / 2 + matrixValues[2];
            }
            if (this.imageHeight > this.parentLayoutBottom - this.parentLayoutTop) {
                pivotY = midPoint.y;
            } else {
                pivotY = this.imageHeight / 2 + matrixValues[5];
            }
//            if (matrixValues[0] < Variables.MIN_ZOOM_SCALE) {//最小缩放级别
//                this.setImageMatrix(savedMatrix);
//                return true;
//            } else if (matrixValues[0] > Variables.MAX_ZOOM_SCALE) {//最大缩放级别
//                this.setImageMatrix(savedMatrix);
//                return true;
//            }
            matrix.postScale(tScale, tScale, pivotX, pivotY);
            this.imageWidth *= tScale;
            this.imageHeight *= tScale;
            this.setImageMatrix(matrix);
        }
        //}
        return true;
    }

    private void adjustSize() {
        float tScale = 1;
        float[] matrixValues = new float[9];
        matrix.getValues(matrixValues);
        float savedScale = matrixValues[0];
//        matrix.set(savedMatrix);
        if (savedScale > Variables.MAX_ZOOM_SCALE) {
            tScale = Variables.MAX_ZOOM_SCALE;
        } else if (savedScale < Variables.MIN_ZOOM_SCALE) {
            tScale = Variables.MIN_ZOOM_SCALE;
            Log.i(Variables.TINYYARD_LOG_TAG, "scale before: " + matrixValues[0]);
        } else {
            return;
        }
        matrix.setScale(tScale, tScale, this.imageWidth / 2 + matrixValues[2], this.imageHeight / 2 + matrixValues[5]);
        this.imageWidth = (int) (originWidth * tScale);
        this.imageHeight = (int) (originHeight * tScale);
//        Log.i(Variables.TINYYARD_LOG_TAG, "image width: " + imageWidth);
        this.setImageMatrix(matrix);
    }

    private void center(boolean vertical, boolean horizontal, int imageWidth, int imageHeight, int parentLayoutWidth, int parentLayoutHeight) {
        float[] matrixValues = new float[9];
        this.matrix.getValues(matrixValues);
        float deltaX = 0, deltaY = 0;

        if (vertical) {
            float imageTop = matrixValues[5];
            float imageBottom = imageTop + imageHeight;
            deltaY = ImageUtil.setCenter(imageHeight, imageTop, imageBottom, parentLayoutHeight, this.parentLayoutTop, this.parentLayoutBottom);
        }

        if (horizontal) {
            float imageLeft = matrixValues[2];
            float imageRight = imageLeft + imageWidth;
            deltaX = ImageUtil.setCenter(imageWidth, imageLeft, imageRight, parentLayoutWidth, this.parentLayoutLeft, this.parentLayoutRight);
        }
        matrix.postTranslate(deltaX, deltaY);
        this.setImageMatrix(matrix);
    }

    public void setParentLayout(ViewGroup layout) {
        this.layout = layout;
    }

    public void setImageWidth(int imageWidth) {
        Log.i(Variables.TINYYARD_LOG_TAG, "image width: " + imageWidth);
        this.imageWidth = imageWidth;
        this.originWidth = imageWidth;
    }

    public void setImageHeight(int imageHeight) {
        Log.i(Variables.TINYYARD_LOG_TAG, "image height: " + imageHeight);
        this.imageHeight = imageHeight;
        this.originHeight = imageHeight;
    }
}
