package com.app.mmm.view.productlist.state

sealed class ProductListViewAction {
    data class AddProductItem(val new: String) : ProductListViewAction()
    data class EditProductItem(val itemId: String) : ProductListViewAction()
}
