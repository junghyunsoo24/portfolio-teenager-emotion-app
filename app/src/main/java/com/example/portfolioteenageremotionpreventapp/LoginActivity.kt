package com.example.portfolioteenageremotionpreventapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var id: String
    private lateinit var pw: String

    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = AppViewModel.getInstance()

    }
}