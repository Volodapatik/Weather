package com.weatherai.network

import com.weatherai.data.GroqResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GroqApiService {
    @POST("openai/v1/chat/completions")
    suspend fun analyzeWeather(
        @Header("Authorization") authorization: String,
        @Body request: GroqChatRequest
    ): GroqResponse
}

data class GroqChatRequest(
    val model: String = "llama-3.3-70b-versatile",
    val messages: List<GroqMessage>,
    val temperature: Double = 0.7,
    val max_tokens: Int = 1024,
    val top_p: Double = 1.0,
    val stream: Boolean = false
)

data class GroqMessage(
    val role: String,
    val content: String
)

object GroqConstants {
    const val BASE_URL = "https://api.groq.com/"
    const val MODEL = "llama-3.3-70b-versatile"
}
