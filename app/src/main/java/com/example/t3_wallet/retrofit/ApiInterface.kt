package com.example.t3_wallet.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/create-wallet")
    fun postData(@Body postModel: PostModel): Call<PostModel>
}