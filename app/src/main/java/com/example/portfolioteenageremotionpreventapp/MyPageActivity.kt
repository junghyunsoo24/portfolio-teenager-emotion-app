package com.example.portfolioteenageremotionpreventapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.databinding.ActivityMypageBinding

class MyPageActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    private val chatBotKey = "chat_history"
    private val expertKey = "expert_history"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "마이 페이지"

        actionBar?.setDisplayHomeAsUpEnabled(true)

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

    private fun onLogoutButtonClicked(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun clearChatHistory() {
        val chatBot = getSharedPreferences(chatBotKey, Context.MODE_PRIVATE)
        val chatBotEditor = chatBot.edit()

        val expert = getSharedPreferences(expertKey, Context.MODE_PRIVATE)
        val expertEditor = expert.edit()

        chatBotEditor.remove(viewModel.getUserId().value)
        chatBotEditor.apply()

        expertEditor.remove(viewModel.getUserId().value)
        expertEditor.apply()
    }
}