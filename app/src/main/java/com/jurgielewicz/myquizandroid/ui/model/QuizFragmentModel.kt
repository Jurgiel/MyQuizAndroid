package com.jurgielewicz.myquizandroid.ui.model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.model.Score
import com.jurgielewicz.myquizandroid.ui.contract.QuizFragmentContract
import io.reactivex.Observable

class QuizFragmentModel: QuizFragmentContract.Model {
    val databaseReference = FirebaseDatabase.getInstance().reference
    override fun getUserScore(uid:String): Observable<Score>{
        return Observable.create{subscriber ->
            run {
                databaseReference.child("scores").child(uid).addValueEventListener(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        Log.d("Error","firebase")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val list = mutableListOf<Question>()
                        val score:Score = p0.getValue(Score::class.java)!!
                        subscriber.onNext(score)

                    }
                })
            }
        }
    }
}