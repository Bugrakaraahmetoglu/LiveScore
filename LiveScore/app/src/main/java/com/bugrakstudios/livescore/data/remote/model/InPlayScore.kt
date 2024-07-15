package com.bugrakstudios.livescore.data.remote.model

data class InPlayScore(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)