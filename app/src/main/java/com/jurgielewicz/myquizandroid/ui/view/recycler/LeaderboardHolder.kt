package com.jurgielewicz.myquizandroid.ui.view.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import com.jurgielewicz.myquizandroid.model.Score
import kotlinx.android.synthetic.main.leaderboard_row.view.*

class LeaderboardHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bind(item: Score?, position: Int){
        view.score_TextView.text = item?.score.toString()
        view.name_TextView.text = item?.name
        view.position_TextView.text = position.toString()
    }
}