package com.jurgielewicz.myquizandroid.ui.presenter

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.model.Score
import com.jurgielewicz.myquizandroid.ui.contract.QuizFragmentContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class QuizFragmentPresenter(private val v: QuizFragmentContract.View, private val model: QuizFragmentContract.Model): QuizFragmentContract.Presenter {
    private var total = 1
    private val reference by lazy {
        FirebaseDatabase.getInstance().reference.child("Questions")
    }

    private val scoreReference by lazy {
        FirebaseDatabase.getInstance().reference.child("scores")
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }
    private val uid = auth.uid!!

    private var questions = mutableListOf<Question?>()
    private var correctAnswer: String? = null
    private var times: Long = 30
    private var lifes = 3
    private var disposableTimer: Disposable? = null
    private var correctAnswers: Int = 0
    private val compositeDisposable: CompositeDisposable? = null

    override fun loadQuestions() {
        val disposable = model.getQuestionList(reference).map { questions = it }
                 .subscribeOn(Schedulers.io())
                 .subscribe { getQuestion() }
        compositeDisposable?.add(disposable)
    }

    override fun getQuestion() {
        if (total <= questions.size) {
            v.updateQuestion(questions[total])
            correctAnswer = questions[total]?.CorrectAnswer
            total++

        }
    }

    override fun checkanswer(answer: String) {
        when(answer){
            correctAnswer -> {
                getQuestion()
                correctAnswers++
            }
            else -> {
                menageLifes()
            }
        }
    }

    override fun timer() {
        disposableTimer = Observable.timer(1, TimeUnit.SECONDS)
                .repeat(times)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<Long>() {
                    override fun onComplete() {
                        v.makeToast("No time left")
                        v.notClickable()
                        v.showDialog(correctAnswers)
                        saveUserScore()
                        disposableTimer?.dispose()
                    }

                    override fun onNext(t: Long) {
                        times--
                        v.updateTimer(times.toString())
                    }

                    override fun onError(e: Throwable) {
                        Log.d("Timer error", e.message)
                    }
                })
    }

    override fun menageLifes() {
        lifes--
        v.minusLife(lifes)
        if(lifes == -1){
            disposableTimer?.dispose()
            v.makeToast("No life left")
            v.notClickable()
            saveUserScore()
            v.showDialog(correctAnswers)
        }
    }

    override fun saveUserScore() {
        if (correctAnswers > 0) {

           val disposable =  model.getUserScore(scoreReference.child(uid))
                    .flatMap {
                        var score = Score(it.name, it.score + correctAnswers)
                        return@flatMap Observable.just(score)
                    }
                    .subscribe { model.setUserScore(scoreReference.child(uid), it) }
            compositeDisposable?.add(disposable)
        }
    }

    override fun onCreated() {
        times = 30
        lifes = 3
        v.lifeVisible()
        v.setClickable()
        loadQuestions()
        timer()
    }

    override fun onDestroyed() {
        compositeDisposable?.dispose()
        disposableTimer?.dispose()
    }
}