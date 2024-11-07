package com.david.glez.firebaseremoteconfig.ui.domain

import com.david.glez.firebaseremoteconfig.ui.data.RemoteConfigRepository
import javax.inject.Inject

class CanAccessToApp @Inject constructor(private val repository: RemoteConfigRepository) {
    suspend operator fun invoke(): Boolean {
        val appVersion = repository.getAppVersion()
        val minVersion = repository.getMinAllowedVersion()

        return appVersion.zip(minVersion).all { (appV, minV) -> appV >= minV }
    }
}