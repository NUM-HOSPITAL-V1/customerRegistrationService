package mn.edu.userapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Server localServer = new Server()
                .url("http://54.251.178.246:8080")
                .description("Development Server");
        Server localServer1 = new Server()
                .url("http://localhost:8080")
                .description("Localtest server");

        Contact contact = new Contact()
                .name("API Support")
                .email("support@example.com")
                .url("https://www.example.com/support");

        License license = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0");

        // Security Scheme тодорхойлох
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        // OpenAPI буцаах
        return new OpenAPI()
                .info(new Info()
                        .title("Миний API")
                        .version("1.0")
                        .description("API-н тайлбар")
                        .contact(contact)
                        .license(license))
                .addServersItem(localServer)
                .addServersItem(localServer1)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth", bearerAuth));
    }
}