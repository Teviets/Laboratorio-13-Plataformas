package com.durini.solucionlab10.data.repository

import android.provider.ContactsContract
import com.durini.solucionlab10.data.local.model.Character
import com.durini.solucionlab10.data.util.DataState
import com.durini.solucionlab10.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface characterRepository {
    suspend fun getAllCharacters(): Resource<List<Character>>
    suspend fun deleteAllCharacters(): Resource<Unit>
    suspend fun getCharacter(id: Int): Resource<Character?>
    suspend fun updateCharacter(character: Character): Resource<Unit>
    suspend fun deleteCharacter(id: Int): Resource<Unit>
}