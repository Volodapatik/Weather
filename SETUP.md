# Налаштування Weather AI Android App

## Передумови

- **Android Studio** 2023.1.1 або новіше
- **JDK 17** або новіше
- **Android SDK** з API 34
- **Gradle** 8.1.0

## Крок 1: Клонування та відкриття проекту

```bash
# Перейти в директорію проекту
cd weather-ai-android

# Відкрити в Android Studio
open -a "Android Studio" .
```

## Крок 2: Налаштування API Ключів

### OpenWeatherMap API

1. Перейти на https://openweathermap.org/api
2. Натиснути "Sign Up" та створити акаунт
3. Перейти в "API keys" та скопіювати ключ
4. Відкрити файл `app/src/main/java/com/weatherai/ui/screens/WeatherScreen.kt`
5. Замінити рядок:
   ```kotlin
   val weatherApiKey = "YOUR_OPENWEATHERMAP_API_KEY"
   ```
   на ваш ключ

### Groq API (Llama 3.3)

Groq API ключ уже вказаний у коді. Якщо потрібно оновити:

1. Перейти на https://console.groq.com
2. Отримати новий API ключ
3. Замінити значення у файлі `WeatherScreen.kt`

## Крок 3: Синхронізація Gradle

1. Відкрити проект у Android Studio
2. Натиснути `File → Sync Now`
3. Дочекатися завершення синхронізації

## Крок 4: Запуск на емуляторі

### Створення емулятора (якщо його немає)

1. Відкрити `Tools → Device Manager`
2. Натиснути `Create device`
3. Вибрати пристрій (наприклад, Pixel 6)
4. Вибрати образ системи (API 34 або вище)
5. Завершити налаштування

### Запуск додатку

1. Вибрати емулятор у випадаючому списку
2. Натиснути кнопку `Run` (Shift + F10)
3. Дочекатися запуску додатку

## Крок 5: Запуск на реальному пристрої

1. Підключити Android пристрій до комп'ютера
2. Увімкнути режим розробника на пристрої
3. Дозволити USB debugging
4. Вибрати пристрій у Android Studio
5. Натиснути `Run`

## Структура Gradle

### build.gradle.kts (корневий)

Містить конфігурацію для всіх модулів та плагіни.

### app/build.gradle.kts

Містить залежності та конфігурацію для app модуля:

- **Compose версія**: 1.5.4
- **Material3 версія**: 1.1.2
- **Retrofit версія**: 2.10.0
- **Hilt версія**: 2.47

## Налаштування IDE

### Android Studio

1. Перейти в `Preferences → Editor → Code Style → Kotlin`
2. Встановити правила форматування
3. Перейти в `Preferences → Plugins` та встановити:
   - Kotlin
   - Gradle
   - Android Support

### Налаштування Lint

Додати файл `lint.xml` у `app/src/main/`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<lint>
    <issue id="MissingTranslation" severity="ignore" />
    <issue id="ExtraTranslation" severity="ignore" />
</lint>
```

## Розробка

### Структура пакетів

```
com.weatherai
├── data/          # Моделі даних та репозиторії
├── network/       # API сервіси
├── di/            # Dependency Injection
├── ui/            # UI компоненти та екрани
├── utils/         # Утиліти та допоміжні функції
└── MainActivity.kt
```

### Додавання нових залежностей

1. Відкрити `app/build.gradle.kts`
2. Додати залежність у блок `dependencies`
3. Натиснути `Sync Now`

Приклад:
```kotlin
implementation("com.example:library:1.0.0")
```

### Запуск тестів

```bash
# Unit тести
./gradlew test

# Instrumented тести
./gradlew connectedAndroidTest

# Конкретний тест
./gradlew test --tests "com.weatherai.data.WeatherRepositoryTest"
```

## Debugging

### Логування

Використовувати стандартний Android Log:

```kotlin
import android.util.Log

Log.d("WeatherTag", "Debug message")
Log.e("WeatherTag", "Error message", exception)
```

### Logcat

1. Відкрити `View → Tool Windows → Logcat`
2. Вибрати пристрій та процес
3. Фільтрувати за тегом або пакетом

### Breakpoints

1. Клацнути на номер рядка для встановлення breakpoint
2. Запустити додаток у режимі debug (Shift + F9)
3. Додаток зупиниться на breakpoint

## Проблеми та рішення

### Gradle синхронізація не працює

```bash
./gradlew clean
./gradlew build
```

### Додаток падає при запуску

1. Перевірити логи у Logcat
2. Переконатися, що API ключі вказані правильно
3. Перевірити дозволи у AndroidManifest.xml

### Помилка при компіляції Kotlin

```bash
./gradlew clean
./gradlew build --stacktrace
```

## Публікація

### Підготовка до публікації

1. Оновити версію у `app/build.gradle.kts`
2. Оновити `README.md` та `CHANGELOG.md`
3. Запустити тести: `./gradlew test`
4. Побудувати release: `./gradlew assembleRelease`

### Підписання APK

1. Створити keystore:
   ```bash
   keytool -genkey -v -keystore release.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias release
   ```

2. Підписати APK:
   ```bash
   jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore release.keystore app-release-unsigned.apk release
   ```

3. Оптимізувати APK:
   ```bash
   zipalign -v 4 app-release-unsigned.apk app-release.apk
   ```

## Корисні посилання

- [Android Developers](https://developer.android.com/)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Hilt Documentation](https://dagger.dev/hilt/)

---

**Останнє оновлення**: 2024
