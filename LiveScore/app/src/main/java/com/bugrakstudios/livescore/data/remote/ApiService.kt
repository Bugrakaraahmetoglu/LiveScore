package com.bugrakstudios.livescore.data.remote

import com.bugrakstudios.livescore.Constants
import com.bugrakstudios.livescore.data.remote.model.InPlayScore
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("x-apisports-key: ${Constants.API_KEY}")
    @GET("fixtures?live=all")
    suspend fun getLiveFixtures(): InPlayScore
}