package com.jurgielewicz.myquizandroid.ui.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jurgielewicz.myquizandroid.R
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.jurgielewicz.myquizandroid.ui.contract.LoginFragmentContract
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*


class LoginFragment : Fragment(), LoginFragmentContract.View, View.OnClickListener {
    private lateinit var rootView: View
    private val presenter: LoginFragmentContract.Presenter by inject { parametersOf(this) }
    private val callbackManager = CallbackManager.Factory.create()
    private val googleSignInClient: GoogleSignInClient by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter.isLoggedIn()
        rootView = inflater.inflate(R.layout.fragment_login, container, false)
        rootView.facebookButton.setOnClickListener(this)
        rootView.googleButton.setOnClickListener(this)
        return rootView
    }

    override fun onLoginResponse(isLoginSuccess: Boolean) {
        when(isLoginSuccess){
            true -> activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentHolder, DashboardFragment())?.commit()
        }
    }

    override fun makeToast(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun initFbLoginButton() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                presenter.handleFacebookAccessToken(result?.accessToken)
            }

            override fun onCancel() {
                makeToast("Canceled")
            }

            override fun onError(error: FacebookException?) {
                makeToast("Error")
                Log.d("initFbLoginError", error?.message)
            }
        })
    }

    override fun initGoogleLogin() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1 )
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            rootView.facebookButton.id -> initFbLoginButton()
            rootView.googleButton.id -> initGoogleLogin()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                presenter.handleGoogleAccessToken(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }
}