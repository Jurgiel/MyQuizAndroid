package com.jurgielewicz.myquizandroid.ui.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jurgielewicz.myquizandroid.R
import com.jurgielewicz.myquizandroid.model.Question
import com.jurgielewicz.myquizandroid.ui.contract.AddQuestionFragmentContract
import kotlinx.android.synthetic.main.fragment_add_question.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class AddQuestionFragment : Fragment(), View.OnClickListener, AddQuestionFragmentContract.View {

    private val presenter: AddQuestionFragmentContract.Presenter by inject { parametersOf(this) }
    private lateinit var rootView: View
    private lateinit var correctAnswer: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_add_question, container, false)
        rootView.addquestion_btn
        rootView.sendquestion_layout.setOnClickListener(this)
        rootView.back_addquestion.setOnClickListener(this)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreated()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            rootView.sendquestion_layout.id -> getUserQuestion()
            rootView.back_addquestion.id -> activity?.onBackPressed()
        }
    }

    override fun getUserQuestion() {
        if(rootView.userQuestion.text.isEmpty()||
                rootView.userAnswerA.text.isEmpty() ||
                rootView.userAnswerB.text.isEmpty() ||
                rootView.userAnswerC.text.isEmpty() ||
                rootView.userAnswerD.text.isEmpty() ||
                rootView.radioGroup_correctAnswer.checkedRadioButtonId == -1) {
            makeToast("Fields can't be blank and correct answer have to be checked!")
        } else {
            val id = rootView.radioGroup_correctAnswer.checkedRadioButtonId

            when(id){
                rootView.radioA.id -> correctAnswer = rootView.userAnswerA.text.toString()
                rootView.radioB.id ->correctAnswer = rootView.userAnswerB.text.toString()
                rootView.radioC.id ->correctAnswer = rootView.userAnswerC.text.toString()
                rootView.radioD.id ->correctAnswer = rootView.userAnswerD.text.toString()
            }
            presenter.
                    addUserQuestion(Question(rootView.userAnswerA.text.toString(),
                            rootView.userAnswerB.text.toString(),
                            rootView.userAnswerC.text.toString(),
                            rootView.userAnswerD.text.toString(),
                            correctAnswer, rootView.userQuestion.text.toString()))
        }
    }

    override fun clearFields() {
        rootView.userQuestion.text.clear()
        rootView.userAnswerA.text.clear()
        rootView.userAnswerB.text.clear()
        rootView.userAnswerC.text.clear()
        rootView.userAnswerD.text.clear()
        rootView.radioGroup_correctAnswer.clearCheck()
    }

    override fun makeToast(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyed()
    }

}
