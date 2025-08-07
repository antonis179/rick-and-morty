package com.amoustakos.rickandmorty

import org.gradle.api.Project


object App {
    fun version(project: Project): Int {
        val props = Methods.getEnv(project)
        return Methods.getAttribute(props, "app.version").toInt()
    }

    fun versionName(project: Project): String {
        val props = Methods.getEnv(project)
        return Methods.getAttribute(props, "app.version.name")
    }
}