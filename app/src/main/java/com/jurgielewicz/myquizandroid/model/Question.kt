package com.jurgielewicz.myquizandroid.model

data class Question(val AnswerA: String? = "",
                    val AnswerB: String? = "",
                    val AnswerC: String? = "",
                    val AnswerD: String? = "",
                    val CorrectAnswer: String? = "",
                    val Question: String? = "")