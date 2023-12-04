package com.example.portfolioteenageremotionpreventapp.infoChatbot

import com.example.portfolioteenageremotionpreventapp.chatbot.ChatBotData
import com.example.portfolioteenageremotionpreventapp.chatbot.ChatBotDataResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

private val mHttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY) // BASIC) // check constants

//private val mOkHttpClient = OkHttpClient
//    .Builder()
//    .addInterceptor(mHttpLoggingInterceptor)
//    .build()

private val moshi = Moshi.Builder()//더 편하게 하기 위해서 사용
    .add(KotlinJsonAdapterFactory())
    .build()

private fun createRetrofit(baseUrl: String, timeout: Long): Retrofit {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(mHttpLoggingInterceptor)
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .build()

    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()
}

interface InfoChatBotApiService {
    @Headers("Content-Type: application/json")

    @POST("/v1/chat/chatbot-historys")
    suspend fun sendMessage(@Body message: InfoChatBotData): Response<InfoChatBotDataResponse>

}


object InfoChatBotApi {
    fun retrofitService(baseUrl: String, timeout: Long): InfoChatBotApiService {
        val retrofit = createRetrofit(baseUrl, timeout)
        return retrofit.create(InfoChatBotApiService::class.java)
    }
}