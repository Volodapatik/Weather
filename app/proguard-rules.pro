# Retrofit
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn retrofit2.**

# Gson
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-dontwarn dagger.hilt.**

# OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# Coroutines
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# Jetpack Compose
-keep class androidx.compose.** { *; }
-keep interface androidx.compose.** { *; }

# Models
-keep class com.weatherai.data.** { *; }
-keepclassmembers class com.weatherai.data.** {
    <fields>;
    <methods>;
}

# Keep our classes
-keep class com.weatherai.** { *; }
-keepclassmembers class com.weatherai.** {
    <fields>;
    <methods>;
}

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Remove logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# Optimization
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose

# Obfuscation
-obfuscationdictionary dictionary.txt
-classobfuscationdictionary dictionary.txt
-packageobfuscationdictionary dictionary.txt
