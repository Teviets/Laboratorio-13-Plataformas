package com.durini.solucionlab10.data.repository

import android.provider.ContactsContract
import com.durini.solucionlab10.data.local.model.Character
import com.durini.solucionlab10.data.util.DataState
import kotlinx.coroutines.flow.Flow

interface characterRepository {
    fun getAllCharacters():Flow<DataState<List<Character>>>
    fun deleteAllCharacters(): Flow<DataState<Int>>
    fun updateCharacter(character: Character): Flow<DataState<Character?>>
    fun getCharacter(id: Int): Flow<DataState<Character>>
    fun deleteCharacter(id : Int):Flow<DataState<Int>>
}