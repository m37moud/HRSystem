//https://medium.com/@theendik00/sqldelight-for-postgresql-on-kotlin-jvm-b95d14d96134

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("com.squareup.sqldelight") //version "1.5.4"
}


// sqldelight {
//     database(name = "HockeyDatabase") { // This will be the name of the generated database class.
//         packageName = "com.HrAppV"
//         dialect = "postgresql"
//         deriveSchemaFromMigrations = true
//     }
// }

// pluginManagement {
//     repositories {
//         google()
//         mavenCentral()
//         gradlePluginPortal() // Not sure if you need this
//     }

//     resolutionStrategy {
//         eachPlugin {
//             if (requested.id.id == "com.squareup.sqldelight") {
//                 useModule("com.squareup.sqldelight:gradle-plugin:1.5.3")
//             }
//         }
//     }
// }

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


//     // PostgreSQL JDBC driver
//     implementation("org.postgresql:postgresql:42.5.0")
    
//     // Hikari requires SLF4J
//     implementation("org.slf4j:slf4j-reload4j:2.0.0")
    
//     // Hikari connection manager
//     implementation("com.zaxxer:HikariCP:5.0.1")
    
//     // Converts javax.sql.DataSource from your connection manager to com.squareup.sqldelight.db.JdbcDriver
//     implementation("com.squareup.sqldelight:jdbc-driver:1.5.3")


}


// sqldelight {
//     sqldelight.com.squareup.sqlite.migrations.Database { // This will be the name of the generated database class.
//         packageName = "com.HrAppV"
//         dialect = "mysql"
//     }
// }
