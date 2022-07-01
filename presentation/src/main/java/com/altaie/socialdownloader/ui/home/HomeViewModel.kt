package com.altaie.socialdownloader.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.altaie.domain.models.Resources
import com.altaie.domain.models.tiktok.TikTokPost
import com.altaie.domain.repositories.TikTokRepository
import com.altaie.domain.usecases.GetIdFromShortLinkUseCase
import com.altaie.domain.usecases.ValidationUrlUseCase
import com.altaie.socialdownloader.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TikTokRepository,
    private val getIdFromShortLinkUseCase: GetIdFromShortLinkUseCase
) : BaseViewModel() {
    val validateUrlState = mutableStateOf<String?>(null)
    val post: MutableState<Resources<TikTokPost?>> = mutableStateOf(Resources.Init)

    fun onEvent(value: String) {
        ValidationUrlUseCase(url = value).run {
            if (isSuccessful) {
                getPost(id = data)
                validateUrlState.value = errorMessage
            } else {
                launch {
                    getIdFromShortLinkUseCase(shortLink = value).run {
                        validateUrlState.value = if (isSuccessful) {
                            getPost(id = data)
                            errorMessage
                        } else errorMessage
                    }
                }
            }
        }
    }

    private fun getPost(id: String) {
        collectResourceValue(repository.getPost(id = id), post)
    }
}