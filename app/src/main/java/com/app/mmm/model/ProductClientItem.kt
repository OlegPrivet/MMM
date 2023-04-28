package com.app.mmm.model

import androidx.compose.runtime.Stable
import com.app.mmm.database.entity.ProductStorageItem

@Stable
data class Header(
    val date: String,
    val totalCaloric: String,
)
@Stable
data class ProductClientItem(
    val id: String,
    val name: String,
    val caloric: String,
    val timestamp: Long,
)

internal val ProductStorageItem.clientValue: ProductClientItem
    get() = ProductClientItem(
        id = id,
        name = name,
        caloric = caloric,
        timestamp = timestamp,
    )


