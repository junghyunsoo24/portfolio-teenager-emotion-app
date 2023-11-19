package com.example.portfolioteenageremotionpreventapp.infoSet

import com.example.portfolioteenageremotionpreventapp.infoList.InfoListData
import com.example.portfolioteenageremotionpreventapp.infoList.InfoListDataResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private val mHttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY) // BASIC) // check constants

//private val mOkHttpClient = OkHttpClient
//    .Builder()
//    .addInterceptor(mHttpLoggingInterceptor)
//    .build()

private val moshi = Moshi.Builder()//더 편하게 하기 위해서 사용
    .add(KotlinJsonAdapterFactory())
    .build()

private fun createRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .build()
}

interface InfoSetListApiService {
    @Headers("Content-Type: application/json")

    @POST("/v1/auth/teenager/set-Info")
    suspend fun sendsMessage(@Body message: InfoSetListData): Response<InfoSetListDataResponse>

}


object InfoSetListApi {
    fun retrofitService(baseUrl: String): InfoSetListApiService {
        val retrofit = createRetrofit(baseUrl)
        return retrofit.create(InfoSetListApiService::class.java)
    }
}