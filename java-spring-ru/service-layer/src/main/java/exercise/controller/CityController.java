package exercise.controller;
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
    public Map<String, Object> getCityWeather(@PathVariable Long id) {
        return weatherService.getWeather(id);
    }

    @GetMapping("/search")
    public List<Map<String, Object>> getListCitiesAndTemperatures(
            @RequestParam(name = "name", required = false) String prefix)
    {
       List<City> cities = prefix == null ?
               cityRepository.findAllByOrderByNameAsc() :
               cityRepository.findByNameIgnoreCaseStartingWith(prefix);

       return cities.stream()
               .map(city -> {
                   Map<String, Object> weather = weatherService.getWeather(city.getId());
                   return Map.of(
                           "name", city.getName(),
                           "temperature", weather.get("temperature")
                   );
               })
               .collect(Collectors.toList());
    }
    // END
}

