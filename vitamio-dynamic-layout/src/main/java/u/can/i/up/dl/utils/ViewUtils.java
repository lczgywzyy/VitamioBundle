package u.can.i.up.dl.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by lczgywzyy on 2014/12/18.
 */
public class ViewUtils {

    private static final ViewUtils mViewUtils = new ViewUtils();

    private VideoView mVideoView = null;
    private int mPlayType = -1;
    private Activity mActivity = null;

    private ViewUtils(){}

    public static ViewUtils getInstance(){
        return mViewUtils;
    }
    public static VideoView getVideoView(){
        return getInstance().mVideoView;
    }

    /**@param mLayout 在指定界面中生成动态布局
     * */
    private void createVideoView(LinearLayout mLayout){
        if (!LibsChecker.checkVitamioLibs(mActivity))
            return;
        mLayout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        CenterLayout.LayoutParams clp = new CenterLayout.LayoutParams(vlp);
        mVideoView = new VideoView(mActivity);
        mVideoView.setLayoutParams(vlp);
        mLayout.addView(mVideoView);
        mVideoView.setMediaController(new MediaController(mActivity));
        mVideoView.requestFocus();
//        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
                updateVideoView();
            }
        });
    }

    /**@param mLayout 在指定界面中生成动态布局
     * */
    private void createVideoView(RelativeLayout mLayout){
        if (!LibsChecker.checkVitamioLibs(mActivity))
            return;
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        CenterLayout.LayoutParams clp = new CenterLayout.LayoutParams(vlp);
        mVideoView = new VideoView(mActivity);
        mVideoView.setLayoutParams(vlp);
        mLayout.addView(mVideoView);
//        mVideoView.setMediaController(new MediaController(mActivity));
        mVideoView.requestFocus();
//        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
                updateVideoView();
            }
        });
    }

    private void updateVideoView(){
        int videoHight = mVideoView.getVideoHeight();
        int videoWidth = mVideoView.getVideoWidth();
        DisplayMetrics metric = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int screenHeight = metric.heightPixels;   // 屏幕高度（像素）
        int screenWidth = metric.widthPixels;     // 屏幕宽度（像素）

        switch (mPlayType){
            case Variables.PLAY_TYPE_NAIVE_WITH_CONTROLLER:
                mVideoView.setMediaController(new MediaController(mActivity));
            case Variables.PLAY_TYPE_NAIVE_WITHOUT_CONTROLLER:
                break;
            case Variables.PLAY_TYPE_CENTER_WITH_CONTROLLER:
                mVideoView.setMediaController(new MediaController(mActivity));
            case Variables.PLAY_TYPE_CENTER_WITHOUT_CONTROLLER:
//                Log.i("=====VideoHeight======", "" + videoHight);
//                Log.i("=====VideoWidth======", "" + videoWidth);
//                Log.i("=====ScreenHeight======", "" + screenHeight);
//                Log.i("=====ScreenWidth======", "" + screenWidth);
                if (videoHight < screenHeight * videoWidth / screenWidth)
                    mVideoView.setY((screenHeight - videoHight)/2);
//                if (videoWidth < screenWidth * videoHight / screenHeight)
//                    mVideoView.setX((screenWidth - videoWidth)/2);
                break;
            default:
                break;
        }
    }

    /**@param path 视频的本地路径
     * @see u.can.i.up.dl.utils.Variables;
     * */
    private void playVideo(String path){
        mVideoView.setVideoPath(path);
    }

    /**@param tmpActivity 指定播放视频所在的界面
     * @param mLayout 在指定界面中生成动态布局，播放时默认布局
     * @param path 视频的本地路径
     *
     * 在指定页面中生成唯一动态布局，并且在该布局中播放视频
     * */
    public void playVideoInVideoView(Activity tmpActivity, LinearLayout mLayout, String path){
        playVideoInVideoView(tmpActivity, mLayout, path, Variables.PLAY_TYPE_NAIVE_WITH_CONTROLLER);
    }

    /**@param tmpActivity 指定播放视频所在的界面
     * @param mLayout 在指定界面中生成动态布局
     * @param path 视频的本地路径
     * @param playType 播放的形式，默认布局还是居中布局等等。
     * @see u.can.i.up.dl.utils.Variables
     *
     * 在指定页面中生成唯一动态布局，并且在该布局中播放视频
     * */
    public void playVideoInVideoView(Activity tmpActivity, LinearLayout mLayout, String path, int playType){
        mActivity = tmpActivity;
        mPlayType = playType;
        createVideoView(mLayout);
        playVideo(path);
    }

    /**@param tmpActivity 指定播放视频所在的界面
     * @param mLayout 在指定界面中生成动态布局
     * @param path 视频的本地路径
     * @param playType 播放的形式，默认布局还是居中布局等等。
     * @see u.can.i.up.dl.utils.Variables
     *
     * 在指定页面中生成唯一动态布局，并且在该布局中播放视频
     * */
    public void playVideoInVideoView(Activity tmpActivity, RelativeLayout mLayout, String path, int playType){
        mActivity = tmpActivity;
        mPlayType = playType;
        createVideoView(mLayout);
        playVideo(path);
    }
}
