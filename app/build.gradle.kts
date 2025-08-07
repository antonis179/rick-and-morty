import com.amoustakos.rickandmorty.App
import com.amoustakos.rickandmorty.BuildParams
import com.amoustakos.rickandmorty.Keys
import com.amoustakos.rickandmorty.Methods
import com.android.build.api.dsl.ApkSigningConfig

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.amoustakos.rickandmorty"
    compileSdk = BuildParams.COMPILE_VERSION

    defaultConfig {
        applicationId = "com.amoustakos.rickandmorty"
        minSdk = BuildParams.MIN_VERSION
        targetSdk = BuildParams.TARGET_VERSION
        versionCode = App.version(project)
        versionName = App.versionName(project)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    flavorDimensions("stores")

    productFlavors {
        create("play") {
            dimension = "stores"
        }
    }

    signingConfigs {
        create("debugSigning", debugSigning())
        create("releaseSigning", releaseSigning())
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debugSigning")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("releaseSigning")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "${project.rootDir}/config/proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = BuildParams.JAVA_VERSION
        targetCompatibility = BuildParams.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = BuildParams.JAVA_VERSION.toString()
    }
}

hilt {
    enableAggregatingTask = true
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    implementation(libs.hilt.android)

    //TODO: Replace with serialization if possible
    implementation(libs.gson)

    ksp(libs.hilt.android.compiler)

    // Compose (ref: https://developer.android.com/jetpack/compose/bom/bom-mapping)
    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.kotlin.bom))
    //https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-bom
    implementation(platform(libs.kotlinx.coroutines.bom))

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.foundation)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui.tooling.data)
    implementation(libs.androidx.ui.viewbinding)
    implementation(libs.androidx.animation)
    implementation(libs.androidx.animation.core)
    implementation(libs.androidx.animation.graphics)
    implementation(libs.androidx.constraintlayout.compose)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)

    implementation(libs.timber)

    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer)

    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.serialization.json)
}

fun debugSigning(): (ApkSigningConfig).() -> Unit = {
    val props by lazy { Methods.getPasswords(project) }

    val loc = Methods.getAttribute(props, Keys.DEBUG_LOCATION)
    val pwd = Methods.getAttribute(props, Keys.DEBUG_PWD)
    val alias = Methods.getAttribute(props, Keys.DEBUG_ALIAS)
    val aliasPwd = Methods.getAttribute(props, Keys.DEBUG_ALIAS_PWD)

    storeFile = project.file(loc)
    storePassword = pwd
    keyAlias = alias
    keyPassword = aliasPwd
}

fun releaseSigning(): (ApkSigningConfig).() -> Unit = {
    val props by lazy { Methods.getPasswords(project) }
    val loc = Methods.getAttribute(props, Keys.RELEASE_LOCATION)
    val pwd = Methods.getAttribute(props, Keys.RELEASE_PWD)
    val alias = Methods.getAttribute(props, Keys.RELEASE_ALIAS)
    val aliasPwd = Methods.getAttribute(props, Keys.RELEASE_ALIAS_PWD)

    storeFile = project.file(loc)
    storePassword = pwd
    keyAlias = alias
    keyPassword = aliasPwd
}