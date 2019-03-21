package scanner.annotation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scanner.annotation.domain.CycleEntity;

@Configuration
public class SampleConfig {
    @Bean
    public Integer numOne() {
        return 0;
    }

    @Bean
    public CycleEntity cycleEntity() {
        return new CycleEntity("循环自身依赖");
    }
}
