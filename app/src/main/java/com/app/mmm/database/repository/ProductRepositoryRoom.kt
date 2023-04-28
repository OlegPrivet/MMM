package com.app.mmm.database.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.mmm.database.AppDatabase
import com.app.mmm.database.dao.ProductDao
import com.app.mmm.database.entity.ProductStorageItem
import com.app.mmm.model.ProductClientItem
import com.app.mmm.model.clientValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class ProductRepositoryRoom(context: Context) : ProductRepository {

    private val database = AppDatabase(context = context)
    private var productDao: ProductDao = database.productDao()

    override suspend fun insertProduct(productStorageItem: ProductStorageItem) {
        productDao.insert(productItem = productStorageItem)
    }

    override suspend fun updateProduct(productStorageItem: ProductStorageItem) {
        productDao.update(productItem = productStorageItem)
    }

    override suspend fun deleteProduct(productId: String) {
        productDao.delete(productId = productId)
    }

    override suspend fun getProduct(productId: String): ProductStorageItem =
        productDao.getProduct(productId = productId)

    override suspend fun getAllProduct(viewModelScope: CoroutineScope): Flow<List<ProductStorageItem>> =
        productDao.getAllProducts()


    override suspend fun <T> runInTransaction(block: suspend () -> T): T {
        TODO("Not yet implemented")
    }
}
