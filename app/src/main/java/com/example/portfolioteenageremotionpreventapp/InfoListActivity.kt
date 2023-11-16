package com.example.portfolioteenageremotionpreventapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.databinding.ActivityInfolistBinding
import com.example.portfolioteenageremotionpreventapp.infoList.InfoListApi
import com.example.portfolioteenageremotionpreventapp.infoList.InfoListData
import com.example.portfolioteenageremotionpreventapp.infoList.InfoListDataResponse
import kotlinx.coroutines.launch

class InfoListActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    private lateinit var binding: ActivityInfolistBinding
    private lateinit var result: InfoListDataResponse
    private lateinit var id: String
    private lateinit var baseUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfolistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "마이페이지"

        actionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = AppViewModel.getInstance()

        id = viewModel.getUserId().value.toString()

        baseUrl = resources.getString(R.string.api_ip_server)
        mobileToServer()
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = InfoListData(id)
                val response = InfoListApi.retrofitService(baseUrl).sendsMessage(message)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // 서버 응답을 확인하는 작업 수행
                        result = responseBody
                        viewModel.setUserName(result.name)
                        viewModel.setUserAge(result.age)
                        viewModel.setUserAddress(result.address)
                        viewModel.setUserGender(result.gender)
                        viewModel.setUserPhoneNum(result.phone_num)

                        binding.idInput.text = viewModel.getUserId().value.toString()
                        binding.pwdInput.text = viewModel.getUserPwd().value.toString()
                        binding.nameInput.text = viewModel.getUserName().value.toString()
                        binding.ageInput.text = viewModel.getUserAge().value.toString()
                        binding.addressInput.text = viewModel.getUserAddress().value.toString()
                        binding.genderInput.text = viewModel.getUserGender().value.toString()
                        binding.phoneInput.text = viewModel.getUserPhoneNum().value.toString()
                    } else {
                        Log.e("@@@@Error3", "Response body is null")
                    }
                } else {
                    Log.e("@@@@Error2", "Response not successful: ${response.code()}")
                }
            } catch (Ex: Exception) {
                Log.e("@@@@Error1", Ex.stackTraceToString())
            }
        }
    }
}