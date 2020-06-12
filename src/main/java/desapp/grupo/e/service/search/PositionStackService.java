package desapp.grupo.e.service.search;

import desapp.grupo.e.model.dto.search.AddressDTO;
import desapp.grupo.e.model.dto.search.PositionStack;
import desapp.grupo.e.service.log.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class PositionStackService {

    private static final String URL_API = "http://api.positionstack.com/v1/forward";
    private RestTemplate restTemplate;

    public PositionStackService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<PositionStack> findPositionByAddress(AddressDTO address) {
        String fullAddress = String.format("%s %s, %s", address.getAddress(), address.getAddressNumber(), address.getLocation());
        String query = "https://example.com?q=" + URLEncoder.encode(fullAddress, StandardCharsets.UTF_8);
        String url = URL_API + "?access_key=" + "key" + "&query="+query+"&limit=1";
        try {
            HttpEntity<Object> httpEntity = new HttpEntity<>(null);
            ResponseEntity<List<PositionStack>> response = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>(){});
            if(!response.getBody().isEmpty()) {
                return Optional.of(response.getBody().get(0));
            }
        } catch (Exception e) {
            Log.info("Error to connect to Position Stack - " + e.getMessage());
        }
        return Optional.empty();
    }
}
