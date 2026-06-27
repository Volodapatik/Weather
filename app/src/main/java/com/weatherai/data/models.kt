package com.weatherai.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// OpenWeatherMap Models
data class WeatherResponse(
    val coord: Coordinates,
    val weather: List<Weather>,
    val main: MainWeatherData,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: SystemData,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

data class Coordinates(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class MainWeatherData(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double? = null
)

data class Clouds(
    val all: Int
)

data class SystemData(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

// Forecast Model
data class ForecastResponse(
    val list: List<ForecastItem>,
    val city: CityData
)

data class ForecastItem(
    val dt: Long,
    val main: MainWeatherData,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    @SerializedName("dt_txt")
    val dtTxt: String
)

data class CityData(
    val id: Int,
    val name: String,
    val coord: Coordinates,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)

// Groq AI Response Model
data class GroqResponse(
    val id: String,
    val object: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)

data class Choice(
    val index: Int,
    val message: Message,
    @SerializedName("finish_reason")
    val finishReason: String
)

data class Message(
    val role: String,
    val content: String
)

data class Usage(
    @SerializedName("prompt_tokens")
    val promptTokens: Int,
    @SerializedName("completion_tokens")
    val completionTokens: Int,
    @SerializedName("total_tokens")
    val totalTokens: Int
)

// Database Entity
@Entity(tableName = "weather_cache")
data class WeatherEntity(
    @PrimaryKey
    val id: String,
    val city: String,
    val temperature: Double,
    val description: String,
    val icon: String,
    val humidity: Int,
    val windSpeed: Double,
    val aiAnalysis: String,
    val timestamp: Long
)

// UI State Models
data class WeatherUIState(
    val isLoading: Boolean = false,
    val weather: WeatherResponse? = null,
    val forecast: ForecastResponse? = null,
    val aiAnalysis: String? = null,
    val error: String? = null,
    val lastUpdated: Long = 0
)

data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val cityName: String
)
