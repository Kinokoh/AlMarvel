package com.chaliez.alma.test.data.repository.preference

import android.content.SharedPreferences
import java.util.*
import javax.inject.Inject

class DefaultAppPreferencesRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AppPreferencesRepository {

    companion object {
        private const val KEY_LAST_UPDATE: String = "LAST_UPDATE_PREFERENCE"
    }

    override fun getLastUpdateDate(): Date = Date(sharedPreferences.getLong(KEY_LAST_UPDATE, 0))

    override fun setLastUpdateDate(date: Date) {
        sharedPreferences.edit().putLong(KEY_LAST_UPDATE, date.time).apply()
    }

}