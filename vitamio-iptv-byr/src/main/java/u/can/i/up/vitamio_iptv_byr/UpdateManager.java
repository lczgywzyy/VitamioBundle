package u.can.i.up.vitamio_iptv_byr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lczgywzyy on 2015/3/13.
 */
public class UpdateManager extends AsyncTask<Integer, Integer, String> {

    private Context mContext;
    private boolean interceptFlag = false;
    private ProgressBar mProgress;
    private int progress;
    private Thread downLoadThread;
    private String serverip = "192.168.2.138";
    private String apkUrl = "http://" + serverip + "/UCanIUp/byrtv/update.apk";
    private String versoinUrl = "http://" + serverip + "/UCanIUp/byrtv/version.xml";
    private static final String savePath = "/sdcard/UCanIUp/";
    private static final String saveFileName = savePath + "update.apk";
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private int ServerVersionCode = -1;
    private String ServerVersionName = "";
    private String ServerVersionInfo = "";

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    break;
                case DOWN_OVER:
                    installApk();
                    break;
                default:
                    break;
            }
        };
    };

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    public void checkUpdate() {
        if (isUpdate()) {
            // 显示提示对话框
            showNoticeDialog();
        }
    }

    /**
     * 检查软件是否有更新版本
     *
     * @return
     */
    private boolean isUpdate() {
        if (getVersionCode(mContext) <  ServerVersionCode){
            return true;
        }else {
            return false;
        }

//        // 获取当前软件版本
//        int versionCode = getVersionCode(mContext);
//        // 把version.xml放到网络上，然后获取文件信息
//        InputStream inStream = ParseXmlService.class.getClassLoader().getResourceAsStream("version.xml");
//        // 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
//        ParseXmlService service = new ParseXmlService();
//        try {
//            mHashMap = service.parseXml(inStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (null != mHashMap) {
//            int serviceCode = Integer.valueOf(mHashMap.get("version"));
//            // 版本判断
//            if (serviceCode > versionCode) {
//                return true;
//            }
//        }
//        return false;

//        pull的XML解析模式，但是HttpURLConnection不能出现在主线程中
//        try {
//            URL url = new URL(xmlUrl);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = urlConnection.getInputStream();
//            XmlPullParser parser = Xml.newPullParser();
//            parser.setInput(inputStream, "UTF-8");
//            int eventType = parser.getEventType();
//            while(eventType != XmlPullParser.END_DOCUMENT)  {
//                switch (eventType){
//                    case XmlPullParser.START_DOCUMENT: //开始读取XML文档
//                        //实例化集合类
//                        break;
//                    case XmlPullParser.START_TAG://开始读取某个标签
//                        if("version".equals(parser.getName())) {
//                            Log.i("UCanIUp", "-->" + parser.nextText());
//                            Log.i("UCanIUp", "-->" + parser.getAttributeValue(0));
//                            Log.i("UCanIUp", "-->" + parser.getAttributeValue(0));
//                            //通过getName判断读到哪个标签，然后通过nextText()获取文本节点值，或通过getAttributeValue(i)获取属性节点值
//                        }
//                        break;
//                    case XmlPullParser.END_TAG://读完一个Person，可以将其添加到集合类中
//                        break;
//                }
//                parser.next();
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }

//        int versionCode = getVersionCode(mContext);
//        int newVerCode = -1;
//        try {
//            String verjson = NetworkTool.getContent(versoinUrl);
//            JSONArray array = new JSONArray(verjson);
//
//            if (array.length() > 0) {
//                JSONObject obj = array.getJSONObject(0);
//                try {
//                    newVerCode = Integer.parseInt(obj.getString("verCode"));
//                } catch (Exception e) {
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (newVerCode > versionCode) {
//            Log.i("UCanIUp", "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//            return true;
//        }else {
//            return false;
//        }
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("u.can.i.up.vitamio_iptv_byr", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.soft_update_title);
        builder.setMessage(ServerVersionInfo);
        // 更新
        builder.setPositiveButton(R.string.soft_update_updatebtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 显示下载对话框
                showDownloadDialog();
            }
        });
        // 稍后更新
        builder.setNegativeButton(R.string.soft_update_later, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件版本更新");

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress, null);
        mProgress = (ProgressBar)v.findViewById(R.id.progress);

        builder.setView(v);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
            }
        });
        Dialog downloadDialog = builder.create();
        downloadDialog.show();
        downloadApk();
    }
    private void downloadApk(){
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }
    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if(!file.exists()){
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do{
                    int numread = is.read(buf);
                    count += numread;
                    progress =(int)(((float)count / length) * 100);
                    //更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if(numread <= 0){
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf,0,numread);
                }while(!interceptFlag);//点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }

        }
    };
    private void installApk(){
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File("/sdcard/UCanIUp/update.apk")), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
        mContext.startActivity(intent);
    }

    @Override
    protected String doInBackground(Integer... params) {
//                pull的XML解析模式，但是HttpURLConnection不能出现在主线程中
        try {
            Log.i("UCanIUp", "------------------------>");
            URL url = new URL(versoinUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, "UTF-8");
            int eventType;
            while((eventType = parser.getEventType()) != XmlPullParser.END_DOCUMENT)  {
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT: //开始读取XML文档
                        //实例化集合类
                        break;
                    case XmlPullParser.START_TAG://开始读取某个标签
                        //通过getName判断读到哪个标签，然后通过nextText()获取文本节点值，或通过getAttributeValue(i)获取属性节点值
                        if("VersionCode".equals(parser.getName())) {
                            ServerVersionCode = Integer.parseInt(parser.nextText().toString());
                            Log.i("UCanIUp", "ServerVersionCode-->" + ServerVersionCode);
                        }
                        if("VersionName".equals(parser.getName())) {
                            ServerVersionName = parser.nextText();
                            Log.i("UCanIUp", "ServerVersionName-->" + ServerVersionName);
                        }
                        if("VersionInfo".equals(parser.getName())) {
                            ServerVersionInfo = parser.nextText();
                            Log.i("UCanIUp", "ServerVersionInfo-->" + ServerVersionInfo);
                        }
                        break;
                    case XmlPullParser.END_TAG://读完一个Person，可以将其添加到集合类中
                        break;
                }
                parser.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
    @Override
    protected void onPostExecute(String result) {
        checkUpdate();
    }
}
