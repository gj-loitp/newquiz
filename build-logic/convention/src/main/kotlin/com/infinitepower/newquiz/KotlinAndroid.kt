package com.infinitepower.newquiz

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, * , * , *, *>
) {
    commonExtension.apply {
        compileSdk = ProjectConfig.compileSdk

        defaultConfig {
            minSdk = ProjectConfig.minSdk
        }

        compileOptions {
            sourceCompatibility = ProjectConfig.javaVersionCompatibility
            targetCompatibility = ProjectConfig.javaVersionCompatibility
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
                excludes += "/META-INF/LICENSE.md"
                excludes += "/META-INF/LICENSE-notice.md"
            }
        }
    }

    configureKotlinAndroid()
}

internal fun Project.configureKotlinJvm() {
    extensions.apply {
        configure<JavaPluginExtension> {
            sourceCompatibility = ProjectConfig.javaVersionCompatibility
            targetCompatibility = ProjectConfig.javaVersionCompatibility
        }

        configure<KotlinJvmProjectExtension> {
            jvmToolchain(ProjectConfig.jvmToolchainVersion)
        }
    }
}

private fun Project.configureKotlinAndroid() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = ProjectConfig.jvmTargetVersion

            freeCompilerArgs += listOf(
                "-opt-in=kotlin.RequiresOptIn"
            )
        }
    }

    extensions.configure<KotlinProjectExtension> {
        jvmToolchain(ProjectConfig.jvmToolchainVersion)
    }
}