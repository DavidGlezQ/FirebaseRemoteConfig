package com.david.glez.firebaseremoteconfig.ui.remoteconfig

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.glez.firebaseremoteconfig.ui.data.RemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val remoteConfigRepository: RemoteConfigRepository) :
    ViewModel() {

    fun initApp() {
        viewModelScope.launch(Dispatchers.IO) {
            val title = remoteConfigRepository.getAppInfo()
            Log.i("Title:", title)
        }
    }
}