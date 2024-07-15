package com.bugrakstudios.livescore.viewModel.state

import com.bugrakstudios.livescore.data.remote.model.Response

sealed class MatchesState {
    object Empty : MatchesState()
    object Loading : MatchesState()
    class Success(val matches: List<Response>) : MatchesState()
    class Error(val message: String) : MatchesState()
}