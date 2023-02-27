package exercise;

import java.time.LocalDateTime;

import exercise.daytimes.Daytime;
import exercise.daytimes.Morning;
import exercise.daytimes.Day;
import exercise.daytimes.Evening;
import exercise.daytimes.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// BEGIN
@Configuration
public class MyApplicationConfig {
    @Bean
    public Daytime getDateTime() {
        int currentHour = LocalDateTime.now().getHour();

        boolean isMorning = currentHour >= 6 && currentHour < 12;
        boolean isDay = currentHour >= 12 && currentHour < 18;
        boolean isEvening = currentHour >= 18 && currentHour < 23;

        if (isMorning) {
            return new Morning();
        }

        if (isDay) {
            return new Day();
        }

        if (isEvening) {
            return new Evening();
        }

        return new Night();
    }
}
// END
