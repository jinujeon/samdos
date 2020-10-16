package com.example.voicetext;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.voicetext.R;

public class Setting extends AppCompatActivity {

    public String username;
    //다른 액티비티로 넘기는 변수
    public static Context context;

    //선택 위한 변수들
    private String[] mChoice = {"텍스트만 저장","음성과 함께 저장"};
    private AlertDialog mSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SharedPreferences sp = getSharedPreferences("sFile", MODE_PRIVATE);
        username = sp.getString("username", "");
        Button closeButton = (Button) findViewById(R.id.closeButton);
        Button idbutton = (Button) findViewById(R.id.idbutton);
        Button testbutton = (Button) findViewById(R.id.testbutton);
        Button permissionbutton = (Button) findViewById(R.id.permissionbutton);
        Button choiceButton = (Button) findViewById(R.id.choiceButton);


        //닉네임 설정
        idbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                show();
            }
        });
        //닉네임 확인
        testbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"저장된 닉네임은 " + username + "입니다.",Toast.LENGTH_LONG).show();
            }
        });
        //권한설정
        permissionbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(myIntent, 5469);
            }
        });
        //저장 종류 설정하기
        choiceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mSelectDialog.show();
            }
        });
        //닫기버튼
        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //선택지 다이얼로그
        mSelectDialog = new AlertDialog.Builder(Setting.this )
                .setItems(mChoice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //closeButton.setText(mChoice[i]);
                    }
                })
                .setTitle("선택")
                .setPositiveButton("확인",null)
                .setNegativeButton("취소",null)
                .create();

        context = this; //다른 액티비티에 넘겨주기 위한 컨텍스트
    }

    //닉네임 저장용
    @Override
    protected void onStop() {
        super.onStop();

        // Activity가 종료되기 전에 저장한다.
        //SharedPreferences를 sFile이름, 기본모드로 설정
        SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);

        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",username); // key, value를 이용하여 저장하는 형태

        //최종 커밋
        editor.commit();


    }

    //닉네임 설정 AlertDiaalog 팝업창 설정
    void show(){
        final EditText nickname = new EditText(getApplicationContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("닉네임 설정");
        //타이틀설정
        builder.setView(nickname);
        //내용설정
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        username = nickname.getText().toString();
                        Toast.makeText(getApplicationContext(),"닉네임 설정 완료",Toast.LENGTH_LONG).show();
                    }
                });

        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소완료",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }


}
