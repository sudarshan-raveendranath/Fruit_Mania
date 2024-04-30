package com.example.fruits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

class ViewFeedbacksActivity : AppCompatActivity() {

    private lateinit var feedbackManager: FeedbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_feedbacks)

        feedbackManager = FeedbackManager(this)

        populateFeedbacks()
    }

    private fun populateFeedbacks() {
        val feedbackList = feedbackManager.getSavedFeedbacks()

        val linearLayout = findViewById<LinearLayout>(R.id.feedbackListLinearLayout)
        linearLayout.removeAllViews()

        for ((index, feedback) in feedbackList.withIndex()) {
            val itemView = layoutInflater.inflate(R.layout.activity_view_feedbacks, null)
            val playerNameTextView = itemView.findViewById<TextView>(R.id.playerNameTextView)
            val ratingTextView = itemView.findViewById<TextView>(R.id.ratingTextView)
            val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)

            playerNameTextView.text = "${index + 1}. ${feedback.playerName}"
            ratingTextView.text = "Rating: ${feedback.rating}"
            descriptionTextView.text = "Description: ${feedback.description}"

            linearLayout.addView(itemView)
        }
    }
}