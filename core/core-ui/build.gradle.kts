plugins {
    id("mova.layer.ui")
}

android {
    namespace = "com.prmto.core_ui"
}

dependencies {
    api(project(":navigation"))
}