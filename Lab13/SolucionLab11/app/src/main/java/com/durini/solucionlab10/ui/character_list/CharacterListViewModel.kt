package com.durini.solucionlab10.ui.character_list

import androidx.lifecycle.ViewModel
import com.durini.solucionlab10.data.local.model.Character
import com.durini.solucionlab10.data.repository.characterRepository
import com.durini.solucionlab10.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: characterRepository
): ViewModel() {

    private val _screenState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Empty)
    val screenState: StateFlow<ListUiState> = _screenState

    sealed interface ListUiState{
        object Empty: ListUiState
        object Loading: ListUiState
        class Succes(val data: List<Character>): ListUiState
        class Error(val message: String)
    }

    suspend fun getCharacterList(){

        val response = repository.getAllCharacters()
        when (response) {
            is Resource.Error -> {

            }
            is Resource.Success -> {
                if ()
            }
        }
    }
}