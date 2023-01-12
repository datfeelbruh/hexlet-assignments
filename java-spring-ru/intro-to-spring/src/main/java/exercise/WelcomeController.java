package exercise;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
public class WelcomeController {
    @GetMapping(path = "/")
    public String welcome() {
        return "Welcome to Spring";
    }

    @GetMapping(path = "/hello")
    public String hello(@RequestParam(defaultValue = "World") String name) {
        return "Hello " + name;
    }
}
// END
