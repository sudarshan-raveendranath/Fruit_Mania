package com.example.fruits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val addFeedbacksButton: Button = findViewById(R.id.addfeedback_bttn)
        val viewFeedbacksButton: Button = findViewById(R.id.viewfeedback_bttn)

        addFeedbacksButton.setOnClickListener {
            goToAddFeedbacks()
        }

        viewFeedbacksButton.setOnClickListener {
            goToViewFeedbacks()
        }
    }

    private fun goToAddFeedbacks() {
        val intent = Intent(this, AddFeedbacksActivity::class.java)
        startActivity(intent)
    }

    private fun goToViewFeedbacks() {
        val intent = Intent(this, ViewFeedbacksActivity::class.java)
        startActivity(intent)
    }
}