package tw.appworks.school.example.stylish;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import tw.appworks.school.example.stylish.storage.StorageProperties;

@SpringBootApplication
@EnableCaching
//@EnableConfigurationProperties(StorageProperties.class)
@EnableConfigurationProperties(StorageProperties.class)
@OpenAPIDefinition(
        servers = {
                @Server(url = "/", description = "Default Server URL")
        },
        info = @Info(
                title = "Batch#23 Group-3 co-work project",
                version = "1.0.0",
                description = "This api document is for Appworks Batch#23 Group-3 co-working",
                termsOfService = "runcodenow"
        )
)
public class StylishApplication {


    public static void main(String[] args) {
        SpringApplication.run(StylishApplication.class, args);
    }

}