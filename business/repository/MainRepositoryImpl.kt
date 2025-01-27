package com.arminmehran.little_lemmon_app_capstone.business.repository

import com.arminmehran.little_lemmon_app_capstone.business.data.MenuItemDao
import com.arminmehran.little_lemmon_app_capstone.business.data.MenuItemNetwork
import com.arminmehran.little_lemmon_app_capstone.business.data.MenuItemRoom
import com.arminmehran.little_lemmon_app_capstone.business.data.MenuNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val dao: MenuItemDao
) : MainRepository {
    override fun getAllItemsStream(): Flow<List<MenuItemRoom>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMenuDataIfNeeded() {
        TODO("Not yet implemented")
    }

    override fun getItemStream(id: Int): Flow<MenuItemRoom?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertItem(item: MenuItemRoom) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: MenuItemRoom) {
        TODO("Not yet implemented")
    }

    override suspend fun updateItem(item: MenuItemRoom) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchItemsFromNetwork(url: String): List<MenuItemNetwork> {
        val httpClient = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("text", "plain"))
            }
        }

        val httpResponse: MenuNetwork = httpClient.get(url).body()
        return httpResponse.items
    }

    override suspend fun getAllDatabaseMenuItems(): Flow<List<MenuItemRoom>> = dao.getAll()

    override suspend fun saveItemsToDatabase(
        dao: MenuItemDao,
        menuItemsNetwork: List<MenuItemNetwork>
    ) {

        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        dao.insertAll(*menuItemsRoom.toTypedArray())
    }


}