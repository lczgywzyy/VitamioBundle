package u.can.i.up.dl.utils.audio;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;


/**
 * Created by lczgywzyy on 2014/12/22.
 */
public class AudioUtils {

    private static final AudioUtils mAudoiUtils = new AudioUtils();
    private MediaPlayer mMediaPlayer;
    private Activity mActivity = null;

//    Handler mTimer = new Handler();
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            Log.i(AudioUtils.class.getName(), "当前位置(ms)：" + mMediaPlayer.getCurrentPosition());
//            seekAfterwards(100000);
//            mTimer.postDelayed(runnable, 5000);
//        }
//    };

    public static AudioUtils getInstance(){
        return mAudoiUtils;
    }

    public void playAudio(Activity ma, String audioPath){
        initAudio(ma);
        try {
            mMediaPlayer.setDataSource(audioPath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            Log.i(AudioUtils.class.getName(), "总时长(ms):" + mMediaPlayer.getDuration());
//            mTimer.post(runnable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void seekAfterwards(int mSecond){
        if (mMediaPlayer == null) {
            return;
        }
        int currentPosition = mMediaPlayer.getCurrentPosition();
        int targetPosition = (currentPosition + mSecond < mMediaPlayer.getDuration()) ? (currentPosition + mSecond): -1;
        switch (targetPosition){
            case -1:
                stopAudio();
                break;
            default:
                mMediaPlayer.seekTo(targetPosition);
                break;
        }
    }

    public void seekBackwards(int mSecond){
        if (mMediaPlayer == null) {
            return;
        }
        int currentPosition = mMediaPlayer.getCurrentPosition();
        int targetPosition = (currentPosition - mSecond > 0) ? (currentPosition - mSecond): 0;
        mMediaPlayer.seekTo(targetPosition);
    }

    public void setVolumeUp(int percent){

    }
    public void setVolumeDown(int percent){

    }

    public void stopAudio(){
//        mTimer.removeCallbacks(runnable);
        if (mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void initAudio(Activity ma){
        mActivity = ma;
        mMediaPlayer = new MediaPlayer();
    }
}
