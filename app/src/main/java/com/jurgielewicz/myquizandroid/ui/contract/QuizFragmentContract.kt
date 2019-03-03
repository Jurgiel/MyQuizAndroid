package com.jurgielewicz.myquizandroid.ui.contract

import com.google.firebase.database.DatabaseReference
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.model.Score
import com.jurgielewicz.myquizandroid.utils.BasePresenter
import io.reactivex.Observable

interface QuizFragmentContract {
    interface View {
        fun updateQuestion(question: Question?)
        fun updateTimer(string: String)
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
        fun saveUserScore()

    }
    interface Model {
        fun setUserScore(ref: DatabaseReference, score: Score)
        fun getUserScore(ref: DatabaseReference): Observable<Score>
        fun getQuestionList(ref:DatabaseReference): Observable<MutableList<Question?>>
    }
}