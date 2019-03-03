package com.jurgielewicz.myquizandroid.ui.presenter

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.jurgielewicz.myquizandroid.ui.contract.DashboardFragmentContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DashboardFragmentPresenter(private val v: DashboardFragmentContract.View, private val model: DashboardFragmentContract.Model): DashboardFragmentContract.Presenter {

    private val scoreReference by lazy {
        FirebaseDatabase.getInstance().reference.child("scores")
    }
    private val compositeDisposable: CompositeDisposable? = null

    override fun getPhoto() {
      val disposable =  model.getUser().flatMap { val url = it.photoUrl.toString()
                    return@flatMap model.downloadPhoto(url)
                            .subscribeOn(Schedulers.io()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {it -> v.setUserPhoto(it)},
                        {error -> Log.d("Photo download error: ", error.message)}
                )
        compositeDisposable?.add(disposable)
        }

    override fun getLeaderboard() {
        val disposable = model.getTopScorers(scoreReference).subscribe { v.setLeaderboard(it) }
        compositeDisposable?.add(disposable)
    }

    override fun onCreated() {
        getPhoto()
    }

    override fun onDestroyed() {
        compositeDisposable?.dispose()
    }
}