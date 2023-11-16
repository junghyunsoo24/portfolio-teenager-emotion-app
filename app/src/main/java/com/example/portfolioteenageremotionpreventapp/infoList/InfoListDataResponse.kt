package com.example.portfolioteenageremotionpreventapp.infoList

data class InfoListDataResponse(
    val key: Int,
    val id: String,
    val name: String,
    val age: Int,
    val address: String,
    val gender: String,
    val phone_num: String,
    val assignments: Int
)