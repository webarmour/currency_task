package ru.webarmour.crypto_task.presentation.favourites_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ru.webarmour.crypto_task.presentation.MainViewModel
import ru.webarmour.crypto_task.presentation.core_ui.CardItem
import ru.webarmour.crypto_task.presentation.theme.header
import ru.webarmour.crypto_task.presentation.theme.textDefaults

@Composable
fun FavouritesScreen(
    navController: NavHostController,
    textColor: Color = textDefaults,
    backgroundColor: Color = header,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.favoriteState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Favourites",
                    style = MaterialTheme.typography.headlineLarge,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            items(state.value){
                CardItem(
                    currentModel = it,
                    viewModel = viewModel
                )
            }
        }
    }
}