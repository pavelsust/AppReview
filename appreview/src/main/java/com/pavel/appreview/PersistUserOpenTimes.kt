/**
 * Copyright 2021 Kakyire (Daniel Frimpong)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pavel.appreview

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * @author Kakyire (Daniel Frimpong)
 *
 * Save and update number of times user has open the app in [DataStore]
 *
 */

private const val DATA_STORE_NAME = "APP_REVIEW"
private const val APP_OPENED_TIMES = "many_times_app_opened"
const val TAG = "InAppReview"

internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)


internal class PersistUserOpenTimes(private val context: Context) {


    private val dataStoreKey = intPreferencesKey(APP_OPENED_TIMES)

    val appOpenedTimesFlow: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())

            } else {
                throw  exception
            }
        }.map { openedTimes ->
            openedTimes[dataStoreKey] ?: 0
        }


    suspend fun increaseAppOpenTimes() {
        context.dataStore.edit { storedData ->
            val currentValue = storedData[dataStoreKey]
                ?: 0 //assign 0 as value when current value is null
            storedData[dataStoreKey] = currentValue + 1
            Log.d(TAG, "increaseNumberOfTimesOpened: $currentValue")
        }
    }
}
