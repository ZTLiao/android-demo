package com.example.appclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {


    public void writeFileData(String filename, String content) {
        try {
            FileOutputStream fos = this.openFileOutput(filename, MODE_PRIVATE);
            byte[] bytes = content.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] ^ (byte) 0x24);
            }
            fos.write(bytes);
            fos.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public String readFileData(String fileName) {
        String result = "";
        try {
            FileInputStream fis = openFileInput(fileName);
            int lenght = fis.available();
            byte[] buffer = new byte[lenght];
            fis.read(buffer);
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = (byte) (buffer[i] ^ (byte) 0x24);
            }
            result = new String(buffer, "UTF-8");
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_state = findViewById(R.id.tv_stat);
        EditText et_username = findViewById(R.id.et_userName);
        EditText et_pwd = findViewById(R.id.et_pwd);
        Button btn_login = findViewById(R.id.btn_login);
        String saveFile = "loginState.txt";

        String readData = readFileData(saveFile);
        if (readData.equals("")) {
            tv_state.setText("当前状态：未登录");
        } else {
            tv_state.setText("当前状态：" + readData + " 已登录");
            Intent it = new Intent(this, MainActivity2.class);
            startActivity(it);
            this.finish();
        }
        //-rw-rw---- 1 u0_a59 u0_a59

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readData.equals("")) {
                    String pwd = et_pwd.getText().toString().trim();
                    String uname = et_username.getText().toString().trim();
                    if (pwd.equals(uname) && pwd.equals("aaaa")) {
                        writeFileData(saveFile, uname);
                        tv_state.setText("当前状态：" + readData + " 已登录");
                        Intent it = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(it);
                        MainActivity.this.finish();
                    } else {
                        tv_state.setText("当前状态：用户名或密码不正确");
                    }

                } else {
                    tv_state.setText("当前状态：用户名或密码不正确");
                }
            }
        });

//        tv_state.setText(stringFromJNI());
    }

}