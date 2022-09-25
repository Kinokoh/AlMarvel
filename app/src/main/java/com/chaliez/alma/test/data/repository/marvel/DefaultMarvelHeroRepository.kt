package com.chaliez.alma.test.data.repository.marvel

import android.content.Context
import com.chaliez.alma.test.data.model.MarvelHero
import com.chaliez.alma.test.data.repository.preference.AppPreferencesRepository
import com.chaliez.alma.test.util.NetworkUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import java.util.Calendar
import javax.inject.Inject

/**
 * Default repository with following behavior :
 *  check if the device is connected to internet and have old data (depending of DELAY_UPDATE)
 *      if yes -> retrieve data from api (NetworkMarvelHeroRepository, store them in database (DatabaseMarvelHeroRepository) and update lastUpdate date using AppPreferencesRepository
 *      if not -> retrieve data from database (DatabaseMarvelHeroRepository)
 */
class DefaultMarvelHeroRepository @Inject constructor(
    private val networkRepository: NetworkMarvelHeroRepository,
    private val databaseMarvelHeroRepository: DatabaseMarvelHeroRepository,
    private val preferencesRepository: AppPreferencesRepository,
    @ApplicationContext private val appContext: Context
) : MarvelHeroRepository {

    companion object {
        private const val DELAY_UPDATE: Long = 3 * 60 * 60 * 1000
    }

    private fun shouldUpdate(): Boolean {
        //Check if we have network
        var shouldUpdate = false
        val hasNetwork = NetworkUtil.isNetworkAvailable(appContext)
        if (hasNetwork) {
            //Check if the last update is recent (i.e. < DELAY_UPDATE)
            val currentDate = Calendar.getInstance().time
            val lastUpdate = preferencesRepository.getLastUpdateDate()
            val isRecent = currentDate.time - lastUpdate.time < DELAY_UPDATE
            shouldUpdate = isRecent.not()
        }
        return shouldUpdate
    }

    override suspend fun getAll(): Flow<List<MarvelHero>> {
        if (shouldUpdate()) {
            val remoteFlow = networkRepository.getAll()
            remoteFlow.collect {
                addAll(it)
                preferencesRepository.setLastUpdateDate(Calendar.getInstance().time)
            }
            return remoteFlow
        } else {
            return databaseMarvelHeroRepository.getAll()
        }
    }

    override suspend fun addAll(newHeroes :List<MarvelHero>) = databaseMarvelHeroRepository.addAll(newHeroes)

    override suspend fun getSquad(): Flow<List<MarvelHero>> = databaseMarvelHeroRepository.getSquad()

    override suspend fun get(id: Int): Flow<MarvelHero> = databaseMarvelHeroRepository.get(id)

    override suspend fun updateSquad(id: Int, isPartOfSquad: Boolean) = databaseMarvelHeroRepository.updateSquad(id, isPartOfSquad)

}