package ru.webarmour.crypto_task.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
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

    private var _baseCurrency: String? = null
    val baseCurrency: String?
        get() = _baseCurrency

    private val _currencyState = MutableStateFlow<CurrencyModel?>(null)
    val currencyState = _currencyState.asStateFlow()

    fun loadCurrency(currencyOrder: CurrencyOrder, baseCoin: String) {
        viewModelScope.launch {
            getCoinUseCase(
                currencyOrder, baseCoin
            ).collect { sortedCurrencyModel ->
                _currencyState.emit(sortedCurrencyModel)
                _baseCurrency = baseCoin
            }
        }
    }

    fun updateSortOrder(currencyOrder: CurrencyOrder, baseCoin: String) {
        viewModelScope.launch {
            loadCurrency(currencyOrder, baseCoin)
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
        .onStart {
            loadData("EUR")
            loadCurrency(
                currencyOrder = CurrencyOrder.Name(OrderType.Descending),
                baseCoin = "EUR"
            )
        }
        .retry(5) {
            false
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CurrencyState(
                selectedCoin = CurrencyModel(
                    chosenCoin = "",
                    ratesModel = listOf()
                )
            )
        )

    fun updateBaseCurrency(baseCurrency: String) {
        _state.value = state.value.copy(
            coins = state.value.coins.copy(chosenCoin = baseCurrency)
        )
        loadData(baseCurrency)
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
                _baseCurrency = coin.chosenCoin
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