package com.example.fruits

import android.content.Intent
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.media.MediaPlayer
import androidx.appcompat.app.AlertDialog
import android.widget.EditText
import android.util.Log

class MenuActivity : AppCompatActivity() {

    private var isSoundOn: Boolean = true
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        // menu buttons
        val startGameButton: Button = findViewById(R.id.start_game_bttn)
        val instructionsButton: Button = findViewById(R.id.instructions_bttn)
        val settingsButton: Button = findViewById(R.id.settings_bttn)
        val leaderboardButton: Button = findViewById(R.id.leaderboard_bttn)
        val soundToggleButton: Button = findViewById(R.id.sound_bttn)
        val feedbackButton: Button = findViewById(R.id.feedback_bttn)

        startGameButton.setOnClickListener {
            showNameInputDialog()
        }

        instructionsButton.setOnClickListener {
            goToInstructions()
        }

        settingsButton.setOnClickListener {
            goToSettings()
        }

        leaderboardButton.setOnClickListener {
            goToLeaderboard()
        }

        soundToggleButton.setOnClickListener {
            toggleSound()
        }

        feedbackButton.setOnClickListener {
            goToFeedbacks()
        }
    }

    // name input function
    private fun showNameInputDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Your Name")

        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, which ->
            val playerName = input.text.toString()
            startGame(playerName)
        }

        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        builder.show()
    }

    // game start function
    private fun startGame(playerName: String) {
        val gameIntent = Intent(this, GameActivity::class.java)
        gameIntent.putExtra("PLAYER_NAME", playerName)
        startActivity(gameIntent)
    }

    //instructions function
    private fun goToInstructions() {
        val intent = Intent(this, InstructionsActivity::class.java)
        startActivity(intent)
    }

    //settings function
    private fun goToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    //leaderboard function
    private fun goToLeaderboard() {
        val intent = Intent(this, LeaderBoardActivity::class.java)
        startActivity(intent)
    }

    //feedback function
    private fun goToFeedbacks() {
        val intent = Intent(this, FeedbackActivity::class.java)
        startActivity(intent)
    }

    //sound toggle function
    private fun toggleSound() {
        isSoundOn = !isSoundOn
        if (isSoundOn) {
            // If sound is on, resume music playback
            mediaPlayer.start()
            Toast.makeText(this, "Sound turned on", Toast.LENGTH_SHORT).show()
        } else {
            // If sound is off, pause music playback
            mediaPlayer.pause()
            Toast.makeText(this, "Sound turned off", Toast.LENGTH_SHORT).show()
        }
    }

    // off the sound when game closed
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    //pause the sound when app paused
    override fun onPause() {
        super.onPause()
        // Pause the music when the app is minimized
        mediaPlayer.pause()
    }

    //resume the sound when the app resumed
    override fun onResume() {
        super.onResume()
        // Resume the music when the app is resumed
        if (isSoundOn) {
            mediaPlayer.start()
        }
    }
}