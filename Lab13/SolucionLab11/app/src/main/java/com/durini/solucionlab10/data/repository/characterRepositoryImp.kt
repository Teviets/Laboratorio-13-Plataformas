package com.durini.solucionlab10.data.repository

import com.durini.solucionlab10.data.local.dao.CharacterDao
import com.durini.solucionlab10.data.local.model.Character
import com.durini.solucionlab10.data.remote.api.RickMortyApi
import com.durini.solucionlab10.data.remote.dto.mapToModel
import com.durini.solucionlab10.data.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class characterRepositoryImp(
    private val characterDao:CharacterDao,
    private var api: RickMortyApi
) :characterRepository {
    override fun getAllCharacters(): Flow<DataState<List<Character>>> = flow {
        val localCharacters = characterDao.getCharacters()
        if (localCharacters.isEmpty()){
            try {
                val remoteCharacters = api.getCharacters().results
                val charactersToStore = remoteCharacters.map{ dto -> dto.mapToModel() }
                characterDao.insertAll(charactersToStore)
                emit(DataState.Success(charactersToStore))
            }catch (e: Exception){
                emit(DataState.Error(e))
            }

        }else {
            emit(DataState.Success(localCharacters))
        }
    }

    override fun deleteAllCharacters(): kotlinx.coroutines.flow.Flow<DataState<Int>> {
        // Loading
        // Obtener registros se borraron
        // Si se borraron mas de 0, Succes
        // Si se borraron 0, Error
    }

    override fun updateCharacter(character: Character): Flow<DataState<Character?>> {
        // Retornar flow
        // Emitir Loading
        // Si se borrar
    }


    override fun getCharacter(id: Int): kotlinx.coroutines.flow.Flow<DataState<Character>> {
        // Retornar flow
        // Emitir Loading
        // Obtener de base de datos
        //
    }

    override fun deleteCharacter(id: Int): Flow<DataState<Int>> {
        TODO("Not yet implemented")
    }


}