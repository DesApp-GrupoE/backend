package service.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.dto.ApiError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonUtilsTest {

    @Test
    public void testConvertToJson() throws JsonProcessingException {
        ApiError error = new ApiError("Test convert to json");

        String json = JsonUtils.toJson(error);

        Assertions.assertEquals(json, "{\n  \"error\" : \"Test convert to json\"\n}");
    }

    @Test
    public void testFromJson() throws JsonProcessingException {
        ApiError apiError = (ApiError) JsonUtils.fromJson(ApiError.class, "{\n  \"error\" : \"Test convert from json\"\n}");

        Assertions.assertEquals(apiError.getError(), "Test convert from json");
    }

    @Test
    public void testJsonProcessingException() {
        Assertions.assertThrows(JsonProcessingException.class, () -> JsonUtils.fromJson(ApiError.class, "{\n  \"inconsistentJson\"\n}"));
    }
}
