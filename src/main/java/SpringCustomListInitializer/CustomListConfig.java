package SpringCustomListInitializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
@Configuration
public class CustomListConfig {
    @Lazy
    @Bean
    public static CustomListInitBPP myAnnotationBPP() {
        return new CustomListInitBPP();
    }
}
