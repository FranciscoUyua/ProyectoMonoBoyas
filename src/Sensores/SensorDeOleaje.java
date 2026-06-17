package Sensores.Providers;

import Sensores.ISensorDataProvider;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiOleajeProvider implements ISensorDataProvider {
    private final String URL = "https://api.open-meteo.com/v1/marine?latitude=-38.71&longitude=-62.28&current=wave_height";

    @Override
    public double obtenerDato() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.body());
        return rootNode.path("current").path("wave_height").asDouble();
    }
}