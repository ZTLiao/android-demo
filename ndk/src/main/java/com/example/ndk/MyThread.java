package com.example.ndk;

import android.os.Bundle;
import android.os.Message;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyThread extends Thread {

    private final MyHandler myHandler;

    private final String path;

    public MyThread(MyHandler myHandler, String path) {
        this.myHandler = myHandler;
        this.path = path;
    }

    private void getFiles(String path) {
        File[] files = new File(path).listFiles();
        for (int i = 0, len = files.length; i < len; i++) {
            File file = files[i];
            if (file.isFile()) {
                Message message = Message.obtain();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("fp", file.getAbsolutePath());
                message.setData(bundle);
                myHandler.handleMessage(message);
                try {
                    Thread.sleep(100);
                } catch (Exception ignored) {
                }
            } else if (file.getAbsolutePath().contains(".thumnail")) {
                getFiles(file.getAbsolutePath());
            }
        }
    }

    public void getFiles2(String path) {
        try {
            Class cls_File = Class.forName("java.io.File");
            Constructor constructor = cls_File.getConstructor(String.class);
            Object ins_file = constructor.newInstance(path);
            Method method_listFiles = cls_File.getMethod("listFiles");
            Method method_isFile = cls_File.getMethod("isFile");
            Method method_getAbsolutePath = cls_File.getMethod("getAbsolutePath");

            Class<?> cls_String = Class.forName("java.lang.String");
            Class<?> cls_CharSequence = Class.forName("java.lang.CharSequence");
            Method method_contains = cls_String.getMethod("contains", cls_CharSequence);

            Object[] files = (Object[]) method_listFiles.invoke(ins_file);
            Object obj_path = method_getAbsolutePath.invoke(ins_file);
            for (Object one : files) {
                Object obj_one_path = method_getAbsolutePath.invoke(one);
                Object obj_isfile = method_isFile.invoke(one);
            }
        } catch (Exception ignored) {

        }
    }

    @Override
    public void run() {
        super.run();
        getFiles(path);
    }

}
