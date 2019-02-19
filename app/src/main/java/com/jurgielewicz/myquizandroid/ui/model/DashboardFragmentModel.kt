package com.jurgielewicz.myquizandroid.ui.model

import android.graphics.Bitmap
import com.jurgielewicz.myquizandroid.ui.contract.DashboardFragmentContract
import com.squareup.picasso.Picasso
import io.reactivex.Maybe
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class DashboardFragmentModel(): DashboardFragmentContract.Model {

    override fun downloadPhoto(url: String): Maybe<Bitmap> = Maybe.fromCallable {
        return@fromCallable Picasso.get().load(url).resize(350, 350).transform(CropCircleTransformation()).get()
    }


}