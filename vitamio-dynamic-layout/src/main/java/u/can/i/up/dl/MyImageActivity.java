package u.can.i.up.dl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import u.can.i.up.dl.utils.Variables;
import u.can.i.up.dl.utils.picture.ImageUtil;
import u.can.i.up.dl.utils.picture.UImageView;

public class MyImageActivity extends Activity {

    private String path;
    private UImageView myImagelView;
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
        myImagelView = addImagelView(Environment.getExternalStorageDirectory().getPath() + "/Pictures/2.jpeg", R.id.image_test);

//        mGestureDetector = new GestureDetector(this, new ImageGestureListener());
//        myImagelView.setOnTouchListener(new ImageOnTouchListener());
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
    private UImageView addImagelView(String imagePath, int parentLayoutId) {
        UImageView imageView = new UImageView(this);
        Bitmap bitmap = ImageUtil.getLoacalImageBitmap(imagePath);
        imageView.setImageBitmap(bitmap);
        if(bitmap != null) {
            imageView.setImageWidth(bitmap.getWidth());
            imageView.setImageHeight(bitmap.getHeight());
            RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //    imageLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            imageView.setLayoutParams(imageLayoutParams);
            //       imageView.setFitsSystemWindows(true);
            //  imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            RelativeLayout parentLayout = (RelativeLayout) findViewById(parentLayoutId);
            parentLayout.addView(imageView);

            imageView.setParentLayout(parentLayout);
            //Log.i(Variables.TINYYARD_LOG_TAG, "bottom :" + parentLayout.getBottom());
            // measureView(imageView);
            //       DisplayMetrics dm = this.getResources().getDisplayMetrics();
//        Log.i(Variables.TINYYARD_LOG_TAG, "screen height : " + dm.heightPixels);
//        Log.i(Variables.TINYYARD_LOG_TAG, "height : " + imageView.getMeasuredHeight());
            imageView.setId(childViewIdTag++);
            path = imagePath;


            //   imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            //   imageView.setImageMatrix(matrix);
            //matrix.set(imageView.getMatrix());
            // imageView.setVisibility(View.GONE);
            Log.i(Variables.TINYYARD_LOG_TAG, "add image view : " + imageView.getId());
        } else {
            Log.w(Variables.TINYYARD_LOG_TAG, "can not show picture : " + path);
        }
            return imageView;
    }

    private class ImageOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return true;
        }
    }

    private class ImageGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            if(myMode == Variables.MODE_NONE) {
//                if (myImagelView.getVisibility() != View.GONE) {
//                    Log.i(Variables.TINYYARD_LOG_TAG, "picture gone");
//                    myImagelView.setVisibility(View.GONE);
//                } else {
//                    Log.i(Variables.TINYYARD_LOG_TAG, "picture show");
//                    myImagelView.setVisibility(View.VISIBLE);
//                }
//            }
            return true;
        }
    }
}
