pluginManagement {
    repositories {
       // gradlePluginPortal()
      //  maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}
rootProject.name = "HrAppV"
include("data")
