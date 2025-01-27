package com.arminmehran.little_lemmon_app_capstone

import androidx.lifecycle.ViewModel
import com.arminmehran.little_lemmon_app_capstone.business.data.MenuItemRoom
import com.arminmehran.little_lemmon_app_capstone.business.repository.MainRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MainRepositoryImpl): ViewModel() {
    private val _menuItems = MutableStateFlow<List<MenuItemRoom>>(emptyList())
    val menuItems: StateFlow<List<MenuItemRoom>> = _menuItems.asStateFlow()

   suspend  fun fetchMenuItems(url:String) {
       val menuList = repo.fetchItemsFromNetwork("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
       _menuItems.value = menuList.map { it.toMenuItemRoom() }.sortedBy { it.title }

       }

    suspend fun getAllMenuItems() =
        repo.getAllDatabaseMenuItems().collect{

    }
}