package com.example.portfolioteenageremotionpreventapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.databinding.ActivityMypageBinding

class MyPageActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    private val sharedPreferencesKey = "chat_history"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        viewModel = AppViewModel.getInstance()

        binding.memberId.text = "아이디: " + viewModel.getUserId().value

        binding.logoutBtn.setOnClickListener {
            clearChatHistory()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("로그아웃")
            builder.setMessage("로그아웃 완료")

            builder.setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
                onLogoutButtonClicked()
            }
            builder.show()
        }
    }

    fun onLogoutButtonClicked(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun clearChatHistory() {
        val sharedPreferences = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove(viewModel.getUserId().value)
        editor.apply()
    }
}