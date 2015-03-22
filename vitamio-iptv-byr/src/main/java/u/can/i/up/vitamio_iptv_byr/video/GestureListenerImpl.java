package u.can.i.up.vitamio_iptv_byr.video;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import io.vov.vitamio.widget.VideoView;
import u.can.i.up.vitamio_iptv_byr.R;
import u.can.i.up.vitamio_iptv_byr.common.Variables;

/**
 * Created by lczgywzyy on 2014/12/19.
 */
public class GestureListenerImpl extends GestureDetector.SimpleOnGestureListener {
    private Activity mActivity;
    private VideoView mVideoView;
    private ImageView mControlView;
    private ProgressBar mProgressBar;
    private AudioManager mAudioManager;
    private int mVolume = -1;//当前声音
    private int mMaxVolume;//最大声音
    private float mBrightness = -1f;//当前亮度

    public GestureListenerImpl(Activity activity, VideoView vv, ImageView iv, ProgressBar pb) {
        mActivity = activity;
        mVideoView = vv;
        mControlView = iv;
        mProgressBar = pb;
        mAudioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }
    public int currMoveMode;
    public void setCurrMoveMode(int mode){
        currMoveMode = mode;
    }

    /**
     * double click
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
//        Log.i("GestureListenerImpl", "onDoubleTap");
        return true;
    }

    /**
     * single click
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
//        Log.i("UCanIUp", "onSingleTapConfirmed");
//        VideoUtils.getInstance().SingleTapConfirmed();
        return true;
    }

    /**
     * move
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        Log.i("UCanIUp", "onScroll");
        ScrollScreen(e1, e2, distanceX, distanceY);
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    public void ScrollScreen(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float mOldX = e1.getX(), mOldY = e1.getY();
        int mNewX = (int) e2.getX(), mNewY = (int) e2.getY();
        //   float rawDistanceX = mOldX - mNewX;
        float rawDistanceY = mOldY - mNewY;
        int windowHeight = mVideoView.getHeight();
        if (currMoveMode == Variables.MODE_VOLUME) {
//            Log.i("UCanIUp", "VolumeSlide(distanceY > 0, (rawDistanceY) / windowHeight);");
            VolumeSlide(distanceY > 0, (rawDistanceY) / windowHeight);
        } else if (currMoveMode == Variables.MODE_BRIGHTNESS) {
//            Log.i("UCanIUp", "BrightnessSlide(distanceY > 0, (rawDistanceY) / windowHeight);");
            BrightnessSlide(distanceY > 0, (rawDistanceY) / windowHeight);
        }
    }

    public void BrightnessSlide(boolean isUp, float percent) {
        if ((isUp && percent > 0) || (!isUp && percent < 0)) {
            mControlView.setImageResource(R.drawable.brightness_1);
            mControlView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            if (mBrightness < 0) {
                mBrightness = mActivity.getWindow().getAttributes().screenBrightness;
                if (mBrightness <= 0.00f)
                    mBrightness = 0.50f;
                if (mBrightness < 0.01f)
                    mBrightness = 0.01f;
            }
            WindowManager.LayoutParams lpa = mActivity.getWindow().getAttributes();
            lpa.screenBrightness = mBrightness + percent;
            if (lpa.screenBrightness > 1.0f)
                lpa.screenBrightness = 1.0f;
            else if (lpa.screenBrightness < 0.01f)
                lpa.screenBrightness = 0.01f;
            mActivity.getWindow().setAttributes(lpa);
            mProgressBar.setProgress((int) (lpa.screenBrightness * 100));
        }
    }

    public void VolumeSlide(boolean isUp, float percent) {
        if ((isUp && percent > 0) || (!isUp && percent < 0)) {
            if (percent > 0) {
//                Log.i("onVolumeSlide", "volume + " + percent);
                mControlView.setImageResource(R.drawable.volume_up_1);
            } else {
//                Log.i("onVolumeSlide", "volume - " + percent);
                mControlView.setImageResource(R.drawable.volume_down_1);
            }
            mControlView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
//            Log.i("UCanIUp----mVolume----", mVolume + "");
            if (mVolume < 0) {
                mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if (mVolume < 0)
                    mVolume = 0;
            }
            float index = (percent * mMaxVolume) + mVolume;
            if (index > mMaxVolume) {
                index = mMaxVolume;
            } else if (index < 0) {
                index = 0;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) index, 0);
            mProgressBar.setProgress((int) ((index / mMaxVolume) * 100));
        } else {
//            Log.i("onVolumeSlide", "invalid scroll : " + percent);
        }
    }


    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(boolean isUp, float percent) {
//        Log.i("UCanIUp", "onVolumeSlide");
//        VideoUtils.getInstance().VolumeSlide(isUp, percent);
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(boolean isUp, float percent) {
//        Log.i("UCanIUp", "onBrightnessSlide");
//        VideoUtils.getInstance().BrightnessSlide(isUp, percent);
    }

    public void endGesture() {
//        Log.i("endGesture", "end gesture");
        mVolume = -1;
        mBrightness = -1f;
//        // hide view
//        mDismissHandler.removeMessages(0);
//        mDismissHandler.sendEmptyMessageDelayed(0, 1000);
//        //   seekVedioRequest = 0;
//        setScrollScreenFlag(false);
    }
}
