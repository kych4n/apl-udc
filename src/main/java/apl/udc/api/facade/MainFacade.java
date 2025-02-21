package apl.udc.api.facade;

import apl.udc.api.dto.request.InfoForFilteringRequest;
import apl.udc.api.dto.response.AttributeResponse;
import apl.udc.auth.AuthProperties;
import apl.udc.global.common.OdcProperties;
import apl.udc.global.util.Decryptor;
import apl.udc.global.util.FileDownloader;
import apl.udc.global.util.SavePath;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import javax.crypto.SecretKey;
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
public class MainFacade {

    private final Gson gson = new Gson();
    private final OkHttpClient client = OkHttpClient();
    private final FileDownloader fileDownloader;
    private final AuthProperties authProperties;
    private final OdcProperties odcProperties;
    private final Decryptor decryptor;

    public AttributeResponse getAttributes(String authorization) throws IOException {

        Request request = new Request.Builder()
                .url(odcProperties.address() + "/api/v1")
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

    public String getEncryptedDataUrl(String authorization, InfoForFilteringRequest infoForFilteringRequest)
            throws IOException {

        RequestBody requestBody = RequestBody.create(gson.toJson(infoForFilteringRequest),
                MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .header("Authorization", authorization)
                .url(odcProperties.address() + "/api/v1")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JsonObject jsonObject = JsonParser.parseString(response.body().string()).getAsJsonObject();
                return jsonObject.getAsJsonObject("data").get("signedUrl").getAsString();
            } else {
                log.info("Request Failed.");
                return null;
            }
        }
    }

    public void download(String encryptedDataUrl) throws IOException {
        fileDownloader.downloadFile(encryptedDataUrl, SavePath.ENCRYPTED.getPath());
    }

    public void decrypt() throws Exception {
        SecretKey secretKey = decryptor.generateKey(authProperties.seed());
        decryptor.decrypt(secretKey);
    }

    public void done(String authorization) throws IOException {
        Files.deleteIfExists(Path.of(SavePath.ENCRYPTED.getPath()));
        Files.deleteIfExists(Path.of(SavePath.DECRYPTED.getPath()));

        Request request = new Request.Builder()
                .header("Authorization", authorization)
                .url(odcProperties.address() + "/api/v1")
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.info("Request Failed.");
            }
        }
    }

    private OkHttpClient OkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMinutes(5L))
                .callTimeout(Duration.ofMinutes(5L))
                .readTimeout(Duration.ofMinutes(5L))
                .writeTimeout(Duration.ofMinutes(5L))
                .build();
    }

}
