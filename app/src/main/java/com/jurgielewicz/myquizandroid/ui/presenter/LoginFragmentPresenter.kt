package com.jurgielewicz.myquizandroid.ui.presenter

import android.util.Log
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.*
import com.jurgielewicz.myquizandroid.ui.contract.LoginFragmentContract
import durdinapps.rxfirebase2.RxFirebaseAuth

class LoginFragmentPresenter(private val v: LoginFragmentContract.View): LoginFragmentContract.Presenter {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun handleFacebookAccessToken( token: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(token!!.token) //found string? but need string

        RxFirebaseAuth.signInWithCredential(auth, credential).subscribe(
                {it -> v.makeToast("Logged in!")
                v.onLoginResponse(true)},
                {error -> v.makeToast(error.message)}
        )
//        auth.signInWithCredential(credential)
//                .(
//                        {v.makeToast("Logged in")
//                        v.onLoginResponse(true)},
//                        {error -> Log.d("LoginError", error.message)}
//                )
    }

    override fun handleGoogleAccessToken(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        RxFirebaseAuth.signInWithCredential(auth, credential).subscribe(
                {it -> v.makeToast("Logged in!")
                v.onLoginResponse(true)},
                { error -> v.makeToast(error.message)}
        )
//        auth.rxSignInWithCredential(credential)
//                .subscribe(
//                        {v.makeToast("Logged in")
//                        v.onLoginResponse(true)},
//                        {error -> Log.d("LoginError", error.message)}
//                )
    }



    override fun signOut() {
        auth.signOut()
        v.makeToast("Logout")
        auth.currentUser?.displayName
    }
}