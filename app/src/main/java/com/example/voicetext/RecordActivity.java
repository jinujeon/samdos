package com.example.voicetext;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RecordActivity extends AppCompatActivity {
    private final static String TAG = "MicActivity";

    MediaRecorder mRecorder = null;
    String mPath = null;

    MediaPlayer mPlayer = null;

    boolean isRecording = false;    //녹음중인지 판별
    boolean isPlaying = false;      //실행중인지 판별

    Button mBtRecord = null;
    Button mBtPlay = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mBtRecord = (Button) findViewById(R.id.bt_record);
        mBtRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording == false) { //녹음중이 아니라면
                    initAudioRecorder();   //레코더를 이니시하고
                    mRecorder.start();  //녹음 시작

                    isRecording = true;     //상태 녹음중으로 변환
                    mBtRecord.setText("Stop Recording");
                } else {                //녹음중일 경우
                    mRecorder.stop();   //녹음을 중지하고
                    isRecording = false;//상태를 중지상태로 변환
                    mBtRecord.setText("Start Recording");
                }
            }
        });


        mBtPlay = (Button) findViewById(R.id.bt_play);
        mBtPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying == false) {   //실행중이 아니라면
                    try {
                        mPlayer.setDataSource(mPath);
                        mPlayer.prepare();  //데이터준비 후
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    mPlayer.start();        //실행
                    isPlaying = true;       //상태를 실행으로 변경
                    mBtPlay.setText("Stop Playing");
                }
                else {                      //실행중이라면
                    mPlayer.stop();         //중지
                    isPlaying = false;      //상태를 중지로 변경
                    mBtPlay.setText("Start Playing");
                }
            }
        });

        mRecorder = new MediaRecorder();
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion");
                isPlaying = false;
                mBtPlay.setText("Start Playing");
            }
        });

    }

    MediaPlayer.OnCompletionListener mListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mBtPlay.setText("Start Playing");
        }
    };


    void initAudioRecorder() {  //녹음기 실행
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //녹음위치 설정
        mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/record.mp3";
        Log.d(TAG, "file path is " + mPath);
        mRecorder.setOutputFile(mPath);
        try {
            mRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }

        if(mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }
}
