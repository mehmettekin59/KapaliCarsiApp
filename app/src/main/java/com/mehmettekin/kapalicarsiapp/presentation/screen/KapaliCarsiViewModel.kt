package com.mehmettekin.kapalicarsiapp.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmettekin.kapalicarsiapp.domain.model.ExchangeRate
import com.mehmettekin.kapalicarsiapp.domain.repository.KapaliCarsiRepository
import com.mehmettekin.kapalicarsiapp.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class KapaliCarsiViewModel @Inject constructor(
    private val repository: KapaliCarsiRepository
) : ViewModel() {

    private val _exchangeRates = MutableStateFlow<ResultState<List<ExchangeRate>>>(ResultState.Idle)
    val exchangeRates: StateFlow<ResultState<List<ExchangeRate>>> = _exchangeRates

    init {
        getExchangeRates()
    }

    fun getExchangeRates() {
        viewModelScope.launch {
            repository.getExchangeRates().collectLatest { result ->
                _exchangeRates.value = result
            }
        }
    }

    fun sortByCode() {
        if (_exchangeRates.value is ResultState.Success) {
            val currentRates = (_exchangeRates.value as ResultState.Success<List<ExchangeRate>>).data
            _exchangeRates.value = ResultState.Success(currentRates.sortedBy { it.code })
        }
    }

    fun sortByBuyingPrice() {
        if (_exchangeRates.value is ResultState.Success) {
            val currentRates = (_exchangeRates.value as ResultState.Success<List<ExchangeRate>>).data
            _exchangeRates.value = ResultState.Success(
                currentRates.sortedBy {
                    it.alis.toString().replace(",", ".").toDoubleOrNull() ?: 0.0
                }
            )
        }
    }

    fun filterByKeyword(keyword: String) {
        if (_exchangeRates.value is ResultState.Success) {
            val allRates = (_exchangeRates.value as ResultState.Success<List<ExchangeRate>>).data
            if (keyword.isBlank()) {
                _exchangeRates.value = ResultState.Success(allRates)
            } else {
                val filtered = allRates.filter {
                    it.code.contains(keyword, ignoreCase = true)
                }
                _exchangeRates.value = ResultState.Success(filtered)
            }
        }
    }
}