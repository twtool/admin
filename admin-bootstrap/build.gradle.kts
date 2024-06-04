plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)

    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.depentency.management)
}

dependencies {
    implementation(projects.adminCommon)
    implementation(projects.adminFramework)

    implementation(projects.adminSystem)

    implementation(libs.mysql.connector)

//    annotationProcessor(libs.spring.boot.configuration.processor)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.security.test)
}

