package com.app.mmm.view.productlist

import androidx.lifecycle.viewModelScope
import com.app.mmm.core.viewmodel.BaseViewModel
import com.app.mmm.database.entity.ProductStorageItem
import com.app.mmm.database.repository.ProductRepository
import com.app.mmm.model.Header
import com.app.mmm.model.ProductClientItem
import com.app.mmm.model.clientValue
import com.app.mmm.view.productlist.state.ProductListViewAction
import com.app.mmm.view.productlist.state.ProductListViewEvent
import com.app.mmm.view.productlist.state.ProductListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository,
) : BaseViewModel<ProductListViewState, ProductListViewAction, ProductListViewEvent>(
    initialState = INIT_STATE
) {

    private val _productList = MutableStateFlow<Map<Header, List<ProductClientItem>>>(emptyMap())
    val productList: Flow<Map<Header, List<ProductClientItem>>> = _productList.asStateFlow()

    init {
        viewState = viewState.copy(isLoading = true)
        loadProducts()
    }

    override fun obtainEvent(viewEvent: ProductListViewEvent) {
        when (viewEvent) {
            is ProductListViewEvent.AddingProductItem -> obtainAddItem(viewEvent.new)
            is ProductListViewEvent.EditingProductItem -> obtainEditItem(viewEvent.itemId)
        }
    }

    private fun obtainAddItem(new: String) {
        viewAction = ProductListViewAction.AddProductItem(new)
    }

    private fun obtainEditItem(itemId: String) {
        viewAction = ProductListViewAction.EditProductItem(itemId)
    }

    private fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllProduct(this).collect {
                val separatorsMap = it.separators()
                delay(500)
                _productList.value = separatorsMap
                viewState = viewState.copy(isLoading = false)
            }
        }
    }

    private fun List<ProductStorageItem>.separators(): Map<Header, List<ProductClientItem>> {
        return groupBy { it.timestamp }.map { (time, list) ->
            val totalCaloric = list.sumOf { it.caloric.toInt() }
            Header(time.formattedDate(), totalCaloric.toString()) to list.map { it.clientValue }
        }.toMap()
    }

    private fun Long.formattedDate() = SimpleDateFormat("dd.MM.yyyy").format(Date(this))

    private companion object {
        val INIT_STATE = ProductListViewState(
            isLoading = false
        )
    }
}
