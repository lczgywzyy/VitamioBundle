/*
 * Copyright (C) 2013 YIXIA.COM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package u.can.i.up.vitamio_iptv_byr;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import u.can.i.up.vitamio_iptv_byr.ads.MyAdsManager;
import u.can.i.up.vitamio_iptv_byr.network.NetworkManager;
import u.can.i.up.vitamio_iptv_byr.network.UpdateManager;
import u.can.i.up.vitamio_iptv_byr.video.VideoViewBuffer;

/**
 * List
 */
public class ByrMainActivity extends ListActivity {

    private Context mContext;
    private int Ads_Count = 0;
    Handler handler=new Handler();
    Runnable runnable=new Runnable(){
        @Override
        public void run() {
            //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
            (new NetworkManager(mContext)).execute();
            Ads_Count++;
            handler.postDelayed(this, 10000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        AdManager.getInstance(mContext).init("4b0bdceb503f38f4", "e10fcd0732f59311", false);

        handler.postDelayed(runnable, 1000);
        (new UpdateManager(this)).execute();
        (new MyAdsManager(this, SpotManager.ORIENTATION_PORTRAIT)).execute();
        setListAdapter(new SimpleAdapter(this, getData(), android.R.layout.simple_list_item_1, new String[] { "title" }, new int[] { android.R.id.text1 }));
    }

    protected List<Map<String, Object>> getData() {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();
        addItem(myData, "CCTV-1 高清", "http://tv6.byr.cn/hls/cctv1hd.m3u8");
        addItem(myData, "CCTV-3 高清", "http://tv6.byr.cn/hls/cctv3hd.m3u8");
        addItem(myData, "CCTV-5 高清", "http://tv6.byr.cn/hls/cctv5hd.m3u8");
        addItem(myData, "CCTV-5+ 高清", "http://tv6.byr.cn/hls/cctv5phd.m3u8");
        addItem(myData, "CCTV-6 高清", "http://tv6.byr.cn/hls/cctv6hd.m3u8");
        addItem(myData, "CCTV-8 高清", "http://tv6.byr.cn/hls/cctv8hd.m3u8");
        addItem(myData, "CHC 高清电影", "http://tv6.byr.cn/hls/chchd.m3u8");

        addItem(myData, "CCTV-1 综合", "http://tv6.byr.cn/hls/cctv1.m3u8");
        addItem(myData, "CCTV-2 财经", "http://tv6.byr.cn/hls/cctv2.m3u8");
        addItem(myData, "CCTV-3 综艺", "http://tv6.byr.cn/hls/cctv3.m3u8");
        addItem(myData, "CCTV-4 中文国际", "http://tv6.byr.cn/hls/cctv4.m3u8");
        addItem(myData, "CCTV-5 体育", "http://tv6.byr.cn/hls/cctv5.m3u8");
        addItem(myData, "CCTV-6 电影", "http://tv6.byr.cn/hls/cctv6.m3u8");
        addItem(myData, "CCTV-7 军事农业", "http://tv6.byr.cn/hls/cctv7.m3u8");
        addItem(myData, "CCTV-8 电视剧", "http://tv6.byr.cn/hls/cctv8.m3u8");
        addItem(myData, "CCTV-9 纪录", "http://tv6.byr.cn/hls/cctv9.m3u8");
        addItem(myData, "CCTV-10 科教", "http://tv6.byr.cn/hls/cctv10.m3u8");
        addItem(myData, "CCTV-11 戏曲", "http://tv6.byr.cn/hls/cctv11.m3u8");
        addItem(myData, "CCTV-12 社会与法", "http://tv6.byr.cn/hls/cctv12.m3u8");
        addItem(myData, "CCTV-13 新闻", "http://tv6.byr.cn/hls/cctv13.m3u8");
        addItem(myData, "CCTV-14 少儿", "http://tv6.byr.cn/hls/cctv14.m3u8");
        addItem(myData, "CCTV-15 音乐", "http://tv6.byr.cn/hls/cctv15.m3u8");
        addItem(myData, "CCTV-NEWS", "http://tv6.byr.cn/hls/cctv16.m3u8");

        addItem(myData, "北京卫视高清", "http://tv6.byr.cn/hls/btv1hd.m3u8");
        addItem(myData, "北京文艺高清", "http://tv6.byr.cn/hls/btv2hd.m3u8");
        addItem(myData, "北京体育高清", "http://tv6.byr.cn/hls/btv6hd.m3u8");
        addItem(myData, "北京纪实高清", "http://tv6.byr.cn/hls/btv11hd.m3u8");
        addItem(myData, "湖南卫视高清", "http://tv6.byr.cn/hls/hunanhd.m3u8");
        addItem(myData, "浙江卫视高清", "http://tv6.byr.cn/hls/zjhd.m3u8");
        addItem(myData, "江苏卫视高清", "http://tv6.byr.cn/hls/jshd.m3u8");
        addItem(myData, "东方卫视高清", "http://tv6.byr.cn/hls/dfhd.m3u8");
        addItem(myData, "安徽卫视高清", "http://tv6.byr.cn/hls/ahhd.m3u8");
        addItem(myData, "黑龙江卫视高清", "http://tv6.byr.cn/hls/hljhd.m3u8");
        addItem(myData, "辽宁卫视高清", "http://tv6.byr.cn/hls/lnhd.m3u8");
        addItem(myData, "深圳卫视高清", "http://tv6.byr.cn/hls/szhd.m3u8");
        addItem(myData, "广东卫视高清", "http://tv6.byr.cn/hls/gdhd.m3u8");
        addItem(myData, "天津卫视高清", "http://tv6.byr.cn/hls/tjhd.m3u8");
        addItem(myData, "湖北卫视高清", "http://tv6.byr.cn/hls/hbhd.m3u8");
        addItem(myData, "山东卫视高清", "http://tv6.byr.cn/hls/sdhd.m3u8");
        addItem(myData, "重庆卫视高清", "http://tv6.byr.cn/hls/cqhd.m3u8");

        addItem(myData, "北京卫视", "http://tv6.byr.cn/hls/btv1.m3u8");
        addItem(myData, "北京文艺", "http://tv6.byr.cn/hls/btv2.m3u8");
        addItem(myData, "北京科教", "http://tv6.byr.cn/hls/btv3.m3u8");
        addItem(myData, "北京影视", "http://tv6.byr.cn/hls/btv4.m3u8");
        addItem(myData, "北京财经", "http://tv6.byr.cn/hls/btv5.m3u8");
        addItem(myData, "北京体育", "http://tv6.byr.cn/hls/btv6.m3u8");
        addItem(myData, "北京生活", "http://tv6.byr.cn/hls/btv7.m3u8");
        addItem(myData, "北京青年", "http://tv6.byr.cn/hls/btv8.m3u8");
        addItem(myData, "北京新闻", "http://tv6.byr.cn/hls/btv9.m3u8");
        addItem(myData, "北京卡酷少儿", "http://tv6.byr.cn/hls/btv10.m3u8");

        addItem(myData, "深圳卫视", "http://tv6.byr.cn/hls/sztv.m3u8");
        addItem(myData, "安徽卫视", "http://tv6.byr.cn/hls/ahtv.m3u8");
        addItem(myData, "河南卫视", "http://tv6.byr.cn/hls/hntv.m3u8");
        addItem(myData, "陕西卫视", "http://tv6.byr.cn/hls/sxtv.m3u8");
        addItem(myData, "吉林卫视", "http://tv6.byr.cn/hls/jltv.m3u8");
        addItem(myData, "广东卫视", "http://tv6.byr.cn/hls/gdtv.m3u8");
        addItem(myData, "山东卫视", "http://tv6.byr.cn/hls/sdtv.m3u8");
        addItem(myData, "湖北卫视", "http://tv6.byr.cn/hls/hbtv.m3u8");
        addItem(myData, "广西卫视", "http://tv6.byr.cn/hls/gxtv.m3u8");
        addItem(myData, "河北卫视", "http://tv6.byr.cn/hls/hebtv.m3u8");
        addItem(myData, "西藏卫视", "http://tv6.byr.cn/hls/xztv.m3u8");
        addItem(myData, "内蒙古卫视", "http://tv6.byr.cn/hls/nmtv.m3u8");
        addItem(myData, "青海卫视", "http://tv6.byr.cn/hls/qhtv.m3u8");
        addItem(myData, "四川卫视", "http://tv6.byr.cn/hls/sctv.m3u8");
        addItem(myData, "江苏卫视", "http://tv6.byr.cn/hls/jstv.m3u8");
        addItem(myData, "天津卫视", "http://tv6.byr.cn/hls/tjtv.m3u8");
        addItem(myData, "山西卫视", "http://tv6.byr.cn/hls/sxrtv.m3u8");
        addItem(myData, "辽宁卫视", "http://tv6.byr.cn/hls/lntv.m3u8");
        addItem(myData, "厦门卫视", "http://tv6.byr.cn/hls/xmtv.m3u8");
        addItem(myData, "新疆卫视", "http://tv6.byr.cn/hls/xjtv.m3u8");
        addItem(myData, "东方卫视", "http://tv6.byr.cn/hls/dftv.m3u8");
        addItem(myData, "黑龙江卫视", "http://tv6.byr.cn/hls/hljtv.m3u8");
        addItem(myData, "湖南卫视", "http://tv6.byr.cn/hls/hunantv.m3u8");
        addItem(myData, "云南卫视", "http://tv6.byr.cn/hls/yntv.m3u8");
        addItem(myData, "江西卫视", "http://tv6.byr.cn/hls/jxtv.m3u8");
        addItem(myData, "福建东南卫视", "http://tv6.byr.cn/hls/dntv.m3u8");
        addItem(myData, "浙江卫视", "http://tv6.byr.cn/hls/zjtv.m3u8");
        addItem(myData, "贵州卫视", "http://tv6.byr.cn/hls/gztv.m3u8");
        addItem(myData, "宁夏卫视", "http://tv6.byr.cn/hls/nxtv.m3u8");
        addItem(myData, "甘肃卫视", "http://tv6.byr.cn/hls/gstv.m3u8");
        addItem(myData, "重庆卫视", "http://tv6.byr.cn/hls/cqtv.m3u8");
        addItem(myData, "兵团卫视", "http://tv6.byr.cn/hls/bttv.m3u8");
        addItem(myData, "旅游卫视", "http://tv6.byr.cn/hls/lytv.m3u8");

        return myData;
    }

    protected void addItem(List<Map<String, Object>> data, String name, String url) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("url", url);
        data.add(temp);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);
        Intent intent = new Intent(this, VideoViewBuffer.class);
        String tmpURL = (String)map.get("url");
//        Log.i("UCanIUp", tmpURL);

        Bundle bundleSimple = new Bundle();
        bundleSimple.putString("url", tmpURL);
        intent.putExtras(bundleSimple);

        startActivity(intent);
    }

    public void onBackPressed() {
        // 如果有需要，可以点击后退关闭插播广告。
        if (!SpotManager.getInstance(this).disMiss()) {
            // 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
            super.onBackPressed();
        }
    }
    @Override

    protected void onStop() {
        // 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
        SpotManager.getInstance(this).onStop();
        super.onStop();
    }
    @Override
    protected void onDestroy(){
        SpotManager.getInstance(this).onDestroy();
        super.onDestroy();
        System.exit(0);
    }
    @Override
    protected void onResume(){
        if(Ads_Count >= 6){
            (new MyAdsManager(this, SpotManager.ORIENTATION_PORTRAIT)).execute();
            Ads_Count = 0;
        }
        super.onResume();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
