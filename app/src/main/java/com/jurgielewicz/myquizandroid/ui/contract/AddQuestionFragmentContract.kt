package com.jurgielewicz.myquizandroid.ui.contract

import com.google.firebase.database.DatabaseReference
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.utils.BasePresenter
import io.reactivex.Observable

interface AddQuestionFragmentContract {

    interface View {
        fun getUserQuestion()
        fun clearFields()
        fun makeToast(message: String?)

    }

    interface Presenter: BasePresenter {
        fun addUserQuestion(question: Question?)
        fun random(): String
    }

    interface Model {
        fun addQuestion(ref: DatabaseReference, question: Question?): Observable<DatabaseReference>
    }
}