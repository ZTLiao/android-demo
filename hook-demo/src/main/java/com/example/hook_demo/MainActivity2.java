package com.example.hook_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySession;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

public class MainActivity2 extends ComponentActivity {

    private static final String TAG = MainActivity2.class.getSimpleName();

    private TextView simpleText;

    private Button submitRequestBtn;

    static {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        simpleText = findViewById(R.id.simpleText);
        submitRequestBtn = findViewById(R.id.submitRequestBtn);
        submitRequestBtn.setOnClickListener(v -> {
            this.submitRequest(this.getApplicationContext());
        });
    }

    private void submitRequest(Context context) {
        try {
            SpdyAgent spdyAgent = SpdyAgent.getInstance(context, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
            spdyAgent.switchAccsServer(1);
//            long spdyAgentPtr = spdyAgent.initAgent(0, 1, 0);
//            Log.i(TAG, "spdyAgentPtr : " + spdyAgentPtr);
//            int result = spdySession.submitRequestN(
//                    514603600992L,
//                    "https://guide-acs.m.taobao.com/gw/mtop.taobao.mloginservice.smssend/1.0/",
//                    (byte) 3,
//                    new String[]{ ":host","guide-acs.m.taobao.com","x-sgext","JBSKjZkXCb588oup8lpFHo27vbq6sq67vbO6u667v6muu7q9uri1vrS4uKm9vLrsubq9uujutOzo67i6rru9qby4rrq9qb26vb2uvq67rriuu667rruuuK66rr%2BuuK66rrquuq66rrquuq66rqnoqb2pvanpue7svbquur26vbquuq6%2FtL6uuq6pvqm8qb6pvan35Nrcrrquqq2qtKqtqr2pvdXM7f7IzP3MzszItcvF%2Fcy30rzSu6u4q7%2Brs6vpq%2B6ru7zVtNW8p7yn0uvSuKu%2Bq7261e%2FVvL27p7ynvae%2BuqC%2Bvae4p7%2BsvKy9rLysvKe8p7y9ur2%2Fv7S4vrurvqu9v9Xp1brhtOfduPXbtfzBvtLv0rjMy8zLzMPMyOrO1cvMy8zL6svKy8y3sNXr1b6nvL%2BguKC4tae4s9K7vdWp8Mff%2BNzC58jkv8vX%2Fs%2Fiy87hzrnp4u%2FFsv7Lz%2BPA%2F87J98e9u%2BTQwM3qy8zLvcLO6bjkxtD34MPHzMvM09f9zMvIx7XYx8m12MfO38v33sS6wOy689rA3rPBz9TlzMvJx%2BDyws7D3dr9393a%2FdzJ3OXV897LzMvFoczLzOXU0%2B7G6svSu7%2FVuqe5uqCztL7Su77VzO3My8zLzMvMy8zLzMvMy8zLzMvMy8zLzMvMy9K7udXM7czPzNvMy8%2FLzMvM6b397se77szbzMvMu9%2FsuczUy8zLzMvl%2BP7IzMC5y8vt%2Ftzgy8zF7%2F3MvszJ7sjOvrjf1aHP3cLL3svP%2FczLzMvO78zL1M3My8nQzNva37nz3vL0%2FcDLx8vEt6vb2cT68P26z8vMy7DVvLzSutK7tNW6rKC7q7qjur26vay5rL2svay9rL2svdW86NK4ur%2BgutK77tW8vbusvay%2Buqu4tLi7rL%2Bzvuuru7y466y1u6u7q7K7rLqzv6y81bzu0ru0s6u70rvo1b%2B%2Btae%2FvrXVvOzSy9zPzMvMy7DVv7rSuqC8udW%2FudK7uryruqOyub21rLisuay5rLisvay9rLysvKy8vtK4udW8p72nubOgsqC7v6e9p7inv7qguLSnv7rSuLrVvL27rL2ktLu7squ7uay0rLy6q7Ors6u4tLO%2Fur%2B9vLu4rL%2B6q76ruKu6q7rSuLXVzL25xdXYwLfSuLTVzNLM7%2BGl3LLL5bzzudLsubnl%2BsjkuMbauunAt9I%3D","x-social-attr","2","x-sign","azYBCM007xAAi%2BcqCj2lISihxXLX6%2Bcr6hyq3XC1KAK25ccytj9U4wCthwI74ZGu5ySZUKkeJzqMkcNvRGnTV76XsbvnJlm71zvnK%2B","x-uid","0","x-nettype","WIFI","x-pv","6.3","x-nq","WIFI","login_sdk_version","5.3.5.3","x-region-channel","MY","x-features","27",":scheme","https","x-app-conf-v","0","x-system-lang","zh-CN","x-mini-wua","alASzgbd8B01NiKyxEwJD7d%2FmZzPdFuD%2BdwdYDYcu49xSUqCRy5Eq86K%2Fo0ZUX7gjzOzGoBOu28UlCd9Bheie6jphhHHORBjkp%2BsREJAOWKdBixoPbDiXGPYG4bikSBUXpoKOxpGoE1vw%2FG0R%2BU1%2Fzvy9qUvqOhvo5SfaurNb7djaudyiSHkKQUdjr3%2Fk0OsArvNmkSG9MAvH%2Fw%3D%3D","content-type","application/x-www-form-urlencoded;charset=UTF-8","Content-Length","4390","x-t","1777284972","Content-Type","application/x-www-form-urlencoded;charset=UTF-8","Cookie","thw=cn; cna=M/ZtIoB06TkCAQ6RNOBR8wcJ; xlly_s=1; isg=BDU14YR1sBr39NSm8797eoE4T7PvsunEJIQxbbda8az7jlWAfwL5lEON3ES4mQF8; tfstk=gbXtRrfcexNTiI2QyAVhnuuKzLsYtWjNYNSSnEYiGwQdVaE2GGjXcKQCol8GcP7AHe_voEv6oIhfSa1MSd6gDZQAkKA1sixjHiQAIxfMIsBd2NCiSd6gDZpNK1DDSPJAcavYrz2uEGSwUKaur1fFDJ9Xc-YbtCT7Rekarz2uKGSw3KaljgYyWwKJVKi6fKZpOe-mhKT6G2GBVeTXhZOjOJtXqAgj1fOQvn8BlK9flMZpmeTXhC_fAQfseE3W-xCQHbo3CDoSnxXpXCL-NeHxHox96Us6JmoVpEO9PGTKnPzqJyY9VTan_3dOfTpGKPNhXe11hdX_-xQRJ_9dpBmbkh112TRPhyIPCTX-95ukYph_vkhqgCtUSY6vVX68whtprk7qgjRTuHLuvkhqgCtevUqLvjl21H5..; hng=MY%7Czh-CN%7CMYR%7C458; x5secdata=xg0eb7a834ef1ec6a2ja4fb11fe91cf54c11d73b7212130b40ed1777284955a-717315356a1266848491abaad3baa33be54b89dd8fdc31e42ad08b9439cd984f__bx__guide-acs.m.taobao.com:443/gw/mtop.relationrecommend.wirelessrecommend.recommend/2.0","x-bx-version","6.7.260101.52320099","f-refer","mtop","x-extdata","openappkey%3DDEFAULT_AUTH","x-ttid","1585202335753%40taobao_android_10.36.10","x-app-ver","10.36.10",":version","HTTP/1.1","x-c-traceid","b8394803baba46fa8172c8281d43cc7e","x-location","113.372274%2C23.125703","a-orange-dq","appKey=21646297&appVersion=10.36.10&clientAppIndexVersion=1120260427180802216","x-regid","reg0fi6VV88vgReRbfDyyvnK0BKbBZLD",":method","POST","x-umt","HOIBlRFLPI54ZgKdzKdoUAD357TjXAEW","x-utdid","ad900VuLB5YDAIeb%2B7UXne7D","c-launch-info","0,0,1777284972912,1777284922744,2","elderHome","0","x-appkey","21646297",":path","/gw/mtop.taobao.mloginservice.smssend/1.0/","x-falco-id","b8394803baba46fa8172c8281d43cc7e","x-page-url","http%3A%2F%2Fs.m.taobao.com%2Fh5entry","x-page-name","com.ali.user.mobile.login.ui.UserLoginActivity","x-devid","I13slmcR4SbhsciyXdbytx4B7QjMvdfngxNViYK-t4TQWBEvXeIgM_oab8656dQZ","user-agent","MTOPSDK%2F3.1.1.7+%28Android%3B8.1.0%3Bgoogle%3BAOSP+on+msm8996%29+DeviceType%28Phone%29"},
//                    new byte[1024],
//                    true,
//                    11,
//                    -1,
//                    7000,
//                    0,
//                    0
//            );
//            String text = "crack submitRequestN result : " + result;
//            simpleText.setText(text);
//            Log.i(TAG, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}