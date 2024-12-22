package ru.webarmour.crypto_task.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.webarmour.crypto_task.domain.CurrencyModel
import ru.webarmour.crypto_task.domain.RatesModel
import ru.webarmour.crypto_task.domain.use_case.CheckForFavouriteUseCase
import ru.webarmour.crypto_task.domain.use_case.DeleteCoinFromDbUseCase
import ru.webarmour.crypto_task.domain.use_case.GetAllFromDbUseCase
import ru.webarmour.crypto_task.domain.use_case.GetCoinUseCase
import ru.webarmour.crypto_task.domain.use_case.InsertCoinToDbUseCase
import ru.webarmour.crypto_task.domain.util.CurrencyOrder
import ru.webarmour.crypto_task.domain.util.OrderType
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    private val insertCoinToDbUseCase: InsertCoinToDbUseCase,
    private val deleteFromDbUseCase: DeleteCoinFromDbUseCase,
    private val getAllFromDbUseCase: GetAllFromDbUseCase,
    private val checkForFavouriteUseCase: CheckForFavouriteUseCase,
) : ViewModel() {


    fun loadCurrency(currencyOrder: CurrencyOrder, baseCoin: String) {
        viewModelScope.launch {
            getCoinUseCase(
                currencyOrder, baseCoin
            ).onStart {
                _state.value = state.value.copy(
                    isLoading = true
                )
            }.collect { sortedCurrencyModel ->
                _state.value = state.value.copy(
                    isLoading = false,
                    coins = sortedCurrencyModel,
                    selectedCoin = sortedCurrencyModel.chosenCoin
                )
            }
        }
    }

//    fun updateSortOrder(currencyOrder: CurrencyOrder, baseCoin: String) {
//        viewModelScope.launch {
//            loadCurrency(currencyOrder, baseCoin)
//        }
//    }

    private val _selectedFilterState = MutableStateFlow<CurrencyOrder>(
        CurrencyOrder.Name(OrderType.Descending)
    )
    val selectedFilterState = _selectedFilterState.asStateFlow()

    fun updateSelectedFilterOption(order: CurrencyOrder) {
        viewModelScope.launch {
            _selectedFilterState.value = order
        }

    }


    private val _favouritesState = MutableStateFlow<List<RatesModel>>(emptyList())

    val favoriteState = _favouritesState
        .onStart {
            getAllFromDb()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val _state = MutableStateFlow(CurrencyState())

    val state = _state
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CurrencyState(
                isLoading = true,
                coins = CurrencyModel(
                    chosenCoin = "USD",
                    ratesModel = listOf()
                )
            )
        )

    fun updateBaseCurrency(baseCurrency: String) {
        _state.value = state.value.copy(
            coins = state.value.coins.copy(chosenCoin = baseCurrency)
        )
        Log.d("MainScreenViewModel", "Base currency updated to: $baseCurrency")
    }

    private fun getAllFromDb() {
        viewModelScope.launch {
            getAllFromDbUseCase().collect {
                _favouritesState.emit(it)
            }

        }
    }


    private fun loadData(baseCoin: String) {
        viewModelScope.launch {
            getCoinUseCase(
                CurrencyOrder.Listing(OrderType.Descending), baseCoin = baseCoin
            ).onStart {
                _state.value = state.value.copy(
                    isLoading = true,
                )
                Log.d("MainScreenViewModel", "Loading data...")
            }.collect { coin ->
                _state.value = state.value.copy(
                    isLoading = false,
                    coins = coin
                )
                Log.d("MainScreenViewModel", "Data loaded: $coin")
            }
        }

    }

    fun insertCoinToDb(model: RatesModel) {
        viewModelScope.launch {
            insertCoinToDbUseCase(model)
        }
    }

    suspend fun isFavourite(coin: RatesModel): Boolean {
        return checkForFavouriteUseCase(coin)
    }

    fun deleteCoinFromDb(model: RatesModel) {
        viewModelScope.launch {
            deleteFromDbUseCase(model)
        }
    }

}