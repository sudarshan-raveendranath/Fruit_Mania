package com.example.fruits
import android.content.Context
import com.google.gson.Gson
import android.util.Log

class FeedbackManager(private val context: Context) {

    private val gson = Gson()
    private val sharedPreferences = context.getSharedPreferences("feedback_prefs", Context.MODE_PRIVATE)

    fun saveFeedback(feedback: Feedback) {
        val feedbackList = getSavedFeedbacks().toMutableList()
        feedbackList.add(feedback)
        val json = gson.toJson(feedbackList)
        sharedPreferences.edit().putString("feedback_list", json).apply()
        Log.d("FeedbackManager", "Feedback saved: $feedback")
    }

    fun getSavedFeedbacks(): List<Feedback> {
        val json = sharedPreferences.getString("feedback_list", "")
        val feedbackList = if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            gson.fromJson(json, Array<Feedback>::class.java).toList()
        }

        // Log the retrieved feedbacks
        Log.d("FeedbackManager", "Retrieved feedbacks: $feedbackList")
        return feedbackList
    }
}