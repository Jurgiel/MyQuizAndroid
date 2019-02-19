package com.jurgielewicz.myquizandroid.di

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jurgielewicz.myquizandroid.ui.contract.DashboardFragmentContract
import com.jurgielewicz.myquizandroid.ui.contract.LoginFragmentContract
import com.jurgielewicz.myquizandroid.ui.contract.QuizFragmentContract
import com.jurgielewicz.myquizandroid.ui.model.DashboardFragmentModel
import com.jurgielewicz.myquizandroid.ui.presenter.DashboardFragmentPresenter
import com.jurgielewicz.myquizandroid.ui.presenter.LoginFragmentPresenter
import com.jurgielewicz.myquizandroid.ui.presenter.QuizFragmentPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

class AppModule {

    private val googleModule = module {
        single {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("969447063804-e6n02a3u91904msm67g1c2r43i5n60rt.apps.googleusercontent.com")
                    .requestEmail()
                    .build()
        }

        single<GoogleSignInClient>{
            GoogleSignIn.getClient(androidContext(), get())
        }
    }

    private val presenters = module {
        factory<LoginFragmentContract.Presenter> { (v: LoginFragmentContract.View) ->
            LoginFragmentPresenter(v)
        }
        factory<DashboardFragmentContract.Presenter> { (v: DashboardFragmentContract.View) ->
            DashboardFragmentPresenter(v, get())
        }
        factory<QuizFragmentContract.Presenter> { (v: QuizFragmentContract.View) ->
            QuizFragmentPresenter(v)
        }
    }

    private val models = module {
        factory<DashboardFragmentContract.Model> { DashboardFragmentModel() }
    }

    val app = listOf(googleModule, presenters, models)
}