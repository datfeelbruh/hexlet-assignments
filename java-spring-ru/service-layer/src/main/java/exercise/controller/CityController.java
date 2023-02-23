package exercise.controller;
import exercise.CityNotFoundException;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherService weatherService;

    // BEGIN
    @GetMapping("/cities/{id}")
    public Map<String, String> getCityWeather(@PathVariable Long id) {
        return weatherService.getWeather(id);
    }

    @GetMapping("/search")
    public List<Map<String, String>> getListCitiesAndTemperatures(
            @RequestParam(name = "name", required = false) String prefix)
    {
       List<City> cities = prefix == null ?
               cityRepository.findAllByOrderByNameAsc() :
               cityRepository.findByNameIgnoreCaseStartingWith(prefix);

       return cities.stream()
               .map(city -> {
                   Map<String, String> weather = weatherService.getWeather(city.getId());
                   Map<String, String> citiesAndTemperatures = new HashMap<>();
                   citiesAndTemperatures.put("temperature", weather.get("temperature"));
                   citiesAndTemperatures.put("name", weather.get("name"));
                   return citiesAndTemperatures;
               })
               .sorted(Comparator.comparing(e -> e.get("name")))
               .collect(Collectors.toList());
    }
    // END
}

