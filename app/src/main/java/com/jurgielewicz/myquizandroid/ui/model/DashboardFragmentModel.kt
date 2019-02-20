package com.jurgielewicz.myquizandroid.ui.model

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jurgielewicz.myquizandroid.ui.contract.DashboardFragmentContract
import com.squareup.picasso.Picasso
import io.reactivex.Maybe
import io.reactivex.Observable
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class DashboardFragmentModel(): DashboardFragmentContract.Model {
    private val auth by lazy {
        FirebaseAuth.getInstance()

    }
    override fun downloadPhoto(url: String): Observable<Bitmap> = Observable.fromCallable {
        return@fromCallable Picasso.get().load(url).resize(350, 350).transform(CropCircleTransformation()).get()
    }

    override fun getUser(): Observable<FirebaseUser> = io.reactivex.Observable.create{ it ->
        val user = auth.currentUser
        it.onNext(user!!)

    }

}