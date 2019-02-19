package com.jurgielewicz.myquizandroid.ui.presenter

import android.util.Log
import com.androidhuman.rxfirebase2.auth.rxSignInWithCredential
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.*
import com.jurgielewicz.myquizandroid.ui.contract.LoginFragmentContract

class LoginFragmentPresenter(private val v: LoginFragmentContract.View): LoginFragmentContract.Presenter {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun handleFacebookAccessToken( token: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(token!!.token) //found string? but need string
        auth.rxSignInWithCredential(credential)
                .subscribe(
                        {v.makeToast("Logged in")
                        v.onLoginResponse(true)},
                        {error -> Log.d("LoginError", error.message)}
                )
    }

    override fun handleGoogleAccessToken(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        auth.rxSignInWithCredential(credential)
                .subscribe(
                        {v.makeToast("Logged in")
                        v.onLoginResponse(true)},
                        {error -> Log.d("LoginError", error.message)}
                )
    }



    override fun signOut() {
        auth.signOut()
        v.makeToast("Logout")
        auth.currentUser?.displayName
    }
}