package com.bugrakstudios.livescore.data.remote.model

data class Event(
    val assist: Assist,
    val comments: String,
    val detail: String,
    val player: Player,
    val team: Team,
    val time: Time,
    val type: String
)