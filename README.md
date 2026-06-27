# Weather AI - Android App

Мобільний додаток для прогнозу погоди з ШІ-аналізом, розроблений на Kotlin з використанням Jetpack Compose та інтеграцією Groq API (Llama 3.3).

## 🌟 Особливості

- **Точний прогноз погоди** - Інтеграція з OpenWeatherMap API
- **ШІ-аналіз** - Детальний аналіз погодних умов за допомогою Llama 3.3
- **Красивий UI** - Jetpack Compose з анімаціями та градієнтами
- **Рекомендації** - Персоналізовані рекомендації на основі погодних умов
- **Прогноз на 5 днів** - Детальний прогноз з погодинними даними
- **Пошук міст** - Швидкий пошук погоди за назвою міста
- **Темна тема** - Підтримка темної теми з Material Design 3

## 📋 Вимоги

- Android 7.0 (API 24) або вище
- Gradle 8.1.0
- Kotlin 1.9.0
- Java 17

## 🔑 API Ключі

Для роботи додатку потрібні два API ключі:

### 1. OpenWeatherMap API
1. Перейти на https://openweathermap.org/api
2. Зареєструватися та отримати безплатний API ключ
3. Замінити `YOUR_OPENWEATHERMAP_API_KEY` у файлі `WeatherScreen.kt`

### 2. Groq API (Llama 3.3)
1. Перейти на https://console.groq.com
2. Отримати API ключ
3. Ключ вже вказаний у коді: `gsk_7RI9Fh7wRt7yBGsOXLvDWGdyb3FY2e G1ytcKQv6i060SGrbmNH2R`

## 🚀 Запуск проекту

### За допомогою Android Studio

1. Відкрити проект у Android Studio
2. Дочекатися завантаження залежностей Gradle
3. Підключити Android пристрій або запустити емулятор
4. Натиснути `Run` або `Shift + F10`

### За допомогою командного рядка

```bash
# Побудувати проект
./gradlew build

# Запустити на пристрої/емуляторі
./gradlew installDebug

# Запустити тести
./gradlew test
```

## 📁 Структура проекту

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/weatherai/
│   │   │   ├── MainActivity.kt
│   │   │   ├── data/
│   │   │   │   ├── models.kt
│   │   │   │   └── WeatherRepository.kt
│   │   │   ├── network/
│   │   │   │   ├── GroqApiService.kt
│   │   │   │   └── WeatherApiService.kt
│   │   │   ├── di/
│   │   │   │   └── NetworkModule.kt
│   │   │   ├── ui/
│   │   │   │   ├── WeatherViewModel.kt
│   │   │   │   ├── screens/
│   │   │   │   │   └── WeatherScreen.kt
│   │   │   │   └── theme/
│   │   │   │       ├── Theme.kt
│   │   │   │       └── Type.kt
│   │   │   └── utils/
│   │   │       └── WeatherUtils.kt
│   │   ├── res/
│   │   │   ├── values/
│   │   │   │   ├── strings.xml
│   │   │   │   ├── colors.xml
│   │   │   │   └── styles.xml
│   │   │   └── AndroidManifest.xml
│   ├── test/
│   └── androidTest/
└── build.gradle.kts
```

## 🔧 Основні залежності

- **Jetpack Compose** - Сучасний UI фреймворк
- **Retrofit** - HTTP клієнт для API запитів
- **Hilt** - Dependency Injection фреймворк
- **Coroutines** - Асинхронне програмування
- **Room** - Локальна база даних (опціонально)
- **Lottie** - Анімації
- **Coil** - Завантаження зображень

## 📝 Функціональність

### Поточна версія (v1.0)

- ✅ Отримання поточної погоди за координатами
- ✅ ШІ-аналіз погодних умов
- ✅ Прогноз на 5 днів
- ✅ Пошук міст
- ✅ Красивий UI з анімаціями
- ✅ Рекомендації на основі погоди
- ✅ Темна тема

### Планові функції

- 🔄 Геолокація користувача
- 🔄 Збереження улюблених міст
- 🔄 Сповіщення про погоду
- 🔄 Графіки температури
- 🔄 Поділ у соціальних мережах

## 🧪 Тестування

```bash
# Запустити unit тести
./gradlew test

# Запустити instrumented тести
./gradlew connectedAndroidTest

# Запустити з покриттям
./gradlew testDebugUnitTestCoverage
```

## 🐛 Відомі проблеми

- Потрібно замінити API ключ OpenWeatherMap перед використанням
- Геолокація ще не реалізована
- Локальне кешування даних ще не активне

## 📞 Контакти

Для питань та пропозицій звертайтеся до розробника.

## 📄 Ліцензія

Цей проект розповсюджується під ліцензією MIT.

---

**Розроблено з ❤️ на Kotlin**
