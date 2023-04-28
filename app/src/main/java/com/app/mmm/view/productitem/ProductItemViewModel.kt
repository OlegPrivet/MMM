package com.app.mmm.view.productitem

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.mmm.core.viewmodel.BaseViewModel
import com.app.mmm.database.entity.ProductStorageItem
import com.app.mmm.database.repository.ProductRepository
import com.app.mmm.view.productitem.state.ProductItemViewAction
import com.app.mmm.view.productitem.state.ProductItemViewEvent
import com.app.mmm.view.productitem.state.ProductItemViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductItemViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProductItemViewState, ProductItemViewAction, ProductItemViewEvent>(initialState = INIT_STATE) {

    init {
        initProduct(savedStateHandle.get<String>("tag"))
    }

    override fun obtainEvent(viewEvent: ProductItemViewEvent) {
        when (viewEvent) {
            is ProductItemViewEvent.ChangingProductName -> obtainName(viewEvent.name)
            is ProductItemViewEvent.ChangingProductCaloric -> obtainCaloric(viewEvent.caloric)
            is ProductItemViewEvent.DeletingProductItem -> obtainDeleteItem()
            is ProductItemViewEvent.SaveProduct -> saveProduct()
        }
    }

    private fun obtainDeleteItem() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(viewState.id)
            viewAction = ProductItemViewAction.SaveAction
        }
    }

    private fun initProduct(tag: String?) {
        if (tag == null || tag == "new") return

        viewModelScope.launch(Dispatchers.IO) {
            val productItem = repository.getProduct(tag)
            viewState = ProductItemViewState(
                id = productItem.id,
                productName = productItem.name,
                productCaloric = productItem.caloric,
                timestamp = productItem.timestamp
            )
        }
    }

    private fun obtainName(name: String) {
        viewState = viewState.copy(productName = name)
    }

    private fun obtainCaloric(caloric: String) {
        viewState = viewState.copy(productCaloric = caloric)
    }

    private fun saveProduct() {
        viewModelScope.launch {
            val productStorageItem = ProductStorageItem(
                id = viewState.id.ifEmpty { UUID.randomUUID().toString() },
                name = viewState.productName,
                caloric = viewState.productCaloric,
                timestamp = if (viewState.timestamp == 0L) midnightDate() else viewState.timestamp
            )
            repository.insertProduct(productStorageItem)
            viewAction = ProductItemViewAction.SaveAction
        }
    }

    private fun midnightDate(): Long {
        val date: Calendar = GregorianCalendar()
        date[Calendar.HOUR_OF_DAY] = 0
        date[Calendar.MINUTE] = 0
        date[Calendar.SECOND] = 0
        date[Calendar.MILLISECOND] = 0
        return date.time.time
    }

    private companion object {
        val INIT_STATE = ProductItemViewState(
            id = "",
            productName = "",
            productCaloric = "",
            timestamp = 0L,
        )
    }
}
