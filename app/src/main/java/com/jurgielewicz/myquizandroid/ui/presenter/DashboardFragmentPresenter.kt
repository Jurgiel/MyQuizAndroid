package com.jurgielewicz.myquizandroid.ui.presenter

import android.util.Log
import com.jurgielewicz.myquizandroid.ui.contract.DashboardFragmentContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DashboardFragmentPresenter(private val v: DashboardFragmentContract.View, private val model: DashboardFragmentContract.Model): DashboardFragmentContract.Presenter {

    override fun getPhoto() {
       model.getUser().flatMap { val url = it.photoUrl.toString()
                    return@flatMap model.downloadPhoto(url)
                            .subscribeOn(Schedulers.io()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {it -> v.setUserPhoto(it)},
                        {error -> Log.d("Photo download error: ", error.message)}
                )
        }


    override fun onCreated() {
        getPhoto()
    }

    override fun onDestroyed() {
    }
}