package com.weatherai.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherai.data.LocationData
import com.weatherai.data.WeatherRepository
import com.weatherai.data.WeatherUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState: StateFlow<WeatherUIState> = _uiState.asStateFlow()

    private val _currentLocation = MutableStateFlow<LocationData?>(null)
    val currentLocation: StateFlow<LocationData?> = _currentLocation.asStateFlow()

    fun updateLocation(latitude: Double, longitude: Double, cityName: String) {
        _currentLocation.value = LocationData(latitude, longitude, cityName)
    }

    fun fetchWeather(
        latitude: Double,
        longitude: Double,
        weatherApiKey: String
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            repository.getCurrentWeather(latitude, longitude, weatherApiKey)
                .collect { result ->
                    result.onSuccess { weather ->
                        _uiState.update { 
                            it.copy(
                                weather = weather,
                                isLoading = false,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        // Отримати прогноз
                        fetchForecast(latitude, longitude, weatherApiKey)
                    }
                    result.onFailure { error ->
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "Невідома помилка"
                            )
                        }
                    }
                }
        }
    }

    private fun fetchForecast(
        latitude: Double,
        longitude: Double,
        weatherApiKey: String
    ) {
        viewModelScope.launch {
            repository.getForecast(latitude, longitude, weatherApiKey)
                .collect { result ->
                    result.onSuccess { forecast ->
                        _uiState.update { it.copy(forecast = forecast) }
                        // Отримати ШІ аналіз
                        val weather = _uiState.value.weather
                        if (weather != null) {
                            fetchAIAnalysis(weather, "")
                        }
                    }
                }
        }
    }

    fun fetchAIAnalysis(
        weatherApiKey: String
    ) {
        val weather = _uiState.value.weather ?: return
        fetchAIAnalysis(weather, weatherApiKey)
    }

    private fun fetchAIAnalysis(
        weather: com.weatherai.data.WeatherResponse,
        groqApiKey: String
    ) {
        viewModelScope.launch {
            val description = weather.weather.firstOrNull()?.description ?: "Невідомо"
            val temperature = weather.main.temp
            val humidity = weather.main.humidity
            val windSpeed = weather.wind.speed

            repository.analyzeWeatherWithAI(
                description,
                temperature,
                humidity,
                windSpeed,
                groqApiKey
            ).collect { result ->
                result.onSuccess { analysis ->
                    _uiState.update { it.copy(aiAnalysis = analysis) }
                }
                result.onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            aiAnalysis = "Помилка при отриманні аналізу: ${error.message}"
                        )
                    }
                }
            }
        }
    }

    fun searchCity(
        cityName: String,
        weatherApiKey: String
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            repository.getWeatherByCity(cityName, weatherApiKey)
                .collect { result ->
                    result.onSuccess { weather ->
                        _currentLocation.value = LocationData(
                            weather.coord.lat,
                            weather.coord.lon,
                            weather.name
                        )
                        _uiState.update { 
                            it.copy(
                                weather = weather,
                                isLoading = false,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        fetchForecast(weather.coord.lat, weather.coord.lon, weatherApiKey)
                    }
                    result.onFailure { error ->
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "Місто не знайдено"
                            )
                        }
                    }
                }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
