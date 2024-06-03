import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false

    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.depentency.management) apply false
}

subprojects {
    group = "icu.twtool"
    version = "0.0.1-SNAPSHOT"

    afterEvaluate {
        tasks.withType<Test> {
            useJUnitPlatform()
        }

        tasks.withType<KotlinCompile> {
            compilerOptions {
                freeCompilerArgs.add("-Xjsr305=strict")
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }

        extensions.findByType<JavaPluginExtension>()?.apply {
            sourceCompatibility = JavaVersion.VERSION_17
        }

        configurations {
            this.findByName("compileOnly")?.apply {
                configurations.findByName("annotationProcessor")?.let {
                    extendsFrom(it)
                }
            }
        }
    }

}