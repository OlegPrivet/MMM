package com.app.mmm.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.mmm.database.entity.ProductStorageItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productItem: ProductStorageItem)

    @Update
    suspend fun update(productItem: ProductStorageItem)

    @Query("DELETE FROM `ProductStorageItem` WHERE id = :productId")
    suspend fun delete(productId: String)

    @Query("SELECT * FROM `ProductStorageItem` WHERE id = :productId")
    fun getProduct(productId: String) : ProductStorageItem

    @Query("SELECT * FROM `ProductStorageItem`")
    fun getAllProducts() : Flow<List<ProductStorageItem>>
}
