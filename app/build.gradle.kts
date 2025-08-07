import com.amoustakos.rickandmorty.Config_gradle.Config

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
	compileSdk = Config.BuildParams.compileVersion

	defaultConfig {
		applicationId = "com.amoustakos.rickandmorty"
		minSdk = Config.BuildParams.minVersion
		targetSdk = Config.BuildParams.targetVersion
		versionCode = Config.App.version(project)
		versionName = Config.App.versionName(project)

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
		val props by lazy { Config.Methods.getPasswords(project) }

		create("debugSigning") {
			val loc = Config.Methods.getAttribute(props, Config.KeyConfig.debugLocation)
			val pwd = Config.Methods.getAttribute(props, Config.KeyConfig.debugPwd)
			val alias = Config.Methods.getAttribute(props, Config.KeyConfig.debugAlias)
			val aliasPwd = Config.Methods.getAttribute(props, Config.KeyConfig.debugAliasPwd)

			storeFile = project.file(loc)
			storePassword = pwd
			keyAlias = alias
			keyPassword = aliasPwd
		}
		create("releaseSigning") {
			val loc = Config.Methods.getAttribute(props, Config.KeyConfig.releaseLocation)
			val pwd = Config.Methods.getAttribute(props, Config.KeyConfig.releasePwd)
			val alias = Config.Methods.getAttribute(props, Config.KeyConfig.releaseAlias)
			val aliasPwd = Config.Methods.getAttribute(props, Config.KeyConfig.releaseAliasPwd)

			storeFile = project.file(loc)
			storePassword = pwd
			keyAlias = alias
			keyPassword = aliasPwd
		}
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
				getDefaultProguardFile("proguard-android-optimize.txt"), // Use optimize for release
				"${project.rootDir}/config/proguard-rules.pro"
			)
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_17.toString()
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