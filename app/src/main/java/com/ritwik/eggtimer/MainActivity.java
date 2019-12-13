package com.ritwik.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Button start;
    long startPoint;
    boolean active;
    CountDownTimer cdt;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600000);
        active = true;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener( ) {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                startPoint = progress;
                long min = startPoint/60000;
                long sec = (startPoint/1000)%60;
                String timer = min+":"+sec;
                if(sec<10){
                    timer = min +":0"+sec;
                }
                textView.setText(timer);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        start.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if(active) {
                    active = false;
                    start.setText("Reset");
                    cdt = new CountDownTimer(startPoint, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            startPoint -= 1000;
                            long min = startPoint / 60000;
                            long sec = (startPoint / 1000) % 60;
                            String timer = min + ":" + sec;
                            if (sec < 10) {
                                timer = min + ":0" + sec;
                            }
                            textView.setText(timer);
                        }

                        @Override
                        public void onFinish() {
                            mp = MediaPlayer.create(MainActivity.this,R.raw.buzzer);
                            mp.start();
                            mp.setLooping(true);
                            textView.setText("00:00");
                            start.setText("Shut Up!!");
                        }
                    }.start( );
                } else{
                    seekBar.setProgress(0);
                    textView.setText("00:00");
                    active = true;
                    startPoint = 0;
                    mp.stop();
                    start.setText("Start");
                    cdt.cancel();
                }
            }
        });

    }
}
