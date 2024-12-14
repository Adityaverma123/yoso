plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.openapi.generator") version "7.7.0"
}

group = "com.yoso"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

openApiGenerate {
	generatorName.set("kotlin-spring")
	inputSpec.set("$rootDir/src/main/resources/specs.yml")
	outputDir.set("${layout.buildDirectory.get()}/generated")
	apiPackage.set("com.ecom.controllers")
	modelPackage.set("com.ecom.model")
	invokerPackage.set("com.cherry.invoker")
	configOptions.set(
		mapOf(
			"serializationLibrary" to "jackson",
			"dateLibrary" to "java8",
			"interfaceOnly" to "true",
			"enumPropertyNaming" to "UPPERCASE",
			"useBeanValidation" to "false",
			"useSpringBoot3" to "true",
			"useCoroutines" to "true",
			"mutableModels" to "true"
		)
	)
	additionalProperties.set(
		mapOf(
			"modelMutable" to "true"
		)
	)
	typeMappings.value(
		mapOf(
			"DateTime" to "java.time.LocalDateTime",
			"array" to "kotlin.collections.List"
		)
	)

}

tasks.named("compileKotlin") {
	dependsOn("openApiGenerate")
}
tasks.named("build") {
	dependsOn("openApiGenerate")
}
sourceSets {
	main {
		java {
			srcDir("${layout.buildDirectory.get()}/generated/src/main/kotlin")
		}
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
