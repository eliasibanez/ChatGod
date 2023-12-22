package test.elias.ChatGod.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "ChatGod API",
                version = "1.0",
                description = "API Documentation for ChatGod App"
        ),
        servers = @Server(url = "http://localhost:8080") // Change this to match your server's URL and port
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("chatgod-public")
                .pathsToMatch("/api/**")
                .build();
    }
}
