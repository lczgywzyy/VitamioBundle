package u.can.i.up.dl.utils.video;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import io.vov.vitamio.widget.VideoView;
import u.can.i.up.dl.utils.Variables;

/**
 * Created by lczgywzyy on 2014/12/21.
 */
public class VideoViewTouchListener implements View.OnTouchListener {

    private GestureDetector mGestureDetector;

    private float downX;
    private float downY;
    private float firstMoveX;
    private float firstMoveY;

    public VideoViewTouchListener(Activity activity) {
        mGestureDetector = new GestureDetector(activity, new GestureListenerImpl());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (VideoUtils.getInstance().isScrollScreen()) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        firstMoveX = event.getX();
                        firstMoveY = event.getY();
                        if(Math.abs(firstMoveX - downX) > Math.abs(firstMoveY - downY)) {
                            Log.i(Variables.TINYYARD_LOG_TAG, "progress mode");
                            VideoUtils.getInstance().setCurrMoveMode(Variables.MODE_PROGRESS);
                        } else {
                            float windowWidth = VideoUtils.getInstance().getVideoViewWidth();
                            if(downX > windowWidth / 2) {// move at right
                                Log.i(Variables.TINYYARD_LOG_TAG, "volume mode");
                                VideoUtils.getInstance().setCurrMoveMode(Variables.MODE_VOLUME);
                            } else if(downX <windowWidth / 2) {
                                Log.i(Variables.TINYYARD_LOG_TAG, "brightness mode");
                                VideoUtils.getInstance().setCurrMoveMode(Variables.MODE_BRIGHTNESS);
                            }
                        }
                        break;
                case MotionEvent.ACTION_CANCEL:
//                Log.i("VideoViewTouchListener", "ACTION_CANCEL");
                case MotionEvent.ACTION_UP:
//                Log.i("VideoViewTouchListener", "ACTION_UP");
                    VideoUtils.getInstance().endGesture();
                    break;
            }
        }
        mGestureDetector.onTouchEvent(event);
        return true;
    }
}
