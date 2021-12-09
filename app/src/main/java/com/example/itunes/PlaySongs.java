 package com.example.itunes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlaySongs extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        updateSeek.interrupt();
    }



    ImageView play;
    ImageView previous;
    ImageView next;
    TextView textView;
    MediaPlayer mediaPlayer;
    String textContent; ArrayList<File> songs;  int position;
    SeekBar seekBar;
    Thread updateSeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_songs);
        play=findViewById(R.id.play);
        previous=findViewById(R.id.previous);
        next=findViewById(R.id.next);
        textView=findViewById(R.id.textView);
        seekBar=findViewById(R.id.seekBar);

        Intent intent=getIntent();
        textContent=intent.getStringExtra("currentSong");
        textView.setText(textContent);
        textView.setSelected(true);

        Bundle bundle=intent.getExtras();
        songs=(ArrayList) bundle.getParcelableArrayList("songList");
        position=intent.getIntExtra("position",0);
        Uri uri=Uri.parse(songs.get(position).toString());
        mediaPlayer=mediaPlayer.create(this,uri);
        mediaPlayer.start();

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        updateSeek=new Thread(){
            @Override
            public void run() {
                int currentposition=0;
                try {
                    while (currentposition < mediaPlayer.getDuration()) {
                        currentposition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentposition);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        updateSeek.start();

    play.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mediaPlayer.isPlaying()){
                play.setImageResource(R.drawable.play);
                mediaPlayer.pause();
            }
            else{
                play.setImageResource(R.drawable.pause);
                mediaPlayer.start();
            }
        }
    });

    previous.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.stop();
            mediaPlayer.release();
           if(position==0){
               position=songs.size()-1;
           }
           else{
               position=position-1;
           }

            //Reused COde
            Uri uri=Uri.parse(songs.get(position).toString());
            mediaPlayer=mediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
            play.setImageResource(R.drawable.pause);

            textContent=songs.get(position).getName().toString();
            textView.setText(textContent);

            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            });
            updateSeek=new Thread(){
                @Override
                public void run() {
                    int currentposition=0;
                    try {
                        while (currentposition < mediaPlayer.getDuration()) {
                            currentposition = mediaPlayer.getCurrentPosition();
                            seekBar.setProgress(currentposition);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            updateSeek.start();
        }
    });

    next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.stop();
            mediaPlayer.release();
            if(position==songs.size()-1){
                position=0;
            }
            else{
                position=position+1;
            }

            //Reused COde
            Uri uri=Uri.parse(songs.get(position).toString());
            mediaPlayer=mediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
            play.setImageResource(R.drawable.pause);

            textContent=songs.get(position).getName().toString();
            textView.setText(textContent);

            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            });
            updateSeek=new Thread(){
                @Override
                public void run() {
                    int currentposition=0;
                    try {
                        while (currentposition < mediaPlayer.getDuration()) {
                            currentposition = mediaPlayer.getCurrentPosition();
                            seekBar.setProgress(currentposition);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            updateSeek.start();
        }
    });


    }
}