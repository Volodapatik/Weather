# Weather AI - Android App 🌤️

Мобільний додаток для прогнозу погоди з ШІ-аналізом, розроблений на Kotlin з використанням Jetpack Compose та інтеграцією Groq API (Llama 3.3).

## 🌟 Особливості

- **Точний прогноз погоди** - Інтеграція з OpenWeatherMap API
- **ШІ-аналіз** - Детальний аналіз погодних умов за допомогою Llama 3.3
- **Красивий UI** - Jetpack Compose з анімаціями та градієнтами
- **Рекомендації** - Персоналізовані рекомендації на основі погодних умов
- **Прогноз на 5 днів** - Детальний прогноз з погодинними даними
- **Пошук міст** - Швидкий пошук погоди за назвою міста
- **Темна тема** - Підтримка темної теми з Material Design 3

## 🔧 Технологічний стек

- **Kotlin 1.9.0** - Мова програмування
- **Jetpack Compose 1.5.4** - Сучасний UI фреймворк
- **Retrofit 2.10.0** - HTTP клієнт
- **Hilt 2.47** - Dependency Injection
- **Coroutines 1.7.3** - Асинхронне програмування
- **Material3 1.1.2** - Design System
- **Lottie** - Анімації
- **Coil** - Завантаження зображень

## 📋 Вимоги

- Android 7.0 (API 24) або вище
- JDK 17 або новіше
- Gradle 8.0+

## 🔑 API Ключі

### OpenWeatherMap API
Отримати ключ: https://openweathermap.org/api

### Groq API (Llama 3.3)
Отримати ключ: https://console.groq.com

## 🚀 Запуск

### Локально

```bash
# Клонувати репозиторій
git clone https://github.com/Volodapatik/Weather.git
cd Weather

# Встановити API ключі у WeatherScreen.kt
# app/src/main/java/com/weatherai/ui/screens/WeatherScreen.kt

# Побудувати APK
./gradlew assembleDebug

# APK буде у: app/build/outputs/apk/debug/app-debug.apk
```

### GitHub Actions

APK автоматично будується при кожному push на `main` гілку.

1. Перейти на вкладку **Actions**
2. Вибрати останній workflow **Build APK**
3. Завантажити APK з **Artifacts**

## 📁 Структура проекту

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/weatherai/
│   │   │   ├── MainActivity.kt
│   │   │   ├── data/
│   │   │   ├── network/
│   │   │   ├── di/
│   │   │   ├── ui/
│   │   │   └── utils/
│   │   └── res/
│   ├── test/
│   └── androidTest/
└── build.gradle.kts
```

## 🧪 Тестування

```bash
# Unit тести
./gradlew test

# Instrumented тести
./gradlew connectedAndroidTest
```

## 📝 Документація

- [README.md](README.md) - Основна документація
- [SETUP.md](SETUP.md) - Інструкції налаштування
- [CHANGELOG.md](CHANGELOG.md) - Історія змін

## 🐛 Відомі проблеми

- Геолокація ще не реалізована
- Локальне кешування даних ще не активне

## 🔄 Планові функції

- [ ] Геолокація користувача
- [ ] Збереження улюблених міст
- [ ] Сповіщення про погоду
- [ ] Графіки температури
- [ ] Поділ у соціальних мережах
- [ ] Офлайн режим
- [ ] Віджети

## 📞 Контакти

Розроблено з ❤️ на Kotlin

## 📄 Ліцензія

MIT License - дивіться файл LICENSE для деталей

---

**Останнє оновлення**: 2026-06-27
