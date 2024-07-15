package com.bugrakstudios.livescore.repository

import com.bugrakstudios.livescore.data.remote.ApiService
import com.bugrakstudios.livescore.data.remote.model.Response
import javax.inject.Inject

class InPlayScoreRepository @Inject constructor(private val apiService : ApiService) {

    suspend fun getAllInPlayScore() : List<Response> = apiService.getLiveFixtures().response
}