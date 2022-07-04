package com.altaie.socialdownloader.ui.base

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altaie.domain.models.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    fun <T> collectResourceValue(
        repoValue: Flow<Resource<T?>>,
        liveValue: MutableState<Resource<T?>>,
        onComplete: suspend () -> Unit = {}
    ) {
        viewModelScope.launch {
            liveValue.value = Resource.Loading
            repoValue
                .catch { }
                .onCompletion { onComplete() }
                .collect { liveValue.value = it }
        }
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            block()
        }
    }
}