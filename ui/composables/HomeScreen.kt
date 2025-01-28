package com.arminmehran.little_lemmon_app_capstone.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.arminmehran.little_lemmon_app_capstone.MainViewModel
import com.arminmehran.little_lemmon_app_capstone.R
import com.arminmehran.little_lemmon_app_capstone.business.data.MenuItemRoom
import com.arminmehran.little_lemmon_app_capstone.navigation.Screen.Profile
import com.arminmehran.little_lemmon_app_capstone.ui.theme.PrimaryGreen
import com.arminmehran.little_lemmon_app_capstone.ui.theme.PrimaryYellow
import com.arminmehran.little_lemmon_app_capstone.ui.theme.Secondary2
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
fun Home(navController: NavHostController, viewModel: MainViewModel) {
    val menuItemsState by viewModel.menuItems.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.getAllMenuItems() }

    val searchPhrase = remember { mutableStateOf("") }

//    LaunchedEffect(Unit) {
//        viewModel.fetchMenuItems()
//    }

    Column() {
        Header(navController)
        UpperPanel() { searchPhrase.value = it }
        LowerPanel(menuItemsState, searchPhrase)
    }
}


@Composable
fun Header(navController: NavHostController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.width(50.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.little_lemon_logo),
            modifier = Modifier
                .fillMaxWidth(0.65f)
        )

        Box(modifier = Modifier
            .size(50.dp)
            .clickable { navController.navigate(Profile.route) }) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.profile_icon_description),
                tint = PrimaryGreen,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 2.dp)
            )
        }
    }
}

@Composable
fun UpperPanel(search: (parameter: String) -> Unit) {
    val searchPhrase = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .background(PrimaryGreen)
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.little_lemon),
            style = MaterialTheme.typography.titleLarge,
            color = PrimaryYellow
        )
        Text(text = stringResource(R.string.new_york), style = MaterialTheme.typography.titleSmall, color = Color.White)
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.we_are_a_family_owned_mediterranean_restaurant_focused_on_traditional_recipes_served_with_a_modern_twist_turkish_italian_indian_and_chinese_recipes_are_our_speciality),
                modifier = Modifier.fillMaxWidth(0.7f),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = stringResource(R.string.hero_image),
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = searchPhrase.value,
            onValueChange = {
                searchPhrase.value = it
                search(searchPhrase.value)
            },
            placeholder = {
                Text(text = stringResource(R.string.enter_search_phrase))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon)
                )
            },
            colors = TextFieldDefaults.colors(Color.Black,MaterialTheme.colorScheme.background),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        )
    }

}

@Composable
fun LowerPanel(databaseMenuItems: List<MenuItemRoom>, search: MutableState<String>) {
    val categories = databaseMenuItems.map {
        it.category.replaceFirstChar { character ->
            character.uppercase()
        }
    }.toSet()

    val selectedCategory = remember {
        mutableStateOf("")
    }

    val items = if (search.value == "") {
        databaseMenuItems
    } else {
        databaseMenuItems.filter {
            it.title.contains(search.value, ignoreCase = true)
        }
    }

    val filteredItems = if (selectedCategory.value == "" || selectedCategory.value == "all") {
        items
    } else {
        items.filter {
            it.category.contains(selectedCategory.value, ignoreCase = true)
        }
    }


    Column {
        MenuCategories(categories) {
            selectedCategory.value = it
        }
        Spacer(modifier = Modifier.size(2.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            for (item in filteredItems) {
                MenuItem(item = item)
            }
        }
    }
}


@Composable
fun MenuCategories(categories: Set<String>, categoryLambda: (sel: String) -> Unit) {
    val cat = remember {
        mutableStateOf("")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp)
    ) {

        Column(Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            Text(text = "ORDER FOR DELIVERY", fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                CategoryButton(category = "All") {
                    cat.value = it.lowercase()
                    categoryLambda(it.lowercase())
                }

                for (category in categories) {
                    CategoryButton(category = category) {
                        cat.value = it
                        categoryLambda(it)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryButton(category: String, selectedCategory: (sel: String) -> Unit) {
    val isClicked = remember {
        mutableStateOf(false)
    }
    Button(
        onClick = {
            isClicked.value = !isClicked.value
            selectedCategory(category)

        },
        colors = ButtonDefaults.buttonColors(
            Color.LightGray,
            Secondary2

        )
    ) {
        Text(text = category, color = Color.DarkGray)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom) {

    val itemDescription = if (item.description.length > 100) {
        item.description.substring(0, 100) + ". . ."
    } else {
        item.description
    }

    Card(
        modifier = Modifier
            .clickable {}
            .shadow(4.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.fillMaxWidth(0.7f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(text = itemDescription, modifier = Modifier.padding(bottom = 10.dp))
                Text(text = "$ ${item.price}", fontWeight = FontWeight.Bold)

            }

            GlideImage(
                model = item.imageUrl,
                contentDescription = "",
                Modifier.size(100.dp, 100.dp),
                contentScale = ContentScale.Crop
            )
        }
    }

}