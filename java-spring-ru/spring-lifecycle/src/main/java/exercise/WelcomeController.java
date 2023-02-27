package exercise;

import exercise.daytimes.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
public class WelcomeController {
    @Autowired
    Meal meal;

    @Autowired
    Daytime daytime;

    @GetMapping
    public String getDaytime() {
        String time = daytime.getName();
        return "It is " + time + " now. Enjoy your " + meal.getMealForDaytime(time);
    }
}
// END
