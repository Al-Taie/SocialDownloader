package com.altaie.socialdownloader

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.altaie.socialdownloader.ui.home.HomeScreen
import com.altaie.socialdownloader.ui.theme.SocialDownloaderTheme
import com.altaie.socialdownloader.utils.toUrlOrNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        var data: Uri? = intent?.data

        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            data = Uri.parse(it)
        }

        setContent {
            SocialDownloaderTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(data = data.toUrlOrNull())
                }
            }
        }
        checkPermissions()
    }


    private fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        val permissions = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )

        for (permission in permissions) {
            result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission)
            }
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), 100)
            return false
        }
        return true
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SocialDownloaderTheme {}
}
