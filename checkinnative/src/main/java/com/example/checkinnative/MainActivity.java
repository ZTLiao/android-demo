package com.example.checkinnative;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkinnative.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'checkinnative' library on application startup.
    static {
        System.loadLibrary("checkinnative");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText et_user = binding.etUser;
        EditText et_pwd = binding.etPwd;
        Button btn_check = binding.btnCheck;
        Button btn_getCoin = binding.btnGetCoin;
        btn_check.setOnClickListener(v -> {
            boolean isUserExist = checkUser(et_user.getText().toString().trim(), et_pwd.getText().toString().trim());
            Toast.makeText(getBaseContext(), "isUserExist : " + isUserExist, Toast.LENGTH_LONG).show();
        });

        btn_getCoin.setOnClickListener(v -> {
            int coin = getCoin();
            Toast.makeText(getBaseContext(), "coin : " + coin, Toast.LENGTH_LONG).show();
        });
    }

    /**
     * A native method that is implemented by the 'checkinnative' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native boolean checkUser(String userName, String userPwd);

    public native int getCoin();
}