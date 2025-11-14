import org.gradle.api.JavaVersion.VERSION_25
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(libs.mordant)
    testImplementation(platform(libs.kotest.bom))
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.framework.engine)
    testImplementation(libs.kotest.assertions.core)
    constraints {
        implementation(libs.mordant) {
            version { strictly(libs.mordant.get().version!!) }
            because("This is what we're testing")
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
        jvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
    }
    withType<JavaCompile>().configureEach {
        if (JavaVersion.current() == VERSION_25) {
            options.release.set(24)
        }
    }
    withType<KotlinCompile>().configureEach {
        if (JavaVersion.current() == VERSION_25) {
            compilerOptions.jvmTarget = JVM_24
        }
    }
}
