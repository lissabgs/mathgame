package com.example.test

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.test.ResultActivity
import com.example.test.R
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var txtQuestion: TextView
    private lateinit var edtAnswer: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnNext: Button
    private lateinit var txtFeedback: TextView

    private var currentQuestion = 0
    private var score = 0
    private var correctAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        txtQuestion = findViewById<TextView>(R.id.txtQuestion)
        edtAnswer = findViewById(R.id.edtAnswer)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnNext = findViewById(R.id.btnNext)
        txtFeedback = findViewById(R.id.txtFeedback)

        generateQuestion()

        btnSubmit.setOnClickListener {
            val userAnswer = edtAnswer.text.toString().toIntOrNull()
            if (userAnswer == correctAnswer) {
                window.decorView.setBackgroundColor(Color.GREEN)
                score += 20
                txtFeedback.text = "Correto!"
            } else {
                window.decorView.setBackgroundColor(Color.RED)
                txtFeedback.text = "Errado! Resposta correta: $correctAnswer"
            }
            btnSubmit.isEnabled = false
            btnNext.isEnabled = true
        }

        btnNext.setOnClickListener {
            currentQuestion++
            if (currentQuestion == 5) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("score", score)
                startActivity(intent)
                finish()
            } else {
                generateQuestion()
                window.decorView.setBackgroundColor(Color.WHITE)
                txtFeedback.text = ""
                edtAnswer.text.clear()
                btnSubmit.isEnabled = true
                btnNext.isEnabled = false
            }
        }
    }

    private fun generateQuestion() {
        val num1 = Random.nextInt(0, 100)
        val num2 = Random.nextInt(0, num1 + 1)
        val op = if (Random.nextBoolean()) '+' else '-'

        correctAnswer = if (op == '+') num1 + num2 else num1 - num2
        txtQuestion.text = "$num1 $op $num2 = ?"
    }
}