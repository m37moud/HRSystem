//https://medium.com/@theendik00/sqldelight-for-postgresql-on-kotlin-jvm-b95d14d96134

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("com.squareup.sqldelight") version "1.5.4"
}


sqldelight {
    database("HrAppDb") { // This will be the name of the generated database class.
//   HrAppDb  { // This will be the name of the generated database class.
        packageName = "com.hrappv"
		  schemaOutputDirectory = file("src/main/sqldelight/databases")
    }
}

group = "com.HrAppV"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    // Dagger
    val daggerVersion: String by rootProject.extra
    api("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    // Arbor : Like Timber, just different.
    api("com.ToxicBakery.logging:arbor-jvm:1.34.109")

    //excel
    implementation("org.apache.poi:poi:3.17")
    implementation("org.apache.poi:poi-ooxml:3.17")


//    // sqlDelight //https://cashapp.github.io/sqldelight/1.5.4/jvm_sqlite/gradle/
    val sqlDelight = "1.5.4"
    implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelight")
//    implementation("com.squareup.sqldelight:native-driver:$sqlDelight")
    implementation("com.squareup.sqldelight:coroutines-extensions-jvm:$sqlDelight")


}
//
//kotlin {
//    // or sourceSets.iosMain, sourceSets.nativeMain, etc.windowsMain
//    sourceSets.nativeMain.dependencies {
//        implementation("com.squareup.sqldelight:native-driver:1.5.4")
//    }
//}

