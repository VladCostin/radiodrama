package translation.drama.radio.radiodramatranslation

import android.content.Context
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import translation.drama.radio.radiodramatranslation.R
import android.media.AudioManager
import android.content.Context.AUDIO_SERVICE
import android.util.Log
import android.widget.Toast


class PlayActivity : AppCompatActivity() {

    var mPlayer : MediaPlayer? = null;
    var length : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0)

//         MediaPlayer.create(applicationContext, R.raw.test).start()

        val buttonPlay = findViewById<Button>(R.id.play)
        buttonPlay.setOnClickListener {

            Toast.makeText(applicationContext,"You clicked me.", Toast.LENGTH_SHORT).show()

            if (mPlayer == null) {

                mPlayer =  MediaPlayer.create(applicationContext, R.raw.test)
            }
            mPlayer!!.seekTo(length)
            mPlayer!!.start()//Create MediaPlayer object with MP3 file under res/raw folde


        }

        val buttonStop = findViewById<Button>(R.id.stop)
        buttonStop.setOnClickListener {

            Toast.makeText(applicationContext,"You clicked me.", Toast.LENGTH_SHORT).show()
            if(mPlayer != null) {
                mPlayer!!.pause()
                length = mPlayer!!.currentPosition
            }
        }
    }
}
