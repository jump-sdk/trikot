plugins {
    id("com.android.library")
    id("kotlin-android")
    id("mirego.publish")
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://plugins.gradle.org/m2/")
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

group = "com.mirego.trikot"

android {
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK
    }

    buildFeatures {
        compose = true
        buildConfig = false
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.JETPACK_COMPOSE
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

kotlin {
    sourceSets.all {
        languageSettings.optIn("coil.annotation.ExperimentalCoilApi")
    }
}

dependencies {
    implementation(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE))
    implementation(project(Project.TRIKOT_FOUNDATION))
    implementation(project(Project.TRIKOT_STREAMS))

    api("androidx.compose.foundation:foundation:${Versions.JETPACK_COMPOSE}")
    api("androidx.compose.material:material:${Versions.JETPACK_COMPOSE}")
    api("androidx.compose.runtime:runtime:${Versions.JETPACK_COMPOSE}")
    api("androidx.compose.ui:ui-tooling:${Versions.JETPACK_COMPOSE}")
    api("io.coil-kt:coil-compose:${Versions.COIL}")

    implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN}")
}

tasks {
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(android.sourceSets.getByName("main").java.srcDirs().toString())
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "viewmodels-declarative-compose"
                artifact(tasks["sourcesJar"])
            }
            create<MavenPublication>("debug") {
                from(components["debug"])
                artifactId = "viewmodels-declarative-compose"
                artifact(tasks["sourcesJar"])
            }
        }
    }
}