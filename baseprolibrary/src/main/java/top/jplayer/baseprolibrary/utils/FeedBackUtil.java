package top.jplayer.baseprolibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Vibrator;
import android.support.annotation.RawRes;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by Obl on 2018/7/31.
 * top.jplayer.baseprolibrary.utils
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 * 音频 震动 反馈
 */

public class FeedBackUtil {
    private static FeedBackUtil mFeedBackUtil;
    private WeakReference<Activity> mWeakReference;
    private Vibrator mVibrator;
    private MediaPlayer mMediaPlayer;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean playBeep;

    public FeedBackUtil(Activity activity) {
        mWeakReference = new WeakReference<>(activity);
    }

    public static FeedBackUtil init(Activity activity) {
        if (mFeedBackUtil == null) {
            mFeedBackUtil = new FeedBackUtil(activity);
        }
        return mFeedBackUtil;
    }

    public void playVibrator(long time) {
        mVibrator = (Vibrator) mWeakReference.get().getSystemService(VIBRATOR_SERVICE);
        stopVibrator();
        if (mVibrator != null) {
            mVibrator.vibrate(time);

        }
    }

    public void playVibrator(long[] time, int repeat) {
        mVibrator = (Vibrator) mWeakReference.get().getSystemService(VIBRATOR_SERVICE);
        stopVibrator();
        if (mVibrator != null) {
            //a[0]表示静止的时间，a[1]代表的是震动的时间，然后数组的a[2]表示静止的时间，a[3]代表的是震动的时间……
            mVibrator.vibrate(time, repeat);
        }
    }

    public void stopVibrator() {
        if (mVibrator != null && mVibrator.hasVibrator()) {
            mVibrator.cancel();
        }
    }

    public void playBeep(@RawRes int raw) {
        stopMedia();
        initBeepSound(raw);
        if (playBeep && mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = mediaPlayer -> mediaPlayer.seekTo(0);

    private void initBeepSound(@RawRes int raw) {
        playBeep = true;
        String audio = Context.AUDIO_SERVICE;
        AudioManager audioService = (AudioManager) mWeakReference.get().getSystemService(audio);
        if (audioService != null && audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        if (playBeep && mMediaPlayer == null) {
            mWeakReference.get().setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = mWeakReference.get().getResources().openRawResourceFd(
                    raw);
            try {
                mMediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mMediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                mMediaPlayer = null;
            }
        }
    }

    private SoundPool sp;

    /**
     * 音效池
     *
     * @param integers
     * @return
     */
    public List<Integer> loadSoundPool(@RawRes List<Integer> integers) {
        List<Integer> soundIdList = new ArrayList<>();
        sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        Observable.fromIterable(integers).subscribe(integer -> {
            int load = sp.load(mWeakReference.get(), integer, 1);
            soundIdList.add(load);
        });
        return soundIdList;
    }

    public List<Integer> playSoundPool(List<Integer> integers) {
        List<Integer> streamIdList = new ArrayList<>();
        Observable.fromIterable(integers).subscribe(integer -> {
            int play = sp.play(integer, 0.5f, 0.5f, integer, 1, 1f);
            streamIdList.add(play);
        });
        return streamIdList;
    }

    public Integer playSoundPool(Integer integers) {
        return sp.play(integers, 0.8f, 0.8f, 1, 1, 1f);
    }

    public void stopSoundPool(int id) {
        sp.resume(id);
    }

    //开始播放
    public void playRing() {
        stopMedia();
        try {
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);//用于获取手机默认铃声的Uri
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(mWeakReference.get(), alert);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);//告诉mediaPlayer播放的是铃声流
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //停止播放
    public void stopMedia() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
            mMediaPlayer = null;
        }
    }

}
