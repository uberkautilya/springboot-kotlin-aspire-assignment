package io.mcm.kotlinaspireassignment.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean


class SwaggerConfiguration {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI().info(
            Info().title("Swagger for CourseManagement")
                .version("1.0.0")
                .description("Swagger UI for CourseManagement")
                .termsOfService("http://mcm.io/terms")
                .license(
                    License().name("Training").url("http://mcm.io/license")
                )
        )
    }
}