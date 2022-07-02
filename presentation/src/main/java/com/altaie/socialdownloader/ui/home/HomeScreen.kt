package com.altaie.socialdownloader.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.altaie.domain.models.Resources
import com.altaie.domain.models.tiktok.TikTokPost
import com.altaie.socialdownloader.ui.home.common.PostWidget
import com.altaie.socialdownloader.ui.theme.size
import com.altaie.socialdownloader.utils.Constants.DOWNLOADED_SUCCESSFULLY
import com.altaie.socialdownloader.utils.Constants.STATUS_DOWNLOAD_COMPLETE
import com.altaie.socialdownloader.utils.Constants.URL_PLACEHOLDER
import com.altaie.socialdownloader.utils.noWhitespace
import com.altaie.socialdownloader.utils.toast


@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), data: String? = null) {
    var url by remember { mutableStateOf(data ?: "").also { viewModel.onEvent(it.value) } }
    val validateUrlState by remember { viewModel.validateUrlState }
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(
            start = MaterialTheme.size.medium,
            end = MaterialTheme.size.medium,
            top = MaterialTheme.size.large,
            bottom = MaterialTheme.size.large,
        )
    ) {
        // Show the url input field if app launched normally
        // and hide it if app launched from deep link or share intent
        if (data == null) {
            TextField(
                value = url,
                singleLine = true,
                onValueChange = { value ->
                    url = value.noWhitespace()
                    viewModel.onEvent(value)
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.onEvent(url) }),
                placeholder = {
                    Text(
                        stringResource(id = URL_PLACEHOLDER),
                        modifier = Modifier.alpha(.5f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large)
            )
        }

        // Show error message if url is invalid
        if (!validateUrlState.isNullOrEmpty() && url.isNotEmpty()) {
            Spacer(modifier = Modifier.height(MaterialTheme.size.small))

            Text(
                text = validateUrlState.toString(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (data == null)
            Spacer(modifier = Modifier.height(MaterialTheme.size.large))

        // A container for PostWidget & Progress Indicators
        Box(contentAlignment = Alignment.Center) {
            PostWidget(data = viewModel.post.value.toData ?: TikTokPost()) { progress ->
                // Show toast when download is complete
                if (progress == STATUS_DOWNLOAD_COMPLETE) {
                    context.toast(context.getString(DOWNLOADED_SUCCESSFULLY))
                }
            }

            // Show progress bar when post loading
            if (viewModel.post.value is Resources.Loading)
                CircularProgressIndicator(modifier = Modifier.padding(bottom = MaterialTheme.size.large - 3.dp))
        }
    }
}
