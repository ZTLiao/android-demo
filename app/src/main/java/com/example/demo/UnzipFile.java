package com.example.demo;

import android.os.AsyncTask;
import android.os.Environment;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile extends AsyncTask<String, String, String> {

    private TextView tv;

    private String ret;

    public UnzipFile(TextView tv) {
        this.tv = tv;
    }

    @Override
    protected String doInBackground(String... strings) {
        InputStream inputStream = null;
        ZipInputStream zipInputStream = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/" + "zipSaveDir" + "/");
            String fullFilePath = dir + "/nvm-setup.zip";
            File file = new File(fullFilePath);
            if (!file.exists()) {
                ret = "file not exists";
            }
            String unzipDir = dir.getAbsolutePath();
            inputStream = new FileInputStream(file);
            zipInputStream = new ZipInputStream(new BufferedInputStream(inputStream));
            byte[] buffer = new byte[1024];
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    File subDir = new File(unzipDir + "/" + fileName);
                    subDir.mkdirs();
                    continue;
                }
                int count;
                FileOutputStream fileOutputStream = new FileOutputStream(unzipDir + "/" + fileName);
                while ((count = zipInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.close();
                zipInputStream.closeEntry();
            }
            zipInputStream.close();
            ret = "unzip success";
        } catch (Exception e) {}
        return ret;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (ret.contains("success")) {
            tv.setText("unzip success");
        } else {
            tv.setText("unzip fail");
        }
    }
}
