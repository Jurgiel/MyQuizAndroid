package com.jurgielewicz.myquizandroid.ui.model

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.jurgielewicz.myquizandroid.model.Score
import com.jurgielewicz.myquizandroid.ui.contract.DashboardFragmentContract
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class DashboardFragmentModel(): DashboardFragmentContract.Model {
    private val auth by lazy {
        FirebaseAuth.getInstance()

    }
    override fun downloadPhoto(url: String): Observable<Bitmap> = Observable.fromCallable {
        return@fromCallable Picasso.get().load(url).resize(350, 350).transform(CropCircleTransformation()).get()
    }

    override fun getUser(): Observable<FirebaseUser> = io.reactivex.Observable.create{ it ->
        val user = auth.currentUser
        it.onNext(user!!)
    }

    override fun getTopScorers(ref: DatabaseReference): Observable<MutableList<Score?>> = Observable.create {
        ref.orderByChild("score").limitToLast(20).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                it.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                var leaderboard = mutableListOf<Score?>()
                for(scoreSnapshot in p0.children){
                    leaderboard.add(scoreSnapshot.getValue(Score::class.java))
                }
                leaderboard.reverse()
                it.onNext(leaderboard)
                it.onComplete()
            }
        })
    }
}