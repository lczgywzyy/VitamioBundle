package u.can.i.up.dl;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import u.can.i.up.dl.utils.Variables;
import u.can.i.up.dl.utils.video.VideoUtils;

/**
 * Created by lczgywzyy on 2014/12/17.
 */
public class MyMainActivity extends Activity {
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_test);
        //initView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /*在当前的Activity中的布局(Layout)中，创建一个新的VideoView，并且把视频加载到指定的VideoView中；
        * */
        VideoUtils.getInstance().playVideoInVideoView(this, (RelativeLayout) findViewById(R.id.mytest), "/sdcard/Movies/test1.mp4", Variables.VIDEO_CENTER_WITHOUT_CONTROLLER);
    }

    /*TODO ！！！旋转屏幕的消息还有BUG，稍后解决。
    * */
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        //ViewUtils.getInstance().updateVideoView();
    }

    private void initView() {
        layout = (RelativeLayout) findViewById(R.id.mytest);
//        layout.setOrientation(LinearLayout.VERTICAL); // 设置Linearlayout 为垂直方向布局

        layout.addView(createView("小张"));
        layout.addView(createView("小林"));
        layout.addView(createView("小李"));
        layout.addView(createView("小黄"));
    }

    private View createView(String txt) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        // View view =LayoutInflater.from(this).inflate(R.layout.view_item, null);//也可以从XML中加载布局
        LinearLayout view = new LinearLayout(this);
        view.setLayoutParams(lp);//设置布局参数
        view.setOrientation(LinearLayout.HORIZONTAL);// 设置子View的Linearlayout// 为垂直方向布局

        //定义子View中两个元素的布局
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        tv1.setLayoutParams(vlp);//设置TextView的布局
        tv2.setLayoutParams(vlp2);
        tv1.setText("姓名: ");
        tv2.setText(txt);
        tv2.setPadding(calculateDpToPx(50), 0, 0, 0);//设置边距
        view.addView(tv1);//将TextView 添加到子View 中
        view.addView(tv2);//将TextView 添加到子View 中
        return view;
    }

    private int calculateDpToPx(int padding_in_dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (padding_in_dp * scale + 0.5f);
    }

}
