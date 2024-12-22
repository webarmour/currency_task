package ru.webarmour.crypto_task.presentation.currencies_screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ru.webarmour.crypto_task.R
import ru.webarmour.crypto_task.domain.util.CurrencyOrder
import ru.webarmour.crypto_task.domain.util.OrderType
import ru.webarmour.crypto_task.presentation.MainViewModel
import ru.webarmour.crypto_task.presentation.Screen
import ru.webarmour.crypto_task.presentation.core_ui.CardItem
import ru.webarmour.crypto_task.presentation.theme.header
import ru.webarmour.crypto_task.presentation.theme.primary
import ru.webarmour.crypto_task.presentation.theme.textDefaults


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesScreen(
    navController: NavHostController,
    textColor: Color = textDefaults,
    backgroundColor: Color = header,
    showBottomNavigation: MutableState<Boolean>,
) {
    showBottomNavigation.value = true

    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
//    val currencyState by viewModel.currencyState.collectAsStateWithLifecycle()

    val currencies = mutableListOf("USD", "EUR", "JPY", "GBP").also {
        it.remove(state.coins.chosenCoin)
    }
    var base: String by rememberSaveable { mutableStateOf(state.coins.chosenCoin) }

    Log.d("CurrenciesScreen", "State: $state")

    LaunchedEffect(base) {
        viewModel.loadCurrency(CurrencyOrder.Name(OrderType.Ascending), base)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Currencies",
                    style = MaterialTheme.typography.headlineLarge,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = it
                        }
                    ) {
                        OutlinedTextField(
                            value = state.coins.chosenCoin,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .focusable(false)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.White)
                                .border(BorderStroke(1.dp, primary), RoundedCornerShape(6.dp))
                                .menuAnchor()
                                .weight(1f),
                            trailingIcon = {
                                Icon(
                                    painterResource(
                                        R.drawable.arrow_down
                                    ),
                                    contentDescription = null,
                                    tint = primary
                                )
                            },
                        )
                        ExposedDropdownMenu(
                            modifier = Modifier
                                .background(Color.White)
                                .clip(RoundedCornerShape(4.dp))
                                .border(
                                    width = 1.dp,
                                    color = primary,
                                    shape = RoundedCornerShape(4.dp)
                                ),
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {
                            currencies.forEach { currency ->
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.updateBaseCurrency(currency)
                                        base = currency
                                        Log.d("MyTag", "$base , $currency")
                                        expanded = false
                                    },
                                    text = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Text(
                                                text = currency,
                                                color = textColor
                                            )
                                        }
                                    },
                                    enabled = true,
                                    colors = MenuDefaults.itemColors(),
                                    contentPadding = PaddingValues(16.dp)
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.White)
                            .border(BorderStroke(1.dp, primary), RoundedCornerShape(6.dp))
                            .padding(8.dp)
                            .size(40.dp)

                    ) {
                        IconButton(onClick = {
                            if (base.isNotEmpty()) {
                                navController.navigate(Screen.Filters.route + "/$base")
                            }
                            Log.d("MyTag", "$base")

                        }) {
                            Icon(
                                painterResource(R.drawable.filter), contentDescription = "Filter",
                                tint = primary
                            )
                        }
                    }

                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (state.isLoading) {

                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )

            } else {
                val filteredRatesModel =
                    state.coins.ratesModel.filter { it.name != state.coins.chosenCoin }
                LazyColumn(
                    Modifier.fillMaxWidth()
                ) {
                    items(filteredRatesModel) { coin ->
                        CardItem(currentModel = coin)
                    }
                }
            }
        }
    }
}