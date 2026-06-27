package com.weatherai.ui

import com.weatherai.data.Clouds
import com.weatherai.data.Coordinates
import com.weatherai.data.MainWeatherData
import com.weatherai.data.SystemData
import com.weatherai.data.Weather
import com.weatherai.data.WeatherRepository
import com.weatherai.data.WeatherResponse
import com.weatherai.data.Wind
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var repository: WeatherRepository

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test fetchWeather updates UI state with weather data`() = runTest {
        // Arrange
        val mockWeather = createMockWeather()
        whenever(
            repository.getCurrentWeather(
                latitude = 50.0,
                longitude = 36.0,
                apiKey = "test_key"
            )
        ).thenReturn(flowOf(Result.success(mockWeather)))

        // Act
        viewModel.fetchWeather(50.0, 36.0, "test_key")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(viewModel.uiState.value.weather == mockWeather)
        assert(viewModel.uiState.value.isLoading == false)
    }

    @Test
    fun `test fetchWeather handles error`() = runTest {
        // Arrange
        val exception = Exception("Network error")
        whenever(
            repository.getCurrentWeather(
                latitude = 50.0,
                longitude = 36.0,
                apiKey = "test_key"
            )
        ).thenReturn(flowOf(Result.failure(exception)))

        // Act
        viewModel.fetchWeather(50.0, 36.0, "test_key")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(viewModel.uiState.value.error != null)
        assert(viewModel.uiState.value.isLoading == false)
    }

    @Test
    fun `test updateLocation sets current location`() {
        // Act
        viewModel.updateLocation(50.0, 36.0, "Kyiv")

        // Assert
        assert(viewModel.currentLocation.value?.cityName == "Kyiv")
        assert(viewModel.currentLocation.value?.latitude == 50.0)
        assert(viewModel.currentLocation.value?.longitude == 36.0)
    }

    @Test
    fun `test clearError removes error message`() = runTest {
        // Arrange
        val exception = Exception("Test error")
        whenever(
            repository.getCurrentWeather(
                latitude = 50.0,
                longitude = 36.0,
                apiKey = "test_key"
            )
        ).thenReturn(flowOf(Result.failure(exception)))

        viewModel.fetchWeather(50.0, 36.0, "test_key")
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.uiState.value.error != null)

        // Act
        viewModel.clearError()

        // Assert
        assert(viewModel.uiState.value.error == null)
    }

    private fun createMockWeather(): WeatherResponse {
        return WeatherResponse(
            coord = Coordinates(lon = 36.0, lat = 50.0),
            weather = listOf(
                Weather(
                    id = 800,
                    main = "Clear",
                    description = "clear sky",
                    icon = "01d"
                )
            ),
            main = MainWeatherData(
                temp = 20.0,
                feelsLike = 19.0,
                tempMin = 18.0,
                tempMax = 22.0,
                pressure = 1013,
                humidity = 65
            ),
            visibility = 10000,
            wind = Wind(speed = 5.0, deg = 180, gust = 7.0),
            clouds = Clouds(all = 0),
            dt = System.currentTimeMillis() / 1000,
            sys = SystemData(
                country = "UA",
                sunrise = 1609459200,
                sunset = 1609495200
            ),
            timezone = 7200,
            id = 703550,
            name = "Kyiv",
            cod = 200
        )
    }
}
