package com.altaie.socialdownloader.ui.base

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altaie.domain.models.Resources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    fun <T> collectValue(
        repoValue: Flow<T?>,
        liveValue: MutableState<T?>,
        onComplete: suspend () -> Unit = {}
    ) {
        viewModelScope.launch {
            repoValue
                .catch { }
                .onCompletion { onComplete() }
                .collect { liveValue.value = it }
        }
    }

    fun <T> collectResourceValue(
        repoValue: Flow<Resources<T?>>,
        liveValue: MutableState<Resources<T?>>,
        onComplete: suspend () -> Unit = {}
    ) {
        viewModelScope.launch {
            repoValue
                .catch { }
                .onCompletion { onComplete() }
                .collect { liveValue.value = it }
        }
    }

    fun <T> collectValueOfList(
        repoValue: Flow<List<T>?>,
        liveValue: MutableState<List<T>?>,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
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