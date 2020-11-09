package ro.ubb.catalog.core;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration
@ComponentScan(value = "ro.ubb.catalog.core")
        //excludeFilters = {@ComponentScan.Filter(value = {JPAConfig.class}, type = FilterType.ASSIGNABLE_TYPE)})
@Import({JPAConfigIT.class})
@PropertySources({@PropertySource("classpath:db-h2.properties")})
public class ITConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
