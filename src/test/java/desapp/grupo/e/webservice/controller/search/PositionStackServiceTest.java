package desapp.grupo.e.webservice.controller.search;

import desapp.grupo.e.model.dto.search.AddressDTO;
import desapp.grupo.e.model.dto.search.PositionStack;
import desapp.grupo.e.service.search.PositionStackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositionStackServiceTest {

    private RestTemplate restTemplate;
    private PositionStackService positionStackService;

    @BeforeEach
    public void setUp() {
        this.restTemplate = mock(RestTemplate.class);
        this.positionStackService = new PositionStackService(restTemplate);
    }

    @Test
    public void findPositionStackWithoutConnectionToApiShouldReturnAPositionStackWithLat0Lng0() {
        when(restTemplate.exchange(anyString(), any(), any(), (ParameterizedTypeReference<List<PositionStack>>) any()))
                .thenThrow(new RestClientException("Error connection"));

        PositionStack position = this.positionStackService.findPositionByAddress(new AddressDTO());
        Assertions.assertEquals(0.0, position.getLatitude());
        Assertions.assertEquals(0.0, position.getLongitude());
    }

    @Test
    public void findPositionStackAndApiReturnEmptyResultShouldReturnAPositionStackWithLat0Lng0() {
        when(restTemplate.exchange(anyString(), any(), any(), (ParameterizedTypeReference<List<PositionStack>>) any()))
                .thenReturn(new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));

        PositionStack position = this.positionStackService.findPositionByAddress(new AddressDTO());
        Assertions.assertEquals(0.0, position.getLatitude());
        Assertions.assertEquals(0.0, position.getLongitude());
    }

    @Test
    public void findPositionStackAndApiFindItShouldReturnAListWithOnePositionStack() {
        PositionStack positionResponse = new PositionStack();
        positionResponse.setLatitude(10.0);
        positionResponse.setLongitude(-10.0);
        when(restTemplate.exchange(anyString(), any(), any(), (ParameterizedTypeReference<List<PositionStack>>) any()))
                .thenReturn(new ResponseEntity<>(Arrays.asList(positionResponse), HttpStatus.OK));

        PositionStack position = this.positionStackService.findPositionByAddress(new AddressDTO());
        Assertions.assertEquals(10.0, position.getLatitude());
        Assertions.assertEquals(-10.0, position.getLongitude());
    }
}
