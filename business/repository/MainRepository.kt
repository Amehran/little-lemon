package com.arminmehran.little_lemmon_app_capstone.business.repository

import com.arminmehran.little_lemmon_app_capstone.business.data.MenuItemDao
import com.arminmehran.little_lemmon_app_capstone.business.data.MenuItemNetwork
import com.arminmehran.little_lemmon_app_capstone.business.data.MenuItemRoom
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAllItemsStream(): Flow<List<MenuItemRoom>>
    suspend fun fetchMenuDataIfNeeded()

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<MenuItemRoom?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: MenuItemRoom)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: MenuItemRoom)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: MenuItemRoom)

    suspend fun fetchItemsFromNetwork(url: String): List<MenuItemNetwork>

    suspend fun getAllDatabaseMenuItems(): Flow<List<MenuItemRoom>>

    suspend fun saveItemsToDatabase(dao: MenuItemDao, menuItemsNetwork: List<MenuItemNetwork>)
}

