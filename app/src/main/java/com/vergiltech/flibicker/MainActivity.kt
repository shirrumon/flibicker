package com.vergiltech.flibicker

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.vergiltech.flibicker.navigation.AppNavHost
import com.vergiltech.flibicker.ui.theme.FlibickerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.fromParts("package", packageName, null)
                startActivity(intent)
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(WRITE_EXTERNAL_STORAGE), 101
                )
            }
        }

        enableEdgeToEdge()
        setContent {
            FlibickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        navController = rememberNavController(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}