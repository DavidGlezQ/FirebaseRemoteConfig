package com.david.glez.firebaseremoteconfig.ui.remoteconfig

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.glez.firebaseremoteconfig.ui.data.RemoteConfigRepository
import com.david.glez.firebaseremoteconfig.ui.domain.CanAccessToApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository,
    private val canAccessToApp: CanAccessToApp
) : ViewModel() {

    private val _showBlockDialog = MutableStateFlow<Boolean?>(null)
    val showBlockDialog: StateFlow<Boolean?> = _showBlockDialog

    fun initApp() {
        viewModelScope.launch(Dispatchers.IO) {
            val title = remoteConfigRepository.getAppInfo()
            Log.i("Title:", title)
        }

        viewModelScope.launch {
            val canAccess = withContext(Dispatchers.IO) {
                canAccessToApp()
            }

            _showBlockDialog.value = !canAccess
        }
    }
}