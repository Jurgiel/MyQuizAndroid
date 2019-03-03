package com.jurgielewicz.myquizandroid.ui.contract

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.jurgielewicz.myquizandroid.utils.BasePresenter

interface LoginFragmentContract {
    interface View {
        fun onLoginResponse(isLoginSuccess: Boolean)
        fun makeToast(message: String?)
        fun initFbLoginButton()
        fun initGoogleLogin()
    }
    interface Presenter: BasePresenter {
        fun handleFacebookAccessToken(accessToken: AccessToken?)
        fun signOut()
        fun handleGoogleAccessToken(account: GoogleSignInAccount?)
        fun isLoggedIn()

    }
}