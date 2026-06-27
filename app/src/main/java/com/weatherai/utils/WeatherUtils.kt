package com.weatherai.utils

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object WeatherUtils {

    fun getWeatherIcon(description: String): String {
        return when {
            description.contains("rain", ignoreCase = true) -> "🌧️"
            description.contains("cloud", ignoreCase = true) -> "☁️"
            description.contains("clear", ignoreCase = true) || description.contains("sunny", ignoreCase = true) -> "☀️"
            description.contains("snow", ignoreCase = true) -> "❄️"
            description.contains("thunder", ignoreCase = true) -> "⛈️"
            description.contains("wind", ignoreCase = true) -> "💨"
            description.contains("fog", ignoreCase = true) || description.contains("mist", ignoreCase = true) -> "🌫️"
            else -> "🌤️"
        }
    }

    fun getWeatherColor(description: String): Color {
        return when {
            description.contains("rain", ignoreCase = true) -> Color(0xFF4A90E2)
            description.contains("cloud", ignoreCase = true) -> Color(0xFF95A5A6)
            description.contains("clear", ignoreCase = true) || description.contains("sunny", ignoreCase = true) -> Color(0xFFF39C12)
            description.contains("snow", ignoreCase = true) -> Color(0xFFECF0F1)
            description.contains("thunder", ignoreCase = true) -> Color(0xFF34495E)
            description.contains("wind", ignoreCase = true) -> Color(0xFF3498DB)
            description.contains("fog", ignoreCase = true) || description.contains("mist", ignoreCase = true) -> Color(0xFFBDC3C7)
            else -> Color(0xFF87CEEB)
        }
    }

    fun getBackgroundGradient(description: String): Pair<Color, Color> {
        return when {
            description.contains("rain", ignoreCase = true) -> 
                Pair(Color(0xFF2C3E50), Color(0xFF34495E))
            description.contains("cloud", ignoreCase = true) -> 
                Pair(Color(0xFF95A5A6), Color(0xFF7F8C8D))
            description.contains("clear", ignoreCase = true) || description.contains("sunny", ignoreCase = true) -> 
                Pair(Color(0xFF87CEEB), Color(0xFFFFA500))
            description.contains("snow", ignoreCase = true) -> 
                Pair(Color(0xFFECF0F1), Color(0xFFBDC3C7))
            description.contains("thunder", ignoreCase = true) -> 
                Pair(Color(0xFF1A1A2E), Color(0xFF16213E))
            else -> 
                Pair(Color(0xFF87CEEB), Color(0xFFE0F6FF))
        }
    }

    fun formatTemperature(temp: Double): String = String.format("%.1f°C", temp)

    fun formatTime(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return format.format(date)
    }

    fun formatDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val format = SimpleDateFormat("dd MMM yyyy", Locale("uk", "UA"))
        return format.format(date)
    }

    fun formatDateTime(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val format = SimpleDateFormat("dd MMM HH:mm", Locale("uk", "UA"))
        return format.format(date)
    }

    fun getWeatherRecommendation(temp: Double, humidity: Int, windSpeed: Double): String {
        return buildString {
            when {
                temp < 0 -> append("❄️ Дуже холодно! Одягніться теплішим одягом.\n")
                temp < 10 -> append("🧥 Холодно. Рекомендується теплий одяг.\n")
                temp < 20 -> append("🧢 Прохолодно. Візьміть легку куртку.\n")
                temp < 25 -> append("👕 Комфортна температура.\n")
                else -> append("☀️ Гарячо! Пийте більше води.\n")
            }

            when {
                humidity > 80 -> append("💧 Висока вологість. Може бути душно.\n")
                humidity < 30 -> append("🏜️ Низька вологість. Зволожуйте шкіру.\n")
            }

            when {
                windSpeed > 10 -> append("💨 Сильний вітер. Будьте обережні.\n")
                windSpeed > 5 -> append("🌬️ Помірний вітер.\n")
            }
        }
    }
}
