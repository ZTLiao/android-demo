package com.example.demo;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadZip extends AsyncTask<String, String, String> {

    String TAG = getClass().getSimpleName();

    private TextView tv;

    private String ret;

    public DownloadZip(TextView tv) {
        this.tv = tv;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(strings[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.i(TAG, "network error coee : " + conn.getResponseCode());
                ret = "code error";
                return ret;
            }
            inputStream = conn.getInputStream();
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/" + "zipSaveDir" + "/");
            if (!dir.exists()) {
                dir.mkdir();
            }
            String[] strArr = strings[0].split("/");
            String fullFilePath = dir + "/" + strArr[strArr.length - 1];
            File file = new File(fullFilePath);
            if (!file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file.getAbsolutePath());
            byte[] buffer = new byte[1024];
            int count;
            while (true) {
                count = inputStream.read(buffer);
                if (count == -1) {
                    break;
                }
                fileOutputStream.write(buffer, 0, count);
            }
            ret = "success";
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            ret = "fail";
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {

            }
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {

            }
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {

            }
        }
        return ret;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (ret.contains("success")) {
            tv.setText("download success");
        } else {
            tv.setText("download fail");
        }
    }
}
