package com.app.mmm.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.mmm.R

enum class BottomBarScreen(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String,
) {
    PRODUCT_LIST(R.string.product_list_title, icon = Icons.Filled.List, route = "product_list"),
    SETTINGS(R.string.setting_title, icon = Icons.Filled.Settings, route = "settings")
}
