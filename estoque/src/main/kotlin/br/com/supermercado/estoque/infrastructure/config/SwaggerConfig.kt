package br.com.supermercado.estoque.infrastructure.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Estoque API")
                    .description("API para gerenciamento de estoque do supermercado")
                    .version("1.0.0")
                    .contact(
                        Contact()
                            .name("Equipe de Desenvolvimento")
                            .email("dev@supermercado.com.br")
                    )
            )
    }
}