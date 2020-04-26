package translation.drama.radio.radiodramatranslation

import android.content.Context
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.media.AudioManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import kotlin.math.ceil


class PlayActivity : AppCompatActivity(), Runnable, SeekBar.OnSeekBarChangeListener {


    private var mPlayer: MediaPlayer? = null
    private var length: Int = 0
    private var seekBarSong: SeekBar? = null
    private var seekBarThread : Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0)

        seekBarSong = findViewById(R.id.seekbar)
        seekBarSong!!.setOnSeekBarChangeListener(this)


        val buttonPlay = findViewById<Button>(R.id.play)
        buttonPlay.setOnClickListener {

            if (mPlayer == null) {

                mPlayer = MediaPlayer.create(applicationContext, R.raw.test)
                mPlayer!!.setVolume(0.1.toFloat(), 0.1.toFloat())
                seekBarSong!!.max = mPlayer!!.duration
                seekBarThread = Thread(this)
                seekBarThread!!.start()
            }
            mPlayer!!.seekTo(seekBarSong!!.progress)
            mPlayer!!.start()//Create MediaPlayer object with MP3 file under res/raw folde


        }

        val buttonStop = findViewById<Button>(R.id.stop)
        buttonStop.setOnClickListener {

            if (mPlayer != null) {
                mPlayer!!.pause()
                length = mPlayer!!.currentPosition
            }
        }
    }


    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

        Log.i("on stop Tracking", "stoped moving the seekbar" + seekBarSong!!.progress)

        if (mPlayer != null) {
            mPlayer!!.seekTo(seekBarSong!!.progress)
        }
    }

    override fun run() {

        Log.i("run song", "entered running")

        while (mPlayer != null) {

            if(mPlayer!!.isPlaying) {

                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }

                seekBarSong!!.progress = mPlayer!!.currentPosition
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        clearMediaPlayer()
        if(seekBarThread != null) {
            seekBarThread!!.join()
        }
    }

    private fun clearMediaPlayer() {

        mPlayer!!.stop()
        mPlayer!!.release()
        mPlayer = null
    }
}
