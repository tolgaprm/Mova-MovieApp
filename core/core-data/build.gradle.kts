plugins {
    id("mova.layer.data")
}

android {
    namespace = "com.prmto.core_data"
}

dependencies {
    api(project(":database"))
}