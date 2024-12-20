package ru.webarmour.crypto_task.presentation.core_ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.webarmour.crypto_task.R
import ru.webarmour.crypto_task.domain.RatesModel
import ru.webarmour.crypto_task.presentation.MainViewModel
import ru.webarmour.crypto_task.presentation.theme.Roboto
import ru.webarmour.crypto_task.presentation.theme.favourite
import ru.webarmour.crypto_task.presentation.theme.secondary
import ru.webarmour.crypto_task.presentation.utils.format


@Composable
fun CardItem(
    currentModel: RatesModel,
    viewModel: MainViewModel = hiltViewModel(),
) {

    var favouriteColor by rememberSaveable { mutableStateOf(false) }
    val updatedModel by remember { mutableStateOf(currentModel) }
    LaunchedEffect(currentModel) {
        favouriteColor = viewModel.isFavourite(currentModel)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 10.dp,
                bottom = 8.dp

            )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()

        ) {

            Text(
                style = TextStyle(
                    fontFamily = Roboto,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                text = currentModel.name,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = currentModel.price.format(),
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = {
                if (!favouriteColor) {
                    viewModel.insertCoinToDb(updatedModel)
                    favouriteColor = true
                } else {
                    viewModel.deleteCoinFromDb(updatedModel)
                    favouriteColor = false
                }

            }) {
                Icon(
                    painterResource(
                        if (!favouriteColor) {
                            R.drawable.favorites_off
                        } else {
                            R.drawable.favorites_on
                        }
                    ), contentDescription = "Filter",
                    tint = if (favouriteColor == false) {
                        secondary
                    } else {
                        favourite
                    }
                )
            }
        }

    }
}

