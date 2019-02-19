package com.jurgielewicz.myquizandroid.ui.view


import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jurgielewicz.myquizandroid.R
import com.jurgielewicz.myquizandroid.ui.contract.DashboardFragmentContract
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class DashboardFragment : Fragment(), DashboardFragmentContract.View {
    private val presenter: DashboardFragmentContract.Presenter by inject { parametersOf(this) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun setUserPhoto(bitmap: Bitmap) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startGame() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendEmail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
