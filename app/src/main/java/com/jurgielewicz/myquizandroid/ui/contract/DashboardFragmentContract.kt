package com.jurgielewicz.myquizandroid.ui.contract

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.jurgielewicz.myquizandroid.utils.BasePresenter
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface DashboardFragmentContract {
    interface View {
        fun setUserPhoto(bitmap: Bitmap)
        fun startGame()
        fun sendEmail()

    }
    interface Presenter: BasePresenter {
        fun getPhoto()

    }

    interface Model {
        fun downloadPhoto(url: String): Observable<Bitmap>
        fun getUser(): Observable<FirebaseUser>
    }
}