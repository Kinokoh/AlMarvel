package com.chaliez.alma.test.data.repository.preference

import java.util.*

interface AppPreferencesRepository {
    fun getLastUpdateDate(): Date
    fun setLastUpdateDate(date: Date)
}
