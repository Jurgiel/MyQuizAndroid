package com.jurgielewicz.myquizandroid.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jurgielewicz.myquizandroid.R
import com.jurgielewicz.myquizandroid.utils.OnPlayAgainListener
import kotlinx.android.synthetic.main.fragment_quiz_dialog.view.*

class QuizDialogFragment : DialogFragment() {

    val TAG = "QuizDialogFragment"
    private lateinit var callback: OnPlayAgainListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            callback = targetFragment as  OnPlayAgainListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Calling fragment must implement Callback interface")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragment_quiz_dialog, container, false)

        view.yourScore_TextView.text = "YOUR SCORE: ${arguments?.getInt("correctAnswers")}"
        view.home_btn.setOnClickListener {
            dismiss()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentHolder, DashboardFragment())?.commit()
        }
        view.playAgain_btn.setOnClickListener {
            dismiss()
            Log.d(TAG, "Play again button clicked")
            callback.onPlayAgain()
        }
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return view
    }

    companion object {
        fun newInstance(num: Int): QuizDialogFragment {
            val f = QuizDialogFragment()
            val args = Bundle()
            args.putInt("correctAnswers", num)
            f.arguments = args
            return f
        }
    }
}