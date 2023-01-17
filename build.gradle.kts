import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//https://medium.com/@theendik00/sqldelight-for-postgresql-on-kotlin-jvm-b95d14d96134

plugins {
//    val kotlinVersion = "1.6.10"
//    kotlin("jvm") version kotlinVersion
//    kotlin("kapt") version kotlinVersion
//    id("org.jetbrains.compose") version "1.1.0"
    val kotlinVersion = "1.7.10"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev764" //"1.2.0-alpha01-dev764"
//    id("com.squareup.sqldelight") version "1.5.4"
}

group = "com.hrappv"
version = "1.0.0"

repositories {
    jcenter()
    mavenCentral()
    mavenLocal()
    google()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven {
        url = uri("https://repo.spring.io/release")
    }
    maven {
        url = uri("https://repository.jboss.org/maven2")
    }
}

val daggerVersion by extra("2.44.2")

dependencies {
    implementation(compose.desktop.currentOs)

    // Module dependencies
    implementation(project(":data"))

//    implementation("androidx.compose.material3:material3:1.0.1")
//    implementation("androidx.compose.material3:material3-window-size-class:1.0.1")

    // Dagger : A fast dependency injector for Android and Java.

    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    kaptTest("com.google.dagger:dagger-compiler:$daggerVersion")
//    kapt ("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.4.2")

    // Cyclone : https://github.com/theapache64/cyclone
    implementation("com.theapache64:cyclone:1.0.0-alpha01")
    // Build, Execution, Deployment | Compiler | Kotlin Compiler | Kotlin Compiler version'

    // Decompose : Decompose
//    val decomposeVersion = "0.2.5"
//    implementation("com.arkivanov.decompose:decompose-jvm:$decomposeVersion")
//    implementation("com.arkivanov.decompose:extensions-compose-jetbrains-jvm:$decomposeVersion")

//     Decompose : Decompose
    implementation("com.arkivanov.decompose:decompose:1.0.0-alpha-04")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:1.0.0-alpha-04")


    //corotunie swing
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")



    //Icons
    implementation("br.com.devsrsouza.compose.icons.jetbrains:font-awesome:1.0.0")
    //date
    implementation("com.github.lgooddatepicker:LGoodDatePicker:11.2.1")


    //material date and time picker 0.2.9 // Latest Stable Library Version: 0.9.0 (Supports Compose 1.2.1)
//    implementation("com.vanpra.compose-material-dialogs:datetime:0.2.9")//0.8.0
    //implementation("com.vanpra.compose-material-dialogs:core:0.8.0")
//    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.9.0")
//    implementation("com.afollestad.material-dialogs:input:3.3.0")


//lottie
//val lottieVersion = "5.2.0"

  //implementation("com.airbnb.android:lottie-compose:$lottieVersion")


    // implementation("com.github.ltttttttttttt.ComposeViews:maven_desktop:$version")//this, such as 1.3.4

//


    /**
     * Testing Dependencies
     */
    testImplementation("org.mockito:mockito-inline:3.7.7")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    // DaggerMock
    testImplementation("com.github.fabioCollini.daggermock:daggermock:0.8.5")
    testImplementation("com.github.fabioCollini.daggermock:daggermock-kotlin:0.8.5")

    // Mockito Core : Mockito mock objects library core API and implementation
    testImplementation("org.mockito:mockito-core:3.7.7")

    // Expekt : An assertion library for Kotlin
    testImplementation("com.github.theapache64:expekt:1.0.0")

    // JUnit

    // Kotlinx Coroutines Test : Coroutines support libraries for Kotlin
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3")
    testImplementation(compose("org.jetbrains.compose.ui:ui-test-junit4"))

    // JUnit : JUnit is a unit testing framework for Java, created by Erich Gamma and Kent Beck.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.7.22")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"//"11"
}
//tasks.withType<KotlinCompile> {
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//}
//
//tasks.withType<JavaCompile> {
//    sourceCompatibility = JavaVersion.VERSION_1_8
//    targetCompatibility = JavaVersion.VERSION_1_8
//}


/*
tasks.withType(JavaCompile) {
    options.compilerArgs << "-Xlint:unchecked" << "-Xlint:deprecation"
    options.encoding = 'UTF-8'
    options.fork = true
    options.compilerArgs << "-parameters"
    options.targetCompatibility = '1.8'
}
*/

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

compose.desktop {
    application {
        mainClass = "com.HrAppV.AppKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "HrAppV"
            packageVersion = "1.0.0"

            val iconsRoot = project.file("src/main/resources/drawables")

            linux {
                iconFile.set(iconsRoot.resolve("launcher_icons/linux.png"))
            }

            windows {
                iconFile.set(iconsRoot.resolve("launcher_icons/windows.ico"))
            }

            macOS {
                iconFile.set(iconsRoot.resolve("launcher_icons/macos.icns"))
            }

        }
    }
}
//
