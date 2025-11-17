package com.example.superutility

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.example.superutility.navigation.AppNavHost
import com.example.superutility.ui.theme.SuperUtilityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperUtilityTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    AppNavHost(navController)
                }
            }
        }
    }
}
