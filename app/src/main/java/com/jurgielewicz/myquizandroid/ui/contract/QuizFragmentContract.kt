package com.jurgielewicz.myquizandroid.ui.contract

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.utils.BasePresenter
import io.reactivex.Observable

interface QuizFragmentContract {
    interface View {
        fun updateQuestion(question: Question?)
        fun updateTimer(long: Long)
        fun makeToast(message: String)
        fun minusLife(int: Int)
        fun lifeVisible()
        fun notClickable()
        fun setClickable()
        fun showDialog(score:Int)
        fun onPlayAgain()
    }
    interface Presenter : BasePresenter {
        fun loadQuestions()
        fun getQuestion()
        fun checkanswer(answer: String)
        fun timer()
        fun menageLifes()
        fun saveScore(i: Int)
        fun rxfirebase(ref: Query): Observable<DataSnapshot>
        fun getUserScore()
    }
    interface Model {

    }
}