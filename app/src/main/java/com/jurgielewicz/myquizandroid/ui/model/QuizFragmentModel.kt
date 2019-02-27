package com.jurgielewicz.myquizandroid.ui.model


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.model.Score
import com.jurgielewicz.myquizandroid.ui.contract.QuizFragmentContract
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class QuizFragmentModel: QuizFragmentContract.Model {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun setUserScore(ref: DatabaseReference, score: Score) { //works
        Observable.just(ref).subscribeOn(Schedulers.io()).subscribe { it.setValue(score) }
    }


    override fun getUserScore(ref: DatabaseReference): Observable<Score> = Observable.create { emitter -> //works
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                var score = p0.getValue(Score::class.java)
                if(score == null ){
                    score = Score(auth.uid!!,  0) //kotlin npe
                }
                    emitter.onNext(score)
                    emitter.onComplete()

            }
        })
    }

    override fun getQuestionList(ref:DatabaseReference): Observable<MutableList<Question?>> = Observable.create { emitter -> //works
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                var questions = mutableListOf<Question?>()
                for (questionSnapshot in p0.children){
                    questions.add(questionSnapshot.getValue(Question::class.java))
                }
                emitter.onNext(questions)
                emitter.onComplete()
            }
        })
    }
}