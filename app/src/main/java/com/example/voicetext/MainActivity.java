package com.example.voicetext;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageButton btn_record, btn_play, btn_stt, btn_tts, btn_trans, btn_setting;
    // 멀티 퍼미션 지정
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, // 기기, 사진, 미디어, 파일 엑세스 권한
            Manifest.permission.RECORD_AUDIO, // 녹음기능
            Manifest.permission.READ_PHONE_STATE // 통화 상태 읽기
            //Manifest.permission.PROCESS_OUTGOING_CALLS // 전화 발신 체크
    };
    private static final int MULTIPLE_PERMISSIONS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check for permission
        if (Build.VERSION.SDK_INT >= 23) { // 안드로이드 6.0 이상일 경우 퍼미션 체크
            checkPermissions();
        }

        ImageButton btn_play = (ImageButton) findViewById(R.id.btn_play);
        ImageButton btn_record = (ImageButton) findViewById(R.id.btn_record);
        ImageButton btn_stt = (ImageButton) findViewById(R.id.btn_stt);
        ImageButton btn_tts = (ImageButton) findViewById(R.id.btn_tts);
        ImageButton btn_trans = (ImageButton) findViewById(R.id.btn_trans);
        ImageButton btn_setting = (ImageButton) findViewById(R.id.btn_setting);

        //환경설정화면
        btn_setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent micButton = new Intent(getApplicationContext(), Setting.class);
                startActivity(micButton);
            }
        });
        //녹음버튼
        btn_record.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent micButton = new Intent(getApplicationContext(), RecordActivity.class);
                startActivity(micButton);
            }
        });
        //음성인식버튼
        btn_stt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent micButton = new Intent(getApplicationContext(), STTActivity.class);
                startActivity(micButton);
            }
        });
        //저장목록버튼
        btn_trans.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent micButton = new Intent(getApplicationContext(), Storage.class);
                startActivity(micButton);
            }
        });

    }

    //권환부여확인
    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[i])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showToast_PermissionDeny();
                            }
                        }
                    }
                } else {
                    showToast_PermissionDeny();
                }
                return;
            }
        }

    }

    private void showToast_PermissionDeny() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

}