package com.jurgielewicz.myquizandroid.ui.presenter

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jurgielewicz.myquizandroid.ui.contract.LoginFragmentContract
import durdinapps.rxfirebase2.RxFirebaseAuth
import io.reactivex.disposables.CompositeDisposable

class LoginFragmentPresenter(private val v: LoginFragmentContract.View): LoginFragmentContract.Presenter {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }
    private val compositeDisposable: CompositeDisposable? = null

    override fun handleFacebookAccessToken( token: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(token!!.token) //found string? but need string

        val disposable = RxFirebaseAuth.signInWithCredential(auth, credential).subscribe(
                {it -> v.makeToast("Logged in!")
                v.onLoginResponse(true)},
                {error -> v.makeToast(error.message)}
        )
        compositeDisposable?.add(disposable)
    }

    override fun handleGoogleAccessToken(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        val disposable =RxFirebaseAuth.signInWithCredential(auth, credential).subscribe(
                {it -> v.makeToast("Logged in!")
                v.onLoginResponse(true)},
                { error -> v.makeToast(error.message)}
        )
        compositeDisposable?.add(disposable)
    }

    override fun isLoggedIn() {
        if(auth.currentUser != null){
            v.onLoginResponse(true)
        }
    }

    override fun signOut() {
        auth.signOut()
        v.makeToast("Logout")
        auth.currentUser?.displayName
    }

    override fun onCreated() {
    }

    override fun onDestroyed() {
        compositeDisposable?.dispose()
    }
}