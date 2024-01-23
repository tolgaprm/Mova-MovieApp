package com.prmto.convention

import com.android.build.api.dsl.DefaultConfig
import java.util.Properties

fun DefaultConfig.addBuildConfigField(
    properties: Properties,
    name: String
) {
    buildConfigField(
        "String",
        name,
        "\"${properties.getProperty(name)}\""
    )
}

fun DefaultConfig.addBuildConfigField(
    name: String,
    value: String
) {
    buildConfigField("String", name, "\"$value\"")
}