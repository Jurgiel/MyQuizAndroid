package com.jurgielewicz.myquizandroid.ui.presenter

import android.util.Log
import com.androidhuman.rxfirebase2.auth.rxGetCurrentUser
import com.google.firebase.auth.FirebaseAuth
import com.jurgielewicz.myquizandroid.ui.contract.DashboardFragmentContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DashboardFragmentPresenter(private val v: DashboardFragmentContract.View, private val dashboardFragmentModel: DashboardFragmentContract.Model): DashboardFragmentContract.Presenter {
    private val auth by lazy {
        FirebaseAuth.getInstance()

    }

    override fun getPhoto() {
        auth.rxGetCurrentUser()
                .flatMap {
                    val url = it.photoUrl.toString()
                    return@flatMap dashboardFragmentModel.downloadPhoto(url)
                            .subscribeOn(Schedulers.io())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { it -> v.setUserPhoto(it) },
                        { error -> Log.d("Photo download error", error.message) })
    }

    override fun onCreated() {
    }

    override fun onDestroyed() {
    }
}