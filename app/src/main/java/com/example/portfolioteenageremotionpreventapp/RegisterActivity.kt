package com.example.portfolioteenageremotionpreventapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.databinding.ActivityRegisterBinding
import com.example.portfolioteenageremotionpreventapp.register.RegisterApi
import com.example.portfolioteenageremotionpreventapp.register.RegisterData
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class RegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var viewModel: AppViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "회원가입"

        actionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = AppViewModel.getInstance()

        val genders = arrayOf("남", "여")
        val spinner = findViewById<Spinner>(R.id.spinner_gender)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        binding.registBtn.setOnClickListener {
            id = binding.firstIdInput.text.toString()
            pw = binding.firstPwdInput.text.toString()
            name = binding.nameInput.text.toString()
            ageInput = binding.ageInput.text.toString()
            address = binding.addressInput.text.toString()
            genderValue?.let { selectedGender ->
                gender = if (selectedGender == "남") "0" else "1"
            }
            phone_num = binding.addressInput.text.toString()
            viewModel.setUrl(resources.getString(R.string.api_ip_server))
            mobileToServer()
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

    private fun onRegisterButtonClicked() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(message)
        builder.setMessage("회원가입 성공")

        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
            onRegisterButtonClicked()
        }
        builder.show()
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                age = ageInput.toInt()
                val message =
                    RegisterData(id, pw, name, age, address, gender, phone_num, assignments)
                val response = viewModel.getUrl().value?.let {
                    RegisterApi.retrofitService(it).sendsMessage(message)
                }
                if (response != null) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val responseData = responseBody.result

                            showAlertDialog(responseData)
                        } else {
                            Log.e("@@@@Error3", "Response body is null")
                        }
                    } else {
                        Log.e("@@@@Error2", "Response not successful: ${response.code()}")
                    }
                }
            } catch (Ex: Exception) {
                Log.e("@@@@Error1", Ex.stackTraceToString())
            }
        }
    }

    // 화면 터치 시 키보드 내리기
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }
}