package com.jurgielewicz.myquizandroid.ui.contract

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface LoginFragmentContract {
    interface View {
        fun onLoginResponse(isLoginSuccess: Boolean)
        fun makeToast(message: String?)
        fun isLoggedIn(isLoggedIn: Boolean)
        fun initFbLoginButton()
        fun initGoogleLogin()
    }
    interface Presenter {
        fun handleFacebookAccessToken(accessToken: AccessToken?)
        fun signOut()
        fun handleGoogleAccessToken(account: GoogleSignInAccount?)
    }
}