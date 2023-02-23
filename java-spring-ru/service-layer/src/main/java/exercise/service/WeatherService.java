package exercise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.HttpClient;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import exercise.CityNotFoundException;
import exercise.repository.CityRepository;
import exercise.model.City;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class WeatherService {

    @Autowired
    CityRepository cityRepository;

    // Клиент
    HttpClient client;

    // При создании класса сервиса клиент передаётся снаружи
    // В теории это позволит заменить клиент без изменения самого сервиса
    WeatherService(HttpClient client) {
        this.client = client;
    }

    // BEGIN
    public Map<String, Object> getWeather(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City not found"));

        String response = client.get("http://weather/api/v2/cities/" + city.getName());

        try {
            return new ObjectMapper().readValue(response, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    // END
}
