package com.jurgielewicz.myquizandroid.ui.view.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jurgielewicz.myquizandroid.R
import com.jurgielewicz.myquizandroid.model.Score

class LeaderboardAdapter(private val leaderboard: List<Score?>): RecyclerView.Adapter<LeaderboardHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LeaderboardHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val row = layoutInflater.inflate(R.layout.leaderboard_row, p0,false )
        return LeaderboardHolder(row)
    }

    override fun getItemCount(): Int {
        return leaderboard.size
    }

    override fun onBindViewHolder(p0: LeaderboardHolder, p1: Int) {
        p0.bind(leaderboard[p1], p1+1)
    }
}