package com.mehmettekin.kapalicarsiapp.data.repository

import com.mehmettekin.kapalicarsiapp.data.KapaliCarsiApi
import com.mehmettekin.kapalicarsiapp.domain.repository.KapaliCarsiRepository
import com.mehmettekin.kapalicarsiapp.util.ResultState
import com.mehmettekin.kapalicarsiapp.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import java.io.IOException
import com.mehmettekin.kapalicarsiapp.domain.model.ExchangeRate

class KapaliCarsiRepositoryImpl @Inject constructor(
    private val api: KapaliCarsiApi
) : KapaliCarsiRepository {

    override suspend fun getExchangeRates(): Flow<ResultState<List<ExchangeRate>>> = flow {
        try {
            emit(ResultState.Loading)
            val rates = api.getExchangeRates()
            emit(ResultState.Success(rates))
        } catch (e: HttpException) {
            emit(ResultState.Error(UiText.dynamicString(e.localizedMessage ?: "Unexpected error occurred")))
        } catch (e: IOException) {
            emit(ResultState.Error(UiText.dynamicString("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(ResultState.Error(UiText.dynamicString(e.localizedMessage ?: "Unknown error occurred")))
        }
    }
}