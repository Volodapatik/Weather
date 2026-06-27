package com.weatherai.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.weatherai.data.WeatherResponse
import com.weatherai.ui.WeatherViewModel
import com.weatherai.utils.WeatherUtils

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentLocation by viewModel.currentLocation.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }

    // TODO: Замінити на реальні API ключі
    val weatherApiKey = "830b510f9c4b651a5b71945b5105828f"
    val groqApiKey = "gsk_7RI9Fh7wRt7yBGsOXLvDWGdyb3FY2e G1ytcKQv6i060SGrbmNH2R"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF87CEEB),
                        Color(0xFFE0F6FF)
                    )
                )
            )
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = Color.White
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header з пошуком
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Weather AI",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        
                        Row {
                            IconButton(
                                onClick = { showSearch = !showSearch }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            
                            IconButton(
                                onClick = {
                                    currentLocation?.let {
                                        viewModel.fetchWeather(
                                            it.latitude,
                                            it.longitude,
                                            weatherApiKey
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Refresh",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }

                // Пошукове поле
                if (showSearch) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp),
                                placeholder = { Text("Введіть назву міста") },
                                singleLine = true
                            )
                            
                            Button(
                                onClick = {
                                    if (searchQuery.isNotEmpty()) {
                                        viewModel.searchCity(searchQuery, weatherApiKey)
                                        searchQuery = ""
                                    }
                                },
                                modifier = Modifier.height(50.dp)
                            ) {
                                Text("Пошук")
                            }
                        }
                    }
                }

                // Основна карточка з погодою
                if (uiState.weather != null) {
                    item {
                        MainWeatherCard(
                            weather = uiState.weather!!,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Карточка з рекомендаціями
                if (uiState.weather != null) {
                    item {
                        RecommendationCard(
                            weather = uiState.weather!!,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // ШІ Аналіз
                if (uiState.aiAnalysis != null) {
                    item {
                        AIAnalysisCard(
                            analysis = uiState.aiAnalysis!!,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Прогноз на 5 днів
                if (uiState.forecast != null) {
                    item {
                        ForecastCard(
                            forecast = uiState.forecast!!,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Помилка
                if (uiState.error != null) {
                    item {
                        ErrorCard(
                            error = uiState.error!!,
                            onDismiss = { viewModel.clearError() },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainWeatherCard(
    weather: WeatherResponse,
    modifier: Modifier = Modifier
) {
    val description = weather.weather.firstOrNull()?.description ?: "Невідомо"
    val icon = WeatherUtils.getWeatherIcon(description)
    val (startColor, endColor) = WeatherUtils.getBackgroundGradient(description)

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut()
    ) {
        Card(
            modifier = modifier
                .height(300.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(startColor, endColor)
                    ),
                    shape = RoundedCornerShape(20.dp)
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = weather.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = icon,
                    fontSize = 80.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = WeatherUtils.formatTemperature(weather.main.temp),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = description.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherDetailItem(
                        label = "Вологість",
                        value = "${weather.main.humidity}%",
                        color = Color.White
                    )
                    WeatherDetailItem(
                        label = "Вітер",
                        value = "${String.format("%.1f", weather.wind.speed)} м/с",
                        color = Color.White
                    )
                    WeatherDetailItem(
                        label = "Тиск",
                        value = "${weather.main.pressure} hPa",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun RecommendationCard(
    weather: WeatherResponse,
    modifier: Modifier = Modifier
) {
    val recommendation = WeatherUtils.getWeatherRecommendation(
        weather.main.temp,
        weather.main.humidity,
        weather.wind.speed
    )

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "💡 Рекомендації",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = recommendation,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun AIAnalysisCard(
    analysis: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "🤖 ШІ Аналіз",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = analysis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ForecastCard(
    forecast: com.weatherai.data.ForecastResponse,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "📅 Прогноз на 5 днів",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            forecast.list.take(8).forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = WeatherUtils.formatDateTime(item.dt),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = WeatherUtils.getWeatherIcon(
                            item.weather.firstOrNull()?.description ?: ""
                        ),
                        fontSize = 20.sp
                    )
                    Text(
                        text = WeatherUtils.formatTemperature(item.main.temp),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorCard(
    error: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "❌ Помилка",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFC62828)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Закрити")
            }
        }
    }
}

@Composable
fun WeatherDetailItem(
    label: String,
    value: String,
    color: Color = Color.Black
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = color.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}
