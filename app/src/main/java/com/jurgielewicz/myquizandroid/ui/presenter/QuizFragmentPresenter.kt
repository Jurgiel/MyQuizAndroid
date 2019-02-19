package com.jurgielewicz.myquizandroid.ui.presenter

import android.util.Log
import com.google.firebase.database.*
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.ui.contract.QuizFragmentContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class QuizFragmentPresenter(private val v: QuizFragmentContract.View): QuizFragmentContract.Presenter {
    private var total = 1
    private val reference by lazy {
        FirebaseDatabase.getInstance().reference.child("Questions")
    }

    private val scoreReference by lazy {
        FirebaseDatabase.getInstance().reference.child("scores")
    }
    private var questions = mutableListOf<Question?>()
    private var correctAnswer: String? = null
    private var times: Long = 10
    private var lifes = 3
    private var disposable: Disposable? = null
    private var correctAnswers: Int = 0

    override fun loadQuestions() {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (questionSnapshot in p0.children){
                    questions.add(questionSnapshot.getValue(Question::class.java))
                }
                getQuestion()
            }
        })
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
        disposable = Observable.timer(1, TimeUnit.SECONDS)
                .repeat(times)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<Long>() {
                    override fun onComplete() {
                        v.makeToast("No time left")
                        v.notClickable()
                        v.showDialog(correctAnswers)
                        disposable?.dispose()
                    }

                    override fun onNext(t: Long) {
                        times--
                        v.updateTimer(times)
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
            disposable?.dispose()
            v.makeToast("No life left")
            v.notClickable()
            v.showDialog(correctAnswers)
        }
    }

    override fun onCreated() {
        times = 10
        lifes = 3
        v.lifeVisible()
        v.setClickable()
        loadQuestions()
        timer()
    }

    override fun onDestroyed() {
    }
}