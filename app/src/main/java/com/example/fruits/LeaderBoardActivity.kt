package com.example.fruits

import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class LeaderBoardActivity : AppCompatActivity() {
    private lateinit var leaderboardRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        leaderboardRecyclerView = findViewById(R.id.leaderboardRecyclerView)

        val playerList = retrievePlayerList() // Get player list from SharedPreferences

        displayPlayers(playerList)

    }

    // player list retrieve function
    private fun retrievePlayerList(): List<Pair<String, Int>> {
        val sharedPreferences = getSharedPreferences("Leaderboard", Context.MODE_PRIVATE)
        val playerNames = sharedPreferences.all.keys.toList()
        val playerList = mutableListOf<Pair<String, Int>>()
        for (name in playerNames) {
            val score = sharedPreferences.getInt(name, 0)
            playerList.add(Pair(name, score))
        }
        return playerList.sortedByDescending { it.second }
    }

    // display players function
    private fun displayPlayers(playerList: List<Pair<String, Int>>) {
        val linearLayout = findViewById<LinearLayout>(R.id.playerListLayout)
        linearLayout.removeAllViews() // Clear previous views

        playerList.forEach { (name, score) ->
            val playerEntryLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val playerNameTextView = TextView(this).apply {
                text = name
                textSize = 26f
                setTextColor(resources.getColor(android.R.color.black))
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }

            val playerScoreTextView = TextView(this).apply {
                text = score.toString()
                textSize = 26f
                setTextColor(resources.getColor(android.R.color.black))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginStart = resources.getDimensionPixelSize(R.dimen.score_margin)
                }
            }

            playerEntryLayout.addView(playerNameTextView)
            playerEntryLayout.addView(playerScoreTextView)
            linearLayout.addView(playerEntryLayout)
        }
    }



}
