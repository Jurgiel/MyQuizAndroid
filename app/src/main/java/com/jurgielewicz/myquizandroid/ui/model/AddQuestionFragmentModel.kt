package com.jurgielewicz.myquizandroid.ui.model

import com.google.firebase.database.DatabaseReference
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.ui.contract.AddQuestionFragmentContract
import io.reactivex.Observable

class AddQuestionFragmentModel: AddQuestionFragmentContract.Model {

    override fun addQuestion(ref: DatabaseReference, question: Question?): Observable<DatabaseReference> = Observable.create {
        ref.setValue(question) { p0, p1 ->
            if (p0 == null){
                it.onNext(p1)
                it.onComplete()
            } else {
                it.onError(p0.toException())
            }
        }
    }
}


