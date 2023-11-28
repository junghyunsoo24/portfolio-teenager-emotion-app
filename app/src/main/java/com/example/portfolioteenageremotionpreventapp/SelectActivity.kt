package com.example.portfolioteenageremotionpreventapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.databinding.ActivitySelectBinding

class SelectActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "하루친구(청소년)"

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel.getInstance()

        val linkTextView: TextView = findViewById(R.id.icon_url)
        linkTextView.setOnClickListener {
            openLink()
        }

        binding.ChatBotBtn.setOnClickListener {
            onAIButtonClicked()
        }

        binding.ExpertBtn.setOnClickListener {
            onExpertButtonClicked()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myPage_btn -> {
                val intent = Intent(this, InfoListActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onAIButtonClicked(){
        val intent = Intent(this, ChatBotActivity::class.java)
        startActivity(intent)
    }

    private fun onExpertButtonClicked(){
        val intent = Intent(this, ExpertChatActivity::class.java)
        startActivity(intent)
    }

    private fun openLink() {
        val url = "https://Icons8.com"

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}