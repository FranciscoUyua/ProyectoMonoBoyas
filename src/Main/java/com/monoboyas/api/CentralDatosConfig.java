package Main.java.com.monoboyas.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Central.CentralDatos;

@Configuration
public class CentralDatosConfig {

    @Bean
    public CentralDatos centralDatos() {
        return new CentralDatos();
    }
}