package com.example.voicetext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.FilenameFilter;

public class Storage extends AppCompatActivity {

    final static String callfolder = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SamdosCALL";
    final static String micfolder = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SamdosMIC";
    private String[] calllist = getTitleList(callfolder);
    private String[] miclist = getTitleList(micfolder);
    private AlertDialog mSelectDialog1, mSelectDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        ImageButton mic_stroage = (ImageButton) findViewById(R.id.mic_stroage);
        ImageButton call_stroage = (ImageButton) findViewById(R.id.call_stroage);


        //닉네임 설정
        mic_stroage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mSelectDialog1.show();
            }
        });

        //닉네임 설정
        call_stroage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mSelectDialog2.show();
            }
        });

        //calllist선택지 다이얼로그
        mSelectDialog1 = new AlertDialog.Builder(Storage.this )
                .setItems(calllist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent txtintent = new Intent(getApplicationContext(), Txtview.class);
                        txtintent.putExtra("txt", calllist[i]);
                        txtintent.putExtra("route", callfolder);
                        startActivity(txtintent);
                    }
                })
                .setTitle("선택")
                .setPositiveButton("확인",null)
                .setNegativeButton("취소",null)
                .create();

        //miclist선택지 다이얼로그
        mSelectDialog2 = new AlertDialog.Builder(Storage.this )
                .setItems(miclist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent txtintent = new Intent(getApplicationContext(), Txtview.class);
                        txtintent.putExtra("txt", miclist[i]);
                        txtintent.putExtra("route", micfolder);
                        startActivity(txtintent);
                    }
                })
                .setTitle("선택")
                .setPositiveButton("확인",null)
                .setNegativeButton("취소",null)
                .create();

    }


    //디렉토리 안의 파일 리스트 가져오는 메소드
    private String[] getTitleList(String foldername) {//알아 보기 쉽게 메소드 부터 시작합니다.
        try{
            FilenameFilter fileFilter = new FilenameFilter() { //이부분은 특정 확장자만 가지고 오고 싶을 경우 사용하시면 됩니다.
                public boolean accept(File dir, String name) {
                    return name.endsWith("txt"); //이 부분에 사용하고 싶은 확장자를 넣으시면 됩니다.
                } //end accept
            };
            File file = new File(foldername); //경로를 SD카드로 잡은거고 그 안에 있는 A폴더 입니다. 입맛에 따라 바꾸세요.
            File[] files = file.listFiles(fileFilter);//위에 만들어 두신 필터를 넣으세요. 만약 필요치 않으시면 fileFilter를 지우세요.
            String [] titleList = new String [files.length]; //파일이 있는 만큼 어레이 생성했구요
            for(int i = 0;i < files.length;i++) {
                titleList[i] = files[i].getName();	//루프로 돌면서 어레이에 하나씩 집어 넣습니다.
            }//end for
            return titleList;
        } catch( Exception e ) {
            return null;
        }//end catch()

    }//end getTitleList
}
