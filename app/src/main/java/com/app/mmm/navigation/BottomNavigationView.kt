package com.app.mmm.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.mmm.state.AppState
import timber.log.Timber

@Composable
fun BottomNavigationView(
    appState: AppState,
) {
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation {
        appState.bottomBarTabs.forEach {
            BottomNavigationItem(
                selected = currentRoute == it.route,
                onClick = {
                    Timber.w(appState.currentRoute)
                    appState.navigateToBottomBarRoute(it.route)
                },
                icon = { Icon(imageVector = it.icon, contentDescription = stringResource(id = it.title)) },
                label = { Text(text = stringResource(id = it.title)) }
            )
        }
    }
}
