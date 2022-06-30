package com.altaie.socialdownloader

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.altaie.socialdownloader.ui.home.HomeScreen
import com.altaie.socialdownloader.ui.theme.SocialDownloaderTheme
import com.altaie.socialdownloader.utils.toUrlOrNull
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val action: String? = intent?.action
        val data: Uri? = intent?.data

        setContent {
            SocialDownloaderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   HomeScreen(data = data.toUrlOrNull())
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SocialDownloaderTheme {
        Greeting("Android")
    }
}