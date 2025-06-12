package com.example.ndk;

import android.os.Bundle;
import android.os.Message;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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

    public void getFiles2(Object path) {
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
                if ((boolean) obj_isfile) {
                    Class cls_Message = Class.forName("android.os.Message");
                    Method method_obtain = cls_Message.getMethod("obtain");
                    Object ins_message = method_obtain.invoke(cls_Message);
                    Field field_what = cls_Message.getField("what");
                    field_what.set(ins_message, 1);

                    Class<?> cls_Bundle = Class.forName("android.os.Bundle");
                    Constructor<?> constructor_Bundle = cls_Bundle.getConstructor();
                    Object ins_Bundle = constructor_Bundle.newInstance();
                    Method method_putString = cls_Bundle.getMethod("putString", cls_String, cls_String);
                    method_putString.invoke(ins_Bundle, "fp", obj_one_path);

                    Method method_setData = cls_Message.getMethod("setData", cls_Bundle);
                    method_setData.invoke(ins_message, ins_Bundle);

                    myHandler.sendMessage((Message) ins_message);
                    try {
                        Thread.sleep(100);
                    } catch (Exception ignored) {
                    }
                } else if (!(boolean) (method_contains.invoke(obj_one_path, ".thumnail"))) {
                    getFiles2(obj_one_path);
                }
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
