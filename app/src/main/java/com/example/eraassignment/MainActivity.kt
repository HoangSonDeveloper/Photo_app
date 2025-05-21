package com.example.eraassignment

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.eraassignment.ui.theme.EraAssignmentTheme
import com.example.eraassignment.view.MainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EraAssignmentTheme {
                MainScreen()
            }
        }
    }
}

class PhotoApp: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PhotoApp)
            modules(AppModules.appModule)
        }
    }
}