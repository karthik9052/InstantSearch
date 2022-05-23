package com.karthik.reloyassignment.data

import com.karthik.reloyassignment.data.response.ApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("?key=27553393-d5743440c5bb3a181b40dbccb&image_type=photo")
    suspend fun getDataFromAPI(@Query("q") searchQuery: String,@Query("page") query: Int): Response<ApiResponse>

    companion object {
        val baseURL = "https://pixabay.com/api/"


        fun getApiService() = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(providesOkHttpClient())
            .build()
            .create(APIService::class.java)

        fun providesOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }
    }



    }
