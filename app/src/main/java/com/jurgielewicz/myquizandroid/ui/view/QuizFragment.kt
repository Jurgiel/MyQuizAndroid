package com.jurgielewicz.myquizandroid.ui.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.jurgielewicz.myquizandroid.R
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.ui.contract.QuizFragmentContract
import com.jurgielewicz.myquizandroid.utils.OnPlayAgainListener
import kotlinx.android.synthetic.main.fragment_quiz.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class QuizFragment : Fragment(), QuizFragmentContract.View, View.OnClickListener, OnPlayAgainListener {
    lateinit var rootView: View
    private val presenter: QuizFragmentContract.Presenter by inject { parametersOf(this) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_quiz, container, false)
        rootView.option1.setOnClickListener(this)
        rootView.option2.setOnClickListener(this)
        rootView.option3.setOnClickListener(this)
        rootView.option4.setOnClickListener(this)
        rootView.back_quiz.setOnClickListener(this)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreated()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            rootView.option1.id -> presenter.checkanswer(rootView.answerA.text.toString())
            rootView.option2.id -> presenter.checkanswer(rootView.answerB.text.toString())
            rootView.option3.id -> presenter.checkanswer(rootView.answerC.text.toString())
            rootView.option4.id -> presenter.checkanswer(rootView.answerD.text.toString())
            rootView.back_quiz.id -> activity?.onBackPressed()

        }
    }
    override fun updateQuestion(question: Question?) {
        rootView.question_TextView.text =  question?.Question
        rootView.answerA.text = question?.AnswerA
        rootView.answerB.text = question?.AnswerB
        rootView.answerC.text = question?.AnswerC
        rootView.answerD.text = question?.AnswerD
    }

    override fun updateTimer(long: Long) {
        rootView.timer.text = "0:$long"
    }

    override fun makeToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun minusLife(int: Int) {
        when(int){
            2 -> rootView.life1.visibility = View.INVISIBLE
            1 -> rootView.life2.visibility = View.INVISIBLE
            0 -> rootView.life3.visibility = View.INVISIBLE
        }
    }

    override fun lifeVisible() {
        rootView.life1.visibility = View.VISIBLE
        rootView.life2.visibility = View.VISIBLE
        rootView.life3.visibility = View.VISIBLE
    }

    override fun notClickable() {
        rootView.option1.isClickable = false
        rootView.option2.isClickable = false
        rootView.option3.isClickable = false
        rootView.option4.isClickable = false
    }

    override fun setClickable() {
        rootView.option1.isClickable = true
        rootView.option2.isClickable = true
        rootView.option3.isClickable = true
        rootView.option4.isClickable = true
    }

    override fun showDialog(score: Int) {
        val ft = activity?.supportFragmentManager
        val newFragment = QuizDialogFragment.newInstance(score)
        newFragment.setTargetFragment(this, 0)
        newFragment.show(ft, "")
    }

    override fun onPlayAgain() {
        presenter.onCreated()
        Log.d("OnPlayAgain", "clicked")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyed()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDestroyed()
    }

}
