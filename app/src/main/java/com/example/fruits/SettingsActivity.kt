package com.example.fruits

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Button

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val gameTimeSpinner: Spinner = findViewById(R.id.gameTimeSpinner)
        val difficultySpinner: Spinner = findViewById(R.id.difficultySpinner)
        val okButton: Button = findViewById(R.id.okButton)

        ArrayAdapter.createFromResource(
            this,
            R.array.game_time_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            gameTimeSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.difficulty_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            difficultySpinner.adapter = adapter
        }

        okButton.setOnClickListener {
            // Get the selected values from the Spinners
            val selectedGameTime = gameTimeSpinner.selectedItem.toString()
            val selectedDifficulty = difficultySpinner.selectedItem.toString()
            Log.d("SettingsActivity", "Selected Game Time: $selectedGameTime")
            Log.d("SettingsActivity", "Selected Difficulty: $selectedDifficulty")


            val sharedPref = getSharedPreferences("GameSettings", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("GAME_TIME", selectedGameTime)
                putString("DIFFICULTY", selectedDifficulty)
                apply()
            }

            // Finish the SettingsActivity
            finish()
        }
    }
}