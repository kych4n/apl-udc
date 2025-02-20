package apl.udc.api.controller;

import apl.udc.auth.AuthProperties;
import apl.udc.auth.OtpHandler;
import apl.udc.dto.request.SignInRequest;
import apl.udc.dto.response.SignInResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthProperties authProperties;
    private final OtpHandler otpHandler;
    private final Gson gson = new Gson();
    private final OkHttpClient client = new OkHttpClient();

    public SignInResponse signIn() throws IOException {
        int totp = otpHandler.getTotp(authProperties.secretKey());
        SignInRequest signInRequest = SignInRequest.builder()
                .username(authProperties.username()).password(authProperties.password()).totp(totp)
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(signInRequest),
                MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/v1/auth/signin")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JsonObject jsonObject = JsonParser.parseString(response.body().string()).getAsJsonObject();
                return SignInResponse.of(
                        jsonObject.getAsJsonObject("data").get("accessToken").getAsString(),
                        jsonObject.getAsJsonObject("data").get("refreshToken").getAsString()
                );
            } else {
                log.info("Request Failed.");
                return null;
            }
        }
    }
}
