package com.bugrakstudios.livescore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugrakstudios.livescore.repository.InPlayScoreRepository
import com.bugrakstudios.livescore.viewModel.state.MatchesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class InPlayMatchesViewModel @Inject constructor(
    private val inPlayScoreRepository: InPlayScoreRepository
) : ViewModel() {
    private var _inplayMatchesState = MutableStateFlow<MatchesState>(MatchesState.Empty)
    val inplayMatchesState : StateFlow<MatchesState> = _inplayMatchesState

    init {
        getAllPlayMatches()
    }

    private fun getAllPlayMatches(){

        _inplayMatchesState.value = MatchesState.Loading

        viewModelScope.launch (Dispatchers.IO){
            try {
                val inplayMatchesResponse = inPlayScoreRepository.getAllInPlayScore()
                _inplayMatchesState.value = MatchesState.Success(inplayMatchesResponse)
            }
            catch (exception : HttpException)
            {
                _inplayMatchesState.value = MatchesState.Error("No internet connection")
            }
            catch (exeption : IOException)
            {
                _inplayMatchesState.value = MatchesState.Error("Something went wrong")
            }
        }
    }
}