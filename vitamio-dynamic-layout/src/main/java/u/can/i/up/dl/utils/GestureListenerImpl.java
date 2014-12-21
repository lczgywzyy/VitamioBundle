package u.can.i.up.dl.utils;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by lczgywzyy on 2014/12/19.
 */
public class GestureListenerImpl  extends GestureDetector.SimpleOnGestureListener{

    public GestureListenerImpl(){

    }

    /**
     * double click
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
//        if (currentLayoutParam.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
//            currentLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        } else {
//            currentLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        }
//        mVideoView.setLayoutParams(currentLayoutParam);
        return true;
    }

    /**
     * single click
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
//        controlView.setImageResource(R.drawable.video_play_1);
//        if (mVideoView.isPlaying()) {
//            Log.i("item id ", "" + controlView.getId());
//            mVideoView.pause();
//            controlView.setVisibility(View.VISIBLE);
//        } else {
//            mVideoView.start();
//            controlView.setVisibility(View.GONE);
//        }
        return true;
    }

    /**
     * move
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        isScollScreen = true;
//        if (Math.abs(distanceY) > Math.abs(distanceX)) {
//            float mOldX = e1.getX(), mOldY = e1.getY();
//            int mNewY = (int) e2.getRawY();
//            int windowWidth = screenWidth;
//            int windowHeight = screenHeight;
//            if (mOldX > windowWidth * 4.0 / 5) {// move at right
//                //onVolumeSlide((distanceY) / screenHeight);
//                onVolumeSlide((mOldY - mNewY) * 0.5f / windowHeight);
//            } else if (mOldX < windowWidth / 5.0) {// move at left
//                onBrightnessSlide((mOldY - mNewY) * 0.5f / windowHeight);
//            }
//        } else {
//            //        if(e2.getAction() ==  MotionEvent.ACTION_UP) {
//            Log.i("distanceX : ", "" + distanceX);
//            if (distanceX < 0) {
//                mVideoView.seekTo(mVideoView.getCurrentPosition() + 3000);
//            } else {
//                mVideoView.seekTo(mVideoView.getCurrentPosition() - 3000);
//            }
//        }
//        //    }
//        return super.onScroll(e1, e2, distanceX, distanceY);
        return true;
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
//        if (mVolume == -1) {
//            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//            if (mVolume < 0)
//                mVolume = 0;
//        }
//
//        int index = (int) (percent * mMaxVolume) + mVolume;
//        if (index > mMaxVolume)
//            index = mMaxVolume;
//        else if (index < 0)
//            index = 0;
//        // 变更声音
//        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
//        controlView.setImageResource(R.drawable.brightness_1);
//        controlView.setVisibility(View.VISIBLE);
//        //       if (mBrightness < 0) {
//        mBrightness = getWindow().getAttributes().screenBrightness;
//        if (mBrightness <= 0.00f)
//            mBrightness = 0.50f;
//        if (mBrightness < 0.01f)
//            mBrightness = 0.01f;
//        //       }
//        WindowManager.LayoutParams lpa = getWindow().getAttributes();
//        lpa.screenBrightness = mBrightness + percent;
//        if (lpa.screenBrightness > 1.0f)
//            lpa.screenBrightness = 1.0f;
//        else if (lpa.screenBrightness < 0.01f)
//            lpa.screenBrightness = 0.01f;
//        getWindow().setAttributes(lpa);
    }

}
