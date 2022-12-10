
plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("com.squareup.sqldelight") version "1.5.4"
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


    // sqlDelight
    val  sqlDelight  = "1.5.4"
    implementation("com.squareup.sqldelight:gradle-plugin:$sqlDelight")
    implementation("com.squareup.sqldelight:jdbc-driver:$sqlDelight")


}


sqldelight {
    sqldelight.com.squareup.sqlite.migrations.Database { // This will be the name of the generated database class.
        packageName = "com.HrAppV"
        dialect = "mysql"
    }
}
