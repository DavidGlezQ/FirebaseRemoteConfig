package com.david.glez.firebaseremoteconfig.ui.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteConfigRepository @Inject constructor(val remoteConfig: FirebaseRemoteConfig) {

    companion object {
        const val TITLE = "title"
        const val HAPPY = "happy"
    }

    suspend fun getAppInfo(): String {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        val happy = remoteConfig.getBoolean(HAPPY)
        val title = remoteConfig.getString(TITLE)
        return "El titulo es $title, me siento happy? $happy"
    }
}