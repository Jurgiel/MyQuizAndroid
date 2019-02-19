package com.jurgielewicz.myquizandroid.ui.view


import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jurgielewicz.myquizandroid.R
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.ui.contract.DashboardFragmentContract
import com.jurgielewicz.myquizandroid.ui.contract.QuizFragmentContract
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class QuizFragment : Fragment(), QuizFragmentContract.View {
    private val presenter: DashboardFragmentContract.Presenter by inject { parametersOf(this) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun updateQuestion(question: Question?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateTimer(long: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun makeToast(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun minusLife(int: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lifeVisible() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun notClickable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setClickable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDialog(score: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPlayAgain() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
