package api.moneyflow.commom.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {
    public static final String AUTHORIZATION = "BearerAuth";

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("MoneyFlow API")
                        .version("1.0.0")
                        .description("API for personal expense management")
                        .contact(new Contact()
                                .name("MoneyFlow Support")
                                .email("support@moneyflow.com")
                                .url("https://moneyflow.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList(AUTHORIZATION))
                .components(new Components()
                        .addSecuritySchemes(AUTHORIZATION, new SecurityScheme()
                                .name(AUTHORIZATION)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                )
                .servers(List.of(
                        new Server().url("https://api-moneyflow.up.railway.app").description("Production"),
                        new Server().url("http://localhost:8081").description("Development")
                ));
    }
}
