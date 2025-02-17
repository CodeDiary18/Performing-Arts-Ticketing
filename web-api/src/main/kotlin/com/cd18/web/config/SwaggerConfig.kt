package com.cd18.web.config

import com.cd18.common.http.annotation.CurrentUser
import com.cd18.common.http.enums.Auth
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .components(
                Components().addSecuritySchemes(
                    "X-USER-ID",
                    SecurityScheme()
                        .name("X-USER-ID")
                        .type(SecurityScheme.Type.APIKEY)
                        .`in`(SecurityScheme.In.HEADER),
                ),
            )
            .info(configurationInfo())
    }

    @Bean
    fun operationCustomizer(): OperationCustomizer {
        return OperationCustomizer { operation, handlerMethod ->
            val currentUserParams =
                handlerMethod.methodParameters
                    .filter { it.hasParameterAnnotation(CurrentUser::class.java) }
                    .mapNotNull { it.parameter.name }

            if (currentUserParams.isNotEmpty()) {
                // add SecurityRequirement
                operation.addSecurityItem(SecurityRequirement().addList(Auth.X_USER_ID))

                // hide currentUser parameter in Swagger UI (only if parameters exist)
                operation.parameters = operation.parameters?.filterNot { param ->
                    currentUserParams.contains(param.name)
                } ?: emptyList()
            }
            operation
        }
    }

    private fun configurationInfo(): Info {
        return Info()
            .title("API Documentation")
            .version("1.0.0")
    }
}
