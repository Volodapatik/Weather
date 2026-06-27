package com.weatherai.data

import com.weatherai.network.GroqApiService
import com.weatherai.network.GroqChatRequest
import com.weatherai.network.GroqConstants
import com.weatherai.network.GroqMessage
import com.weatherai.network.WeatherApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val groqApiService: GroqApiService
) {

    fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): Flow<Result<WeatherResponse>> = flow {
        try {
            val response = weatherApiService.getCurrentWeather(latitude, longitude, apiKey)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getForecast(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): Flow<Result<ForecastResponse>> = flow {
        try {
            val response = weatherApiService.getForecast(latitude, longitude, apiKey)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getWeatherByCity(
        cityName: String,
        apiKey: String
    ): Flow<Result<WeatherResponse>> = flow {
        try {
            val response = weatherApiService.getWeatherByCity(cityName, apiKey)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun analyzeWeatherWithAI(
        weatherDescription: String,
        temperature: Double,
        humidity: Int,
        windSpeed: Double,
        apiKey: String
    ): Flow<Result<String>> = flow {
        try {
            val prompt = """
                Проаналізуй наступні дані про погоду та дай детальний прогноз та рекомендації:
                
                Опис: $weatherDescription
                Температура: ${temperature}°C
                Вологість: $humidity%
                Швидкість вітру: $windSpeed м/с
                
                Надай:
                1. Детальний прогноз на наступні години
                2. Рекомендації щодо одягу та діяльності
                3. Попередження про небезпечні умови (якщо є)
                4. Цікаві факти про цю погоду
                
                Відповідай українською мовою, коротко та інформативно.
            """.trimIndent()

            val request = GroqChatRequest(
                model = GroqConstants.MODEL,
                messages = listOf(
                    GroqMessage(
                        role = "user",
                        content = prompt
                    )
                ),
                temperature = 0.7,
                max_tokens = 1024
            )

            val response = groqApiService.analyzeWeather(
                authorization = "Bearer $apiKey",
                request = request
            )

            val analysis = response.choices.firstOrNull()?.message?.content 
                ?: "Не вдалось отримати аналіз"
            
            emit(Result.success(analysis))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
