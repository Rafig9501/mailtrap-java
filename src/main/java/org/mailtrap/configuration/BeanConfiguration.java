package org.mailtrap.configuration;

import java.util.List;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPI31
@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info().title("Mailtrap Service").version("1.0.0").description("Mailtrap email sending application");
        return new OpenAPI().info(info);
    }
}