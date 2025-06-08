package com.example.demo;

import android.os.Message;

public class MyThread01 extends Thread {

    private MyHandler01 myHandler01;

    public MyThread01(MyHandler01 myHandler01) {
        this.myHandler01 = myHandler01;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Message message = Message.obtain();
            message.what = i;
            myHandler01.sendMessage(message);
        }
    }
}
