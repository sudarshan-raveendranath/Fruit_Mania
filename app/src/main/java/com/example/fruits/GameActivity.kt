package com.example.fruits

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.fruits.databinding.ActivityGameBinding
import java.util.*
import android.util.Log
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.lifecycle.ViewModelProvider

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var score = 0
    private val imageArray = ArrayList<ImageView>()
    private val handler = android.os.Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var gameTimeInSeconds: Long = 10
    private lateinit var selectedDifficulty: String
    private lateinit var playerName: String
    private lateinit var sharedPreferences: SharedPreferences
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)

        sharedPreferences = getSharedPreferences("Leaderboard", Context.MODE_PRIVATE) // shared preferences for leaderboard
        val sharedPref = getSharedPreferences("GameSettings", Context.MODE_PRIVATE) // shared preferences for settings
        gameTimeInSeconds = sharedPref.getString("GAME_TIME", "10")?.filter { it.isDigit() }?.toLong() ?: 10L // Default value is 10 if not set
        selectedDifficulty = sharedPref.getString("DIFFICULTY", "Medium") ?: "Medium" // default level is medium if not set
        Log.d("GameActivity", "Game time retrieved: $gameTimeInSeconds seconds")
        Log.d("GameActivity", "Selected Game Time: $gameTimeInSeconds")

        playerName = intent.getStringExtra("PLAYER_NAME") ?: "Unknown"
        Log.d("GameActivity", "Player Name: $playerName")

        binding.catchFruits = this
        binding.score = getString(R.string.score_0)
        score = 0
        imageArray.addAll(
            listOf(
                binding.ivApple, binding.ivBanana, binding.ivCherry,
                binding.ivGrapes, binding.ivKiwi, binding.ivOrange,
                binding.ivPear, binding.ivStrawberry, binding.ivWatermelon
            )
        )
        hideImages()
        playAndRestart()
    }


    // hide images function
    private fun hideImages() {
        val delayMillis = when (selectedDifficulty) {
            "Easy" -> 1000 // 1000 milliseconds for easy difficulty
            "Medium" -> 600 // 600 milliseconds for medium difficulty
            "Hard" -> 400 // 400 milliseconds for hard difficulty
            else -> 600 // Default to medium difficulty if difficulty is not specified
        }

        runnable = Runnable {
            imageArray.forEach { it.visibility = View.INVISIBLE }
            imageArray[Random().nextInt(8)].visibility = View.VISIBLE
            handler.postDelayed(runnable, delayMillis.toLong())
        }
        handler.post(runnable)
    }

    // increase score
    @SuppressLint("SetTextI18n")
    fun increaseScore() {
        binding.score = "Score : " + (++score)
        playSoundEffect()
    }

    // play function
    @SuppressLint("SetTextI18n")
    fun playAndRestart() {
        score = 0
        binding.score = "Score : 0"
        hideImages()
        binding.time = "Time : ${gameTimeInSeconds}"
        imageArray.forEach { it.visibility = View.INVISIBLE }

        object : CountDownTimer(gameTimeInSeconds * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                binding.time = getString(R.string.time_up)
                handler.removeCallbacks(runnable)

                with(sharedPreferences.edit()) {
                    val currentScore = sharedPreferences.getInt(playerName, 0)
                    if (score > currentScore) {
                        putInt(playerName, score)
                        apply()
                    }
                }

                // ending sound function
                playEndingSoundEffect()

                Log.d("GameActivity", "Player Name: $playerName, Score: $score")

                AlertDialog.Builder(this@GameActivity).apply {
                    setCancelable(false)
                    setTitle(getString(R.string.game_name))
                    setMessage("Your score : $score\nWould you like to play again?")
                    setPositiveButton(getString(R.string.yes)) { _, _ -> playAndRestart() }
                    setNegativeButton(getString(R.string.no)) { _, _ ->
                        score = 0
                        binding.score = "Score : 0"
                        binding.time = "Time : 0"
                        imageArray.forEach { it.visibility = View.INVISIBLE }
                        finish()
                    }
                }.create().show()
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(tick: Long) {
                binding.time = "Time : " + tick / 1000
            }
        }.start()
    }

    // touch sound function
    private fun playSoundEffect() {
        mediaPlayer = MediaPlayer.create(this, R.raw.touch_sound)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    // ending sound function
    private fun playEndingSoundEffect() {
        mediaPlayer = MediaPlayer.create(this, R.raw.end_sound)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    override fun onResume() {
        super.onResume()
        if (::runnable.isInitialized) {
            handler.post(runnable)
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }


}