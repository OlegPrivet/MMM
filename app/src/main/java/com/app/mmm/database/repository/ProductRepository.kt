package com.app.mmm.database.repository

import com.app.mmm.database.entity.ProductStorageItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ProductRepository : Repository {

    suspend fun insertProduct(productStorageItem: ProductStorageItem)

    suspend fun updateProduct(productStorageItem: ProductStorageItem)

    suspend fun deleteProduct(productId: String)

    suspend fun getProduct(productId: String): ProductStorageItem

    suspend fun getAllProduct(viewModelScope: CoroutineScope): Flow<List<ProductStorageItem>>
}
