pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val VERSION_NAME: String? by settings
            if (!VERSION_NAME.isNullOrBlank()) {
                version("mordant", VERSION_NAME!!)
            }
        }
    }
}
