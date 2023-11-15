package com.example.portfolioteenageremotionpreventapp.chatbot

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
        .connectTimeout(timeout, TimeUnit.SECONDS) // 연결 타임아웃 설정
        .readTimeout(timeout, TimeUnit.SECONDS) // 읽기 타임아웃 설정
        .writeTimeout(timeout, TimeUnit.SECONDS) // 쓰기 타임아웃 설정
        .build()

    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .client(okHttpClient) // OkHttpClient를 Retrofit에 설정
        .build()
}

interface ChatBotApiService {
    @Headers("Content-Type: application/json")

    @POST("/chat")
    suspend fun sendMessage(@Body message: ChatBotData): Response<ChatBotDataResponse>

}


object ChatBotApi {
    fun retrofitService(baseUrl: String, timeout: Long): ChatBotApiService {
        val retrofit = createRetrofit(baseUrl, timeout)
        return retrofit.create(ChatBotApiService::class.java)
    }
}