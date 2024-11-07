package com.david.glez.firebaseremoteconfig.ui.data

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteConfigRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val remoteConfig: FirebaseRemoteConfig
) {

    companion object {
        const val TITLE = "title"
        const val HAPPY = "happy"
        const val MIN_VERSION_RC = "min_version"
    }

    suspend fun getAppInfo(): String {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        val happy = remoteConfig.getBoolean(HAPPY)
        val title = remoteConfig.getString(TITLE)
        return "El titulo es $title, me siento happy? $happy"
    }

    fun getAppVersion(): List<Int> {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName!!.split(".").map { it.toInt() }
        } catch (e: Exception) {
            listOf(0, 0, 0)
        }
    }

    suspend fun getMinAllowedVersion(): List<Int> {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        val minVersion = remoteConfig.getString(MIN_VERSION_RC)
        if (minVersion.isBlank()) return listOf(0, 0, 0)
        return minVersion.split(".").map { it.toInt() }
    }
}