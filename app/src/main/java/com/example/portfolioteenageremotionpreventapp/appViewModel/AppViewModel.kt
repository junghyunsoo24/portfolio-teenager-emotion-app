package com.example.portfolioteenageremotionpreventapp.appViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel private constructor() : ViewModel() {
    private val jwtTokenLiveData = MutableLiveData<String>()

    private val userIdLiveData = MutableLiveData<String>()
    private val userPwdLiveData = MutableLiveData<String>()
    private val userNameLiveData = MutableLiveData<String>()
    private val userAgeLiveData = MutableLiveData<Int>()
    private val userAddressLiveData = MutableLiveData<String>()
    private val userGenderLiveData = MutableLiveData<String>()
    private val userPhoneNumLiveData = MutableLiveData<String>()

    private val messageListLiveData = MutableLiveData<List<String>>()

    private val urlLiveData = MutableLiveData<String>()

    private val currentDate = MutableLiveData<String>()

    private val cnt = MutableLiveData<Int>()

    fun getCnt(): MutableLiveData<Int> {
        return cnt
    }

    // Setter
    fun setCnt(newCnt: Int) {
        cnt.value = newCnt
    }

    fun setJwtToken(token: String) {
        jwtTokenLiveData.value = token
    }

    fun getJwtToken(): LiveData<String> {
        return jwtTokenLiveData
    }

    fun setUserId(id: String) {
        userIdLiveData.value = id
    }

    fun getUserId(): LiveData<String> {
        return userIdLiveData
    }

    fun setUserPwd(pwd: String) {
        userPwdLiveData.value = pwd
    }

    fun getUserPwd(): LiveData<String> {
        return userPwdLiveData
    }

    fun setUserName(name: String) {
        userNameLiveData.value = name
    }

    fun getUserName(): LiveData<String> {
        return userNameLiveData
    }

    fun setUserAge(age: Int) {
        userAgeLiveData.value = age
    }

    fun getUserAge(): LiveData<Int> {
        return userAgeLiveData
    }

    fun setUserAddress(address: String) {
        userAddressLiveData.value = address
    }

    fun getUserAddress(): LiveData<String> {
        return userAddressLiveData
    }

    fun setUserGender(gender: String) {
        userGenderLiveData.value = gender
    }

    fun getUserGender(): LiveData<String> {
        return userGenderLiveData
    }

    fun setUserPhoneNum(phoneNum: String) {
        userPhoneNumLiveData.value = phoneNum
    }

    fun getUserPhoneNum(): LiveData<String> {
        return userPhoneNumLiveData
    }

    fun setUrl(url: String) {
        urlLiveData.value = url
    }

    fun getUrl(): LiveData<String> {
        return urlLiveData
    }

    fun setCurrentDate(date: String) {
        currentDate.value = date
    }

    fun getCurrentDate(): LiveData<String> {
        return currentDate
    }

    fun setMessageList(messages: List<String>) {
        messageListLiveData.value = messages
    }

    fun getMessageList(): LiveData<List<String>> {
        return messageListLiveData
    }

    companion object {
        private var instance: AppViewModel? = null

        fun getInstance(): AppViewModel {
            if (instance == null) {
                instance = AppViewModel()
            }
            return instance!!
        }
    }
}