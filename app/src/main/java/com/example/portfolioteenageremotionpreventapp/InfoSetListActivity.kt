package com.example.portfolioteenageremotionpreventapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.databinding.ActivityInfosetlistBinding
import com.example.portfolioteenageremotionpreventapp.infoSet.InfoSetListApi
import com.example.portfolioteenageremotionpreventapp.infoSet.InfoSetListData
import com.example.portfolioteenageremotionpreventapp.infoSet.InfoSetListDataResponse
import kotlinx.coroutines.launch

class InfoSetListActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var viewModel: AppViewModel
    private lateinit var binding: ActivityInfosetlistBinding
    private lateinit var result: InfoSetListDataResponse

    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var name: String
    private lateinit var ageInput: String
    private lateinit var address: String
    private lateinit var gender: String
    private lateinit var phone_num: String

    private var age = 0
    private val assignments = 0
    private lateinit var genderValue: String

    private lateinit var baseUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfosetlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "개인정보수정"

        actionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = AppViewModel.getInstance()

        val genders = arrayOf("남", "여")
        val spinner = findViewById<Spinner>(R.id.spinner_gender)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        id = viewModel.getUserId().value.toString()
        binding.idInput.text = id

        binding.infoSetBtn.setOnClickListener {
            pw = binding.firstPwdInput.text.toString()
            if(pw == "") {
                pw = viewModel.getUserPwd().value.toString()
            }
            Log.e("pwd", pw)

            name = binding.nameInput.text.toString()
            if(name == "") {
                name = viewModel.getUserName().value.toString()
            }

            ageInput = binding.ageInput.text.toString()
            age = if (ageInput.isNotEmpty()) {
                if (ageInput.toInt() in 0..100 && ageInput.toInt() != viewModel.getUserAge().value) {
                    ageInput.toInt()
                } else{
                    viewModel.getUserAge().value!!
                }

            } else {
                viewModel.getUserAge().value!!
            }

            address = binding.addressInput.text.toString()
            if(address == "") {
                address = viewModel.getUserAddress().value.toString()
            }

            genderValue?.let { selectedGender ->
                gender = if (selectedGender == "남") "0" else "1"
                viewModel.setUserGender(gender)
            }

            phone_num = binding.addressInput.text.toString()
            if(phone_num == "") {
                phone_num = viewModel.getUserPhoneNum().value.toString()
            }

            baseUrl = resources.getString(R.string.api_ip_server)
            mobileToServer()
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("개인정보수정 성공")
        builder.setMessage("개인정보수정 완료")
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {

                val message = InfoSetListData(id,pw, name, age, address, gender, phone_num)
                val response = InfoSetListApi.retrofitService(baseUrl).sendsMessage(message)
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

                        viewModel.setUserPwd(pw)
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

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: android.view.View?,
        position: Int,
        id: Long
    ) {
        genderValue = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}