package com.durini.solucionlab10.data.repository

import com.durini.solucionlab10.data.local.dao.CharacterDao
import com.durini.solucionlab10.data.local.model.Character
import com.durini.solucionlab10.data.remote.api.RickMortyApi
import com.durini.solucionlab10.data.remote.dto.mapToModel

import com.durini.solucionlab10.data.util.Resource
import kotlin.Exception

class characterRepositoryImp(
    private val characterDao:CharacterDao,
    private var api: RickMortyApi
) :characterRepository {
    override suspend fun getAllCharacters(): Resource<List<Character>> {
        val localCharacters = characterDao.getCharacters()
        return if(localCharacters.isEmpty()){
            try {
                val remoteCharacter = api.getCharacters().results
                val charactersToStore = remoteCharacter.map { dto -> dto.mapToModel() }
                characterDao.insertAll(charactersToStore)
                Resource.Success(charactersToStore)
            }catch (e: Exception){
                Resource.Error(e.message ?: "")
            }
        }else {
            Resource.Success(localCharacters)
        }
    }

    override suspend fun deleteAllCharacters(): Resource<Unit> {
        return try {
            characterDao.deleteAll()
            Resource.Success(Unit)
        }catch (e: Exception){
            Resource.Error(e.message ?: "")
        }
    }

    override suspend fun getCharacter(id: Int): Resource<Character?> {
        return try {
            Resource.Success(characterDao.getCharacter(id))
        } catch (e: Exception){
            Resource.Error(e.message ?:"")
        }
    }

    override suspend fun updateCharacter(character: Character): Resource<Unit> {
        return try {
            characterDao.update(character)
            Resource.Success(Unit)
        } catch (e: Exception){
            Resource.Error(e.message ?: "")
        }
    }

    override suspend fun deleteCharacter(id: Int): Resource<Unit> {
        return try {
            characterDao.delete(characterDao.getCharacter(id))
            Resource.Success(Unit)
        }catch (e: Exception){
            Resource.Error(e.message ?:"")
        }
    }

}