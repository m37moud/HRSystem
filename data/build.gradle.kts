plugins {
    kotlin("jvm")
    kotlin("kapt")
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

}
