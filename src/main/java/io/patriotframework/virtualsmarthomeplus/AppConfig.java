package io.patriotframework.virtualsmarthomeplus;

import io.patriotframework.virtualsmarthomeplus.house.House;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class AppConfig {

    @Bean
    public House houseProducer() {
        return new House();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
