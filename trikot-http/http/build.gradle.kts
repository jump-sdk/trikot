plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("mirego.release").version("2.0")
    id("mirego.publish").version("1.0")
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

kotlin {
    android {
        publishAllLibraryVariants()
    }
    jvm()
    ios()
    iosArm32("iosArm32")
//    iosSimulatorArm64()
    tvos()
    watchos()
    macosX64()
    js(IR) {
        moduleName = "@trikot/http"
        browser()
        binaries.executable()
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.ExperimentalStdlibApi")
        }

        val commonMain by getting {
            dependencies {
                implementation(project(Project.TRIKOT_FOUNDATION))
                implementation(project(Project.TRIKOT_STREAMS))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLINX_SERIALIZATION}")
                implementation("io.ktor:ktor-http:${Versions.KTOR}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
                implementation("org.jetbrains.kotlin:kotlin-reflect")
                implementation("junit:junit:4.13.2")
                implementation("io.mockk:mockk-common:1.12.1")
            }
        }

        val jvmMain by getting {
            dependsOn(commonMain)
        }

        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

        val jsMain by getting {
            dependsOn(commonMain)
        }

        val jsTest by getting {
            dependsOn(commonTest)
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                api("io.ktor:ktor-client-logging-jvm:${Versions.KTOR}")
                implementation("io.ktor:ktor-client-android:${Versions.KTOR}")
                implementation("androidx.lifecycle:lifecycle-extensions:${Versions.ANDROIDX_LIFECYCLE_EXTENSIONS}")
                implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.3.0") {
                    exclude(group = "org.reactivestreams")
                }
            }
        }

        val nativeMain by creating {
            dependsOn(commonMain)
        }

        val iosArm32Main by getting {
            dependsOn(nativeMain)
        }

        val iosArm64Main by getting {
            dependsOn(nativeMain)
        }

//        val iosSimulatorArm64Main by getting {
//            dependsOn(iosArm64Main)
//        }

        val iosX64Main by getting {
            dependsOn(nativeMain)
        }

        val tvosArm64Main by getting {
            dependsOn(nativeMain)
        }

        val tvosX64Main by getting {
            dependsOn(nativeMain)
        }

        val watchos32Main by creating {
            dependsOn(nativeMain)
        }

        val watchosArm64Main by getting {
            dependsOn(nativeMain)
        }

        val watchosX64Main by getting {
            dependsOn(nativeMain)
        }

        val macosX64Main by getting {
            dependsOn(nativeMain)
        }
    }
}

android {
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK
    }
}

release {
    checkTasks = listOf("check")
    buildTasks = listOf("publish")
    updateVersionPart = 2
}
