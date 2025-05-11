//package mn.edu.userapi.config;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.servers.Server;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class OpenAPIConfig {
//    @Bean
//    public OpenAPI customOpenAPI() {
//        Server localServer = new Server()
//                .url("http://54.251.178.246:8081")
//                .description("Development Server");
//
//        Contact contact = new Contact()
//                .name("API Support")
//                .email("support@example.com")
//                .url("https://www.example.com/support");
//
//        License license = new License()
//                .name("Apache 2.0")
//                .url("https://www.apache.org/licenses/LICENSE-2.0");
//
//        return new OpenAPI()
//                .info(new Info()
//                        .title("Миний API")
//                        .version("1.0")
//                        .description("API-н тайлбар")
//                        .contact(contact)
//                        .license(license))
//                .servers(List.of(localServer));
//    }
//}