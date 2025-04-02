package com.mehmettekin.kapalicarsiapp.domain.repository

import com.mehmettekin.kapalicarsiapp.domain.model.ExchangeRate
import com.mehmettekin.kapalicarsiapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface KapaliCarsiRepository {
    suspend fun getExchangeRates(): Flow<ResultState<List<ExchangeRate>>>
}