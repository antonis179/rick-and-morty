package com.amoustakos.rickandmorty

import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.FileInputStream
import java.util.Properties


object Methods {
    @JvmStatic
    fun getProperties(project: Project, filename: String, fromRoot: Boolean): Properties {
        val props = Properties()
        val secondaryPath = if (fromRoot) "/" else "/_KEYS_/"
        val path = project.rootDir.path + secondaryPath + filename
        FileInputStream(path).use { props.load(it) }
        return props
    }

    @JvmStatic
    fun checkAttribute(attribute: String?, key: String) {
        if (attribute.isNullOrEmpty()) {
            throw GradleException("Missing Attribute for key $key")
        }
    }

    @JvmStatic
    fun getAttribute(props: Properties, key: String): String {
        val attribute = props.getProperty(key)
        checkAttribute(attribute, key)
        return attribute
    }

    @JvmStatic
    fun getEnv(project: Project): Properties =
        getProperties(project, "environment.properties", true)

    @JvmStatic
    fun getPasswords(project: Project): Properties =
        getProperties(project, "passwords.properties", false)
}