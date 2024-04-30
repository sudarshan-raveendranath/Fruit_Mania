package com.example.fruits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast

class AddFeedbacksActivity : AppCompatActivity() {

    private lateinit var feedbackManager: FeedbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_feedbacks)

        feedbackManager = FeedbackManager(this)

        val submitButton = findViewById<Button>(R.id.submit_bttn)
        submitButton.setOnClickListener {
            saveFeedback()
        }
    }

    private fun saveFeedback() {
        val playerNameInput = findViewById<EditText>(R.id.playerNameInput)
        val ratingSpinner = findViewById<Spinner>(R.id.ratingSpinner)
        val feedbackDescriptionInput = findViewById<EditText>(R.id.feedbackDescriptionInput)

        val playerName = playerNameInput.text.toString()
        val rating = ratingSpinner.selectedItemPosition + 1 // assuming spinner position is 0-based
        val description = feedbackDescriptionInput.text.toString()

        val feedback = Feedback(playerName, rating, description)
        feedbackManager.saveFeedback(feedback)

        Toast.makeText(this, "Feedback saved successfully", Toast.LENGTH_SHORT).show()

        // Clear input fields after saving feedback
        playerNameInput.text.clear()
        feedbackDescriptionInput.text.clear()
    }
}