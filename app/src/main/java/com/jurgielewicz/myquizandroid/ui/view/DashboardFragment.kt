package com.jurgielewicz.myquizandroid.ui.view


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jurgielewicz.myquizandroid.R
import com.jurgielewicz.myquizandroid.model.Score
import com.jurgielewicz.myquizandroid.ui.contract.DashboardFragmentContract
import com.jurgielewicz.myquizandroid.ui.view.recycler.LeaderboardAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.lang.Exception


class DashboardFragment : Fragment(), DashboardFragmentContract.View, View.OnClickListener {
    private val presenter: DashboardFragmentContract.Presenter by inject { parametersOf(this) }
    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        rootView.playButton.setOnClickListener(this)
        rootView.bugButton.setOnClickListener(this)
        rootView.leaderboard_Recycler.layoutManager = LinearLayoutManager(activity)
        rootView.leaderboard_Recycler.adapter = null
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.onCreated()
        presenter.getLeaderboard()
    }

    override fun setLeaderboard(list: List<Score?>) {
        rootView.leaderboard_Recycler.adapter = LeaderboardAdapter(list)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            rootView.playButton.id -> startGame()
            rootView.bugButton.id -> sendEmail()
        }
    }

    override fun setUserPhoto(bitmap: Bitmap) {
        try{
            rootView.userPhoto.setImageBitmap(bitmap)
        } catch (e: Exception) {
            Log.d("Set user photo", e.message)
        }
    }

    override fun startGame() {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentHolder, QuizFragment())?.commit()
    }

    override fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "patrykjurgielewicz91@gmail.com", null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug description")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Provide as much detail as possible")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }
}
