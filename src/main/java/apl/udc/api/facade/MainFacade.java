package apl.udc.api.facade;

import apl.udc.api.dto.AttributeResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainFacade {

    private final Gson gson = new Gson();
    private final OkHttpClient client = new OkHttpClient();

    public AttributeResponse getAttributes(String authorization) throws IOException {

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/v1")
                .header("Authorization", authorization)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JsonObject jsonObject = JsonParser.parseString(response.body().string()).getAsJsonObject();
                return AttributeResponse.of(
                        jsonObject.getAsJsonObject("data").get("attributes").getAsJsonArray().asList()
                                .stream().map(JsonElement::getAsString).toList()
                );
            } else {
                log.info("Request Failed.");
                return null;
            }
        }
    }
}
