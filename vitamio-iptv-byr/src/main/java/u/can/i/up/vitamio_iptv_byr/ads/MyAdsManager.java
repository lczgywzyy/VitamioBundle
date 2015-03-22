package u.can.i.up.vitamio_iptv_byr.ads;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotManager;

/**
 * Created by lczgywzyy on 2015/3/16.
 */
public class MyAdsManager extends AsyncTask<Integer, Integer, String> {
    private Context mContext;
    private int adsORIENTATION = -1;

    public MyAdsManager(Context c, int ori){
        mContext = c;
        adsORIENTATION = ori;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(Integer... params) {
        SpotManager.getInstance(mContext).loadSpotAds();
        SpotManager.getInstance(mContext).setSpotOrientation(adsORIENTATION);
        SpotManager.getInstance(mContext).setAnimationType(SpotManager.ANIM_ADVANCE);
        return null;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        SpotManager.getInstance(mContext).showSpotAds(mContext);
    }
}
