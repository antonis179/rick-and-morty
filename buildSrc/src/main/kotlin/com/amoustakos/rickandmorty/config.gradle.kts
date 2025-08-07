package com.amoustakos.rickandmorty

import java.io.FileInputStream
import java.util.Properties

object Config {
	object BuildParams {
		const val compileVersion = 35
		const val minVersion = 26
		const val targetVersion = 35
	}

	object KeyConfig {
		const val debugLocation = "debug.key.location"
		const val debugPwd = "debug.key.password"
		const val debugAlias = "debug.key.alias"
		const val debugAliasPwd = "debug.key.alias.password"

		const val releaseLocation = "release.key.location"
		const val releasePwd = "release.key.password"
		const val releaseAlias = "release.key.alias"
		const val releaseAliasPwd = "release.key.alias.password"

		const val appVersion = "app.version"
		const val appVersionName = "app.version.name"
	}

	object Methods {
		fun getProperties(project: Project, filename: String, fromRoot: Boolean): Properties {
			val props = Properties()
			val secondaryPath = if (fromRoot) "/" else "/_KEYS_/"
			val path = project.rootDir.path + secondaryPath + filename
			FileInputStream(path).use { props.load(it) }
			return props
		}

		fun checkAttribute(attribute: String?, key: String) {
			if (attribute.isNullOrEmpty()) {
				throw GradleException("Missing Attribute for key $key")
			}
		}

		fun getAttribute(props: Properties, key: String): String {
			val attribute = props.getProperty(key)
			checkAttribute(attribute, key)
			return attribute
		}

		fun getStringifiedAttribute(props: Properties, key: String): String {
			val attribute = getAttribute(props, key)
			return "\"$attribute\""
		}

		fun getEnv(project: Project): Properties = getProperties(project, "environment.properties", true)
		fun getPasswords(project: Project): Properties = getProperties(project, "passwords.properties", false)
	}

	object App {
		fun version(project: Project): Int {
			val props = Methods.getEnv(project)
			return Methods.getAttribute(props, KeyConfig.appVersion).toInt()
		}

		fun versionName(project: Project): String {
			val props = Methods.getEnv(project)
			return Methods.getAttribute(props, KeyConfig.appVersionName)
		}
	}
}
