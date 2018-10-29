package com.ali.memco.evolation.view.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.memco.evolation.R;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayer extends AppCompatActivity {

    private ImageView play;
    private ImageView forwrad;
    private ImageView backward;
    private TextView curTime;
    private TextView allTime;
    private TextView musicName;
    private SeekBar seekBar;
    private Timer timer;
    private File dirMusic;
    private File newMusic;

    private static String MUSIC_LINK="http://dl.nex1music.ir/1397/08/04/Behnam%20Bani%20-%20Del%20Nakan.mp3";
    //private static String MUSIC_name="amirabbas_golab_shahe_ghalbam.mp3";

    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        initViews();

        setupMusic();

    }

    private void setupMusic() {

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(this, Uri.parse(MUSIC_LINK));
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            setupiews();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


    }

    private void setupiews() {

        curTime.setText(formatTime(0));
        allTime.setText(formatTime(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
        musicName.setText(musicName(MUSIC_LINK));

        if (mediaPlayer.isPlaying()){
            play.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_pause,null));
        }else {
            play.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_play,null));
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    play.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_play,null));
                    mediaPlayer.pause();
                }else {
                    play.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_pause,null));
                    mediaPlayer.start();
                }
            }
        });


        forwrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+5000);
                seekBar.setProgress(mediaPlayer.getCurrentPosition()+5000);
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                curTime.setText(formatTime(seekBar.getVerticalScrollbarPosition()));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.seekTo(0);
                play.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_play,null));
            }
        });
        timer = new Timer();
        timer.schedule(new MainTimer(),0,1000);

    }

    private void initViews() {
        play = findViewById(R.id.music_play);
        forwrad = findViewById(R.id.music_forward);
        backward = findViewById(R.id.music_backward);
        curTime = findViewById(R.id.music_cutime);
        allTime = findViewById(R.id.music_alltime);
        musicName = findViewById(R.id.music_name);
        seekBar = findViewById(R.id.music_seekbar);
    }

    private String formatTime(long duration){
        int second = (int) (duration/1000);
        int minutes = second/60;
        second %=60;
        return String.format(Locale.ENGLISH,"%02d",minutes)+":"+String.format(Locale.ENGLISH,"%02d",second);
    }

    private class MainTimer extends TimerTask{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    curTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
                }
            });

        }
    }

    private String musicName(String musicName){
        String name = musicName.substring(musicName.lastIndexOf("/")+1,musicName.length());
        name = name.replace("%20"," ");
        return name;
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.release();
        timer.purge();
        timer.cancel();
        super.onDestroy();
    }
}
