package u.can.i.up.vitamio_iptv_byr.video;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import io.vov.vitamio.widget.VideoView;
import u.can.i.up.vitamio_iptv_byr.common.Variables;

/**
 * Created by lczgywzyy on 2014/12/21.
 */
public class VideoViewTouchListener implements View.OnTouchListener {

    private GestureDetector mGestureDetector;
    private GestureListenerImpl mGestureListenerImpl;

    private float downX;
    private float downY;
    private float firstMoveX;
    private float firstMoveY;
    private VideoView mVideoView;

    public VideoViewTouchListener(Activity activity, VideoView vv, ImageView iv, ProgressBar pb) {
        mVideoView = vv;
        mGestureListenerImpl = new GestureListenerImpl(activity, vv, iv, pb);
        mGestureDetector = new GestureDetector(activity, mGestureListenerImpl);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        if (VideoUtils.getInstance().isScrollScreen()) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                firstMoveX = event.getX();
                firstMoveY = event.getY();
                if(Math.abs(firstMoveX - downX) > Math.abs(firstMoveY - downY)) {
//                    Log.i("UCanIUp.vvt", "progress mode");
                    mGestureListenerImpl.setCurrMoveMode(Variables.MODE_PROGRESS);
                } else {
                    float windowWidth = mVideoView.getWidth();
                    if(downX > windowWidth / 2) {// move at right
//                        Log.i("UCanIUp.vvt", "volume mode");
                        mGestureListenerImpl.setCurrMoveMode(Variables.MODE_VOLUME);
                    } else if(downX <windowWidth / 2) {
//                        Log.i("UCanIUp.vvt", "brightness mode");
                        mGestureListenerImpl.setCurrMoveMode(Variables.MODE_BRIGHTNESS);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
//                Log.i("VideoViewTouchListener", "ACTION_CANCEL");
            case MotionEvent.ACTION_UP:
//                Log.i("VideoViewTouchListener", "ACTION_UP");
                mGestureListenerImpl.endGesture();
                break;
        }
//        }
        mGestureDetector.onTouchEvent(event);
        return true;
    }
}
