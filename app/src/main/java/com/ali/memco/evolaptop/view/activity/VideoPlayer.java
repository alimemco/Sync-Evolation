package com.ali.memco.evolation;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.MainThread;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPlayer extends AppCompatActivity {

    private ImageView play;
    private ImageView forwrad;
    private ImageView backward;
    private TextView curTime;
    private TextView allTime;
    private SeekBar seekBar;
    private FrameLayout frameLayout;
    private Timer timer;
    private VideoView videoView;
    private RelativeLayout.LayoutParams landScape;
    private RelativeLayout.LayoutParams portrate;
    private static String VideoURL="http://simafekr.tv/wp-content/uploads/2016/07/201601/1982_1081424710_hq.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        initViews();
        setupOriation();
        setupVideo();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            frameLayout.setLayoutParams(landScape);
            frameLayout.setBackgroundColor(ContextCompat.getColor(VideoPlayer.this,android.R.color.black));
            frameLayout.bringToFront();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else {
            frameLayout.setLayoutParams(portrate);
            frameLayout.setBackgroundColor(ContextCompat.getColor(VideoPlayer.this,android.R.color.white));
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void setupOriation() {
        View toolbar = findViewById(R.id.video_toolbar);
        View controler = findViewById(R.id.controler);

        portrate = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        landScape = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        portrate.addRule(RelativeLayout.BELOW,toolbar.getId());
        portrate.addRule(RelativeLayout.ABOVE,controler.getId());

    }

    private void setupVideo() {

        videoView = findViewById(R.id.video_view);
        videoView.setVideoURI(Uri.parse(VideoURL));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setupViews();
            }
        });


    }

    private void setupViews() {

        curTime.setText(formatTime(0));
        allTime.setText(formatTime(videoView.getDuration()));
        seekBar.setMax(videoView.getDuration());

        if (videoView.isPlaying()){
            play.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_pause,null));
        }else {
            play.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_play,null));
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()){
                    play.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_play,null));
                    videoView.pause();
                }else {
                    play.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_pause,null));
                    videoView.start();
                }
            }
        });


        forwrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.seekTo(videoView.getCurrentPosition()+5000);
                seekBar.setProgress(videoView.getCurrentPosition()+5000);
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.seekTo(videoView.getCurrentPosition()-5000);
                seekBar.setProgress(videoView.getCurrentPosition());
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    videoView.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                play.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_play,null));
            }
        });

        timer = new Timer();
        timer.schedule(new MainTimer(),0,1000);


    }

    private void initViews() {

        play = findViewById(R.id.video_play);
        forwrad = findViewById(R.id.video_forward);
        backward = findViewById(R.id.video_backward);
        curTime = findViewById(R.id.video_cutime);
        allTime = findViewById(R.id.video_alltime);
        seekBar = findViewById(R.id.video_seekbar);
        frameLayout = findViewById(R.id.video_frame);


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
                    seekBar.setProgress(videoView.getCurrentPosition());
                    seekBar.setSecondaryProgress((videoView.getBufferPercentage()*videoView.getDuration())/100);
                    curTime.setText(formatTime(videoView.getCurrentPosition()));
                }
            });
        }
    }
}
