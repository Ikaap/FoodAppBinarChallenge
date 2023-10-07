package com.ikapurwanti.foodappbinarchallenge.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.ikapurwanti.foodappbinarchallenge.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface AppPreferenceDataSource {
    suspend fun getAppLayoutPref(): Boolean
    fun getAppLayoutFlow(): Flow<Boolean>
    suspend fun setAppLayoutPref(isGridLayout: Boolean)
}

class AppPreferenceDataSourceImpl(
    private val preferenceHelper: PreferenceDataStoreHelper
): AppPreferenceDataSource {
    override suspend fun getAppLayoutPref(): Boolean {
        return preferenceHelper.getFirstPreference(PREF_GRID_LAYOUT, false)
    }

    override fun getAppLayoutFlow(): Flow<Boolean> {
        return preferenceHelper.getPreference(PREF_GRID_LAYOUT, false)
    }

    override suspend fun setAppLayoutPref(isGridLayout: Boolean) {
        return preferenceHelper.putPreference(PREF_GRID_LAYOUT, isGridLayout)
    }

    companion object {
        val PREF_GRID_LAYOUT = booleanPreferencesKey("PREF_GRID_LAYOUT")
    }

}