package com.mehmettekin.kapalicarsiapp.data

import com.mehmettekin.kapalicarsiapp.domain.model.ExchangeRate
import retrofit2.http.GET

interface KapaliCarsiApi {
    @GET("/")
    suspend fun getExchangeRates(): List<ExchangeRate>
}