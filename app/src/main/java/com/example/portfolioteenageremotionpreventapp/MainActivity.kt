package com.example.portfolioteenageremotionpreventapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = AppViewModel.getInstance()

        val actionBar: ActionBar? = supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "하루친구(청소년)"

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linkTextView: TextView = findViewById(R.id.icon_url)
        linkTextView.setOnClickListener {
            openLink()
        }

        binding.childBtn.setOnClickListener {
            onStartButtonClicked()
        }

    }

    private fun onStartButtonClicked() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun openLink() {
        // 링크 주소를 여기에 추가
        val url = "https://Icons8.com"

        // 인텐트를 사용하여 브라우저 열기
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}