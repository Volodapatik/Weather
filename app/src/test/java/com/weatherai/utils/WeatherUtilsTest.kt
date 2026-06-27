package com.weatherai.utils

import org.junit.Test
import org.junit.Assert.*

class WeatherUtilsTest {

    @Test
    fun `test getWeatherIcon for rain`() {
        val icon = WeatherUtils.getWeatherIcon("rainy")
        assertEquals("🌧️", icon)
    }

    @Test
    fun `test getWeatherIcon for clear sky`() {
        val icon = WeatherUtils.getWeatherIcon("clear sky")
        assertEquals("☀️", icon)
    }

    @Test
    fun `test getWeatherIcon for clouds`() {
        val icon = WeatherUtils.getWeatherIcon("cloudy")
        assertEquals("☁️", icon)
    }

    @Test
    fun `test getWeatherIcon for snow`() {
        val icon = WeatherUtils.getWeatherIcon("snow")
        assertEquals("❄️", icon)
    }

    @Test
    fun `test getWeatherIcon for thunderstorm`() {
        val icon = WeatherUtils.getWeatherIcon("thunderstorm")
        assertEquals("⛈️", icon)
    }

    @Test
    fun `test formatTemperature`() {
        val formatted = WeatherUtils.formatTemperature(20.5)
        assertEquals("20.5°C", formatted)
    }

    @Test
    fun `test formatTemperature with negative value`() {
        val formatted = WeatherUtils.formatTemperature(-5.3)
        assertEquals("-5.3°C", formatted)
    }

    @Test
    fun `test getWeatherRecommendation for cold temperature`() {
        val recommendation = WeatherUtils.getWeatherRecommendation(
            temp = -5.0,
            humidity = 50,
            windSpeed = 3.0
        )
        assertTrue(recommendation.contains("холодно"))
    }

    @Test
    fun `test getWeatherRecommendation for hot temperature`() {
        val recommendation = WeatherUtils.getWeatherRecommendation(
            temp = 30.0,
            humidity = 50,
            windSpeed = 3.0
        )
        assertTrue(recommendation.contains("Гарячо"))
    }

    @Test
    fun `test getWeatherRecommendation for high humidity`() {
        val recommendation = WeatherUtils.getWeatherRecommendation(
            temp = 20.0,
            humidity = 85,
            windSpeed = 3.0
        )
        assertTrue(recommendation.contains("Висока вологість"))
    }

    @Test
    fun `test getWeatherRecommendation for strong wind`() {
        val recommendation = WeatherUtils.getWeatherRecommendation(
            temp = 20.0,
            humidity = 50,
            windSpeed = 12.0
        )
        assertTrue(recommendation.contains("Сильний вітер"))
    }
}
