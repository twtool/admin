plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)

    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.depentency.management)
}

dependencies {
    api(libs.apache.commons.lang3)
    api(libs.apache.commons.codec)
    api(libs.jackson.module.kotlin)
    api(libs.kotlin.reflect)
    api(libs.slf4j.api)

    api(libs.mybatis.plus.boot.starter)

    api(libs.spring.boot.starter.security)
    api(libs.spring.boot.starter.data.redis)
    api(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.validation)
}

val customGeneratedFile = layout.buildDirectory.file("generated/custom/main/kotlin")


sourceSets {
    main {
        kotlin.srcDirs(customGeneratedFile.get(), kotlin.srcDirs)
    }
}

tasks {

    val commonDir = customGeneratedFile.get().asFile.resolve("icu/twtool/common")
    if (commonDir.exists() || commonDir.mkdirs()) {
        commonDir.resolve("TwtoolAdminVersion.kt")
            .writeText(
                """
                    package icu.twtool.common
                    
                    /**
                     * 公开 Twtool Admin 版本
                     *
                     * @author wen-flower
                     */
                    object TwtoolAdminVersion {
                        
                        const val VERSION = "$version"
                        
                    }
                    """.trimIndent(),
            )
    }

}

