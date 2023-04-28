package com.app.mmm.view.productitem.state

sealed class ProductItemViewEvent {
    data class ChangingProductName(val name: String) : ProductItemViewEvent()
    data class ChangingProductCaloric(val caloric: String) : ProductItemViewEvent()
    object DeletingProductItem : ProductItemViewEvent()
    object SaveProduct : ProductItemViewEvent()
}
