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
import android.widget.ImageView;
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
        myImagelView = addImagelView(Environment.getExternalStorageDirectory().getPath() + "/Pictures/1.jpg", R.id.image_test);
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
        path = imagePath;
        Bitmap bitmap = ImageUtil.getLoacalImageBitmap(imagePath);
        UImageView imageView = new UImageView(this);
        imageView.setImageBitmap(bitmap);
        RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        imageLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//        imageLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        imageLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
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
