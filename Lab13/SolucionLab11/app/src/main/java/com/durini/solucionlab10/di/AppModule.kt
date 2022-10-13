package com.durini.solucionlab10.di

import android.content.Context
import androidx.room.Room
import com.durini.solucionlab10.data.local.LabDatabase
import com.durini.solucionlab10.data.local.dao.CharacterDao
import com.durini.solucionlab10.data.remote.api.RickMortyApi
import com.durini.solucionlab10.data.remote.dto.CharactersResponse
import com.durini.solucionlab10.data.repository.characterRepository
import com.durini.solucionlab10.data.repository.characterRepositoryImp
import com.durini.solucionlab10.data.util.API_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggginInterceptro(): HttpLoggingInterceptor{
        val loggin = HttpLoggingInterceptor()
        loggin.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggin
    }
    @Provides
    @Singleton
    fun provideHttpClient ( interceptor: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): RickMortyApi{
        return Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create())
            .client(client).build().create(RickMortyApi::class.java)

    }

    @Provides
    @Singleton
    fun provideCharacterDao(context: Context):LabDatabase{
        return Room.databaseBuilder(
            context,
            LabDatabase::class.java,
            "LabDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDataBase(database: LabDatabase):CharacterDao{
        return database.characterDao()
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(api: RickMortyApi, dao: CharacterDao):characterRepository{
        return characterRepositoryImp(
            api = api,
            characterDao = dao
        )
    }


}