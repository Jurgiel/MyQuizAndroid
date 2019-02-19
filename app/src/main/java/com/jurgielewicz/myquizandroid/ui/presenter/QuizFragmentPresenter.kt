package com.jurgielewicz.myquizandroid.ui.presenter

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.jurgielewicz.myquizandroid.ui.contract.QuizFragmentContract
import io.reactivex.Observable

class QuizFragmentPresenter(private val v: QuizFragmentContract.View): QuizFragmentContract.Presenter {
    override fun loadQuestions() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getQuestion() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkanswer(answer: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun timer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun menageLifes() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveScore(i: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun rxfirebase(ref: Query): Observable<DataSnapshot> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserScore() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreated() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroyed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}