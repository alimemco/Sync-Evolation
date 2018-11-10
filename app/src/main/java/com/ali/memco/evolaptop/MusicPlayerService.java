package com.ali.memco.evolation;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ali.memco.evolation.view.activity.MusicPlayer;

import java.io.IOException;

public class MusicPlayerService extends Service {

    public static String MUSIC_LINK = "http://dl.nex1music.ir/1397/08/04/Behnam%20Bani%20-%20Del%20Nakan.mp3";
    MusicPlayerBinder musicPlayerBinder = new MusicPlayerBinder();
    private MediaPlayer mediaPlayer;

    private static final String ACTION_PLAY_MUSIC = "com.ali.memco.evolation.PLAY_MUSIC";
    private static final String ACTION_FORWARD_MUSIC = "com.ali.memco.evolation.PLAY_FORWARD";
    private static final String ACTION_REWIND_MUSIC = "com.ali.memco.evolation.PLAY_REWIND";
    private static final String STOP_MUSIC_SERVICE = "com.ali.memco.evolation.STOP_MUSIC_SERVICE";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        setupMusic();
        Log.i("TestServiceStarted", "onBind: ");
        return musicPlayerBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("TestServiceStarted", "onStartCommand: ");

        if (intent.getAction() == null) {
            intent.setAction("");
            Log.i("TestServiceStarted", "null Action: ");
        }
        switch (intent.getAction()) {
            case ACTION_PLAY_MUSIC:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }else {
                    mediaPlayer.start();
                }
                break;

            case ACTION_FORWARD_MUSIC:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
                break;

            case ACTION_REWIND_MUSIC:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
                break;

            case STOP_MUSIC_SERVICE:
                stopForeground(true);
                stopSelf();
                break;

            default:
                Log.i("TestServiceStarted", "defult Action: ");
                Intent showMusicIntent = new Intent(this, MusicPlayer.class);
                showMusicIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                Intent playMusic = new Intent(this, MusicPlayerService.class);
                playMusic.setAction(ACTION_PLAY_MUSIC);
                PendingIntent playMusicPendidng = PendingIntent.getService(this, 0, playMusic, 0);

                Intent forwardMusic = new Intent(this, MusicPlayerService.class);
                forwardMusic.setAction(ACTION_FORWARD_MUSIC);
                PendingIntent forwardMusicPendidng = PendingIntent.getService(this, 0, forwardMusic, 0);

                Intent rewindMusic = new Intent(this, MusicPlayerService.class);
                rewindMusic.setAction(ACTION_REWIND_MUSIC);
                PendingIntent rewindMusicPendidng = PendingIntent.getService(this, 0, rewindMusic, 0);

                Intent stopServiceIntent = new Intent(this, MusicPlayerService.class);
                stopServiceIntent.setAction(STOP_MUSIC_SERVICE);
                PendingIntent stopServicePendgingIntent = PendingIntent.getService(this, 0, stopServiceIntent, 0);

                Notification notification = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_music_note_black_36dp)
                        .setContentTitle("Music Player")
                        .setContentText("Behnam Bani - Del nakan")
                        .setOngoing(true)
                        .setContentIntent(PendingIntent.getActivity(this,0,showMusicIntent,0))
                        //.addAction(R.drawable.ic_fast_rewind_black_48dp,"Rewind",rewindMusicPendidng)
                        .addAction(R.drawable.ic_action_play_black,"play",playMusicPendidng)
                       // .addAction(R.drawable.ic_fast_forward_black_48dp,"forward",forwardMusicPendidng)
                        .addAction(android.R.drawable.ic_delete,"Close",stopServicePendgingIntent)
                        .build();
                startForeground(112,notification);

                break;
        }

        return START_STICKY;
    }

    private void setupMusic() {

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, Uri.parse(MUSIC_LINK));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopForeground(true);
                stopSelf();

            }
        });
    }

    @Override
    public void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    public class MusicPlayerBinder extends Binder {
        public MusicPlayerService getService() {
            return MusicPlayerService.this;
        }
    }

}
