package com.app.mmm.view.productlist.state

sealed class ProductListViewEvent {
    data class AddingProductItem(val new: String) : ProductListViewEvent()
    data class EditingProductItem(val itemId: String) : ProductListViewEvent()
}
