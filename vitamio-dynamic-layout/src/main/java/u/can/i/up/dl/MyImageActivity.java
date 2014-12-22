package u.can.i.up.dl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import u.can.i.up.dl.utils.Variables;

public class MyImageActivity extends ActionBarActivity {

    private String path;
    private ImageView myImagelView;
    private int childViewIdTag = 5;
    private GestureDetector mGestureDetector;

    private int myMode = Variables.MODE_NONE;

    private PointF prev = new PointF();
    private Matrix savedMatrix = new Matrix();
    private Matrix matrix = new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_test_layout);
        myImagelView = addImagelView(Environment.getExternalStorageDirectory().getPath() + "/Pictures/1.jpg", R.id.image_test);
        mGestureDetector = new GestureDetector(this, new ImageGestureListener());
        myImagelView.setOnTouchListener(new ImageOnTouchListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        myImagelView.setVisibility(View.VISIBLE);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param imagePath
     * @param parentLayoutId
     * @return
     */
    private ImageView addImagelView(String imagePath, int parentLayoutId) {
        path = imagePath;
        Bitmap bitmap = getLoacalBitmap(imagePath);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        imageLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imageLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        imageView.setLayoutParams(imageLayoutParams);
        imageView.setId(childViewIdTag++);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);

        RelativeLayout parentLayout = (RelativeLayout) findViewById(parentLayoutId);
        parentLayout.addView(imageView);

        matrix.set(imageView.getMatrix());
        // imageView.setVisibility(View.GONE);
        Log.i(Variables.TINYYARD_LOG_TAG, "add image view : " + imageView.getId());
        return imageView;

    }

    /**
     * load local picture
     *
     * @param url
     * @return
     */
    private static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class ImageOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent e) {
            switch (e.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    savedMatrix.set(matrix);
                    myMode = Variables.MODE_DRAG;
                    prev.set(e.getX(), e.getY());
                    Log.i(Variables.TINYYARD_LOG_TAG, "down point : " + e.getX() + ":" + e.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    switch (myMode) {
                        case Variables.MODE_DRAG:
                            imageDrag(e);
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

    private class ImageGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageLayoutParams.addRule(RelativeLayout.SYSTEM_UI_FLAG_FULLSCREEN);
            imageLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//            if (myImagelView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//                myImagelView.setScaleType(ImageView.ScaleType.FIT_XY);
//            } else {
//                myImagelView.setScaleType(ImageView.ScaleType.CENTER);
//            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(myMode == Variables.MODE_NONE) {
                if (myImagelView.getVisibility() != View.GONE) {
                    Log.i(Variables.TINYYARD_LOG_TAG, "picture gone");
                    myImagelView.setVisibility(View.GONE);
                } else {
                    Log.i(Variables.TINYYARD_LOG_TAG, "picture show");
                    myImagelView.setVisibility(View.VISIBLE);
                }
            }
            return true;
        }


    }

    private boolean imageDrag(MotionEvent e) {
        if (myImagelView == null) {
            Log.e(Variables.TINYYARD_LOG_TAG, "null image view");
            return false;
        }
        float mX = e.getX();
        float mY = e.getY();
        Log.i(Variables.TINYYARD_LOG_TAG, "down point2 : " + prev.x + ":" + prev.x);
   //     Log.i(LOGTAG, "move point : " + e.getX() + ":" + e.getY());
        matrix.set(savedMatrix);
        matrix.postTranslate(e.getX() - prev.x, e.getY() - prev.y);
        myImagelView.setImageMatrix(matrix);
        return true;
    }
}
