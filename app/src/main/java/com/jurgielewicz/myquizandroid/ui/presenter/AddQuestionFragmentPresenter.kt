package com.jurgielewicz.myquizandroid.ui.presenter

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.ui.contract.AddQuestionFragmentContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddQuestionFragmentPresenter(private val view: AddQuestionFragmentContract.View, private val model: AddQuestionFragmentContract.Model): AddQuestionFragmentContract.Presenter {

    private val userQuestionRef = FirebaseDatabase.getInstance().reference.child("userQuestions")
    private val compositeDisposable: CompositeDisposable? = null

    override fun addUserQuestion(question: Question?) {
        val disposable = model.addQuestion(userQuestionRef.child(random()), question)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { view.clearFields()
                            view.makeToast("Question has been sent!")},
                        {error ->view.makeToast(error.message)
                            Log.d("AddQuestionFragmentPresenter", error.message)}
                )
        compositeDisposable?.add(disposable)
    }

    override fun onCreated() {

    }

    override fun onDestroyed() {
        compositeDisposable?.dispose()
    }

    override fun random(): String = (-999999999..999999999).random().toString()
}