package com.app.mmm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.app.mmm.navigation.BottomNavigationView
import com.app.mmm.navigation.HomeNavGraph
import com.app.mmm.state.rememberAppState
import com.app.mmm.ui.theme.MMMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MMMTheme {
                val appState = rememberAppState()
                Scaffold(
                    bottomBar = {
                        if (appState.shouldShowBottomBar) {
                            BottomNavigationView(appState = appState)
                        }
                    },
                    scaffoldState = appState.scaffoldState,
                ) {
                    HomeNavGraph(
                        modifier = Modifier.padding(it),
                        navController = appState.navController,
                    )
                }
            }
        }
    }
}
