package ru.webarmour.crypto_task.presentation.filter_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ru.webarmour.crypto_task.R
import ru.webarmour.crypto_task.domain.util.CurrencyOrder
import ru.webarmour.crypto_task.domain.util.OrderType
import ru.webarmour.crypto_task.presentation.MainViewModel
import ru.webarmour.crypto_task.presentation.theme.header
import ru.webarmour.crypto_task.presentation.theme.primary
import ru.webarmour.crypto_task.presentation.theme.textDefaults
import ru.webarmour.crypto_task.presentation.theme.text_secondary


@Composable
fun FilterScreen(
    navController: NavHostController,
    textColor: Color = textDefaults,
    backgroundColor: Color = header,
    viewModel: MainViewModel = hiltViewModel(),
    showBottomNavigation: MutableState<Boolean>,
    _baseCoin: String,
) {
    showBottomNavigation.value = false
    val sortOptions = listOf(
        SortOption("Code A-Z", CurrencyOrder.Name(OrderType.Descending)),
        SortOption("Code Z-A", CurrencyOrder.Name(OrderType.Ascending)),
        SortOption("Quote Asc.", CurrencyOrder.Listing(OrderType.Ascending)),
        SortOption("Quote Desc.", CurrencyOrder.Listing(OrderType.Descending))

    )

    Log.d("MyTag", "$_baseCoin")
    val selectedFilterState by viewModel.selectedFilterState.collectAsStateWithLifecycle()
    var filter by remember { mutableStateOf(selectedFilterState) }
    val baseCoin by rememberSaveable { mutableStateOf(_baseCoin) }

    LaunchedEffect(selectedFilterState) {
        filter = selectedFilterState
    }
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
                    text = stringResource(R.string.filter_screen),
                    style = MaterialTheme.typography.headlineLarge,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(R.string.sort_by),
                fontWeight = FontWeight.Bold,
                color = text_secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            RadioButtonList(sortOptions = sortOptions, selectedOption = filter) { option ->
                filter = option
            }
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
            Button(
                colors = ButtonColors(
                    containerColor = primary,
                    contentColor = Color.White,
                    disabledContainerColor = backgroundColor,
                    disabledContentColor = backgroundColor
                ),
                onClick = {
                    filter?.let {
                        viewModel.loadCurrency(it, baseCoin = baseCoin)
                        viewModel.updateSelectedFilterOption(it)
                        Log.d(
                            "MyTag",
                            "${it.toString()} $baseCoin, selected option $filter"
                        )
                    }

                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Apply")
            }
        }
    }
}