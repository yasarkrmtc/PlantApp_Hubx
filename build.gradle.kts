buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.0")
    }

}

plugins {
    id ("com.android.application") version "8.0.2" apply false
    id ("com.android.library" )version "8.0.2" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id ("androidx.navigation.safeargs.kotlin") version "2.8.0" apply false
    id ("com.google.dagger.hilt.android") version "2.48" apply false
}