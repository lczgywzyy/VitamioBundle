package u.can.i.up.vitamio_iptv_byr.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lczgywzyy on 2015/3/15.
 */
public class NetworkManager extends AsyncTask<Integer, Integer, String> {

    private Context mContext;
    private boolean isWIFI = false;

    Handler handler=new Handler();
    Runnable runnable=new Runnable(){
        @Override
        public void run() {
            //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
//            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    };

    public NetworkManager(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... params) {
        isWIFI = isWifi(mContext);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//        Log.i("UCanIUp", "" + isWIFI);
        if (!isWIFI){
            Toast.makeText(mContext, "请使用WIFI观看视频...", Toast.LENGTH_SHORT).show();
            handler.postDelayed(runnable, 3000);
        }
    }

    /**
     * make true current connect service is wifi
     * @param mContext
     * @return
     */
    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
