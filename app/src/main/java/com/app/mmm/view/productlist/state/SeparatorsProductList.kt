package com.app.mmm.view.productlist.state

import com.app.mmm.model.ProductClientItem

sealed class SeparatorsProductList {

    data class ProductData(
        val id: String,
        val name: String,
        val caloric: String,
        val timestamp: Long,
    ) : SeparatorsProductList() {
        constructor(clientItem: ProductClientItem) : this(
            id = clientItem.id,
            name = clientItem.name,
            caloric = clientItem.caloric,
            timestamp = clientItem.timestamp
        )
    }

    data class ProductSeparator(val date: String, val total: String) : SeparatorsProductList()
}
