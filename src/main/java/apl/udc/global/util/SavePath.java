package apl.udc.global.util;


import lombok.Getter;

@Getter
public enum SavePath {
    ENCRYPTED("/app/data/encrypted_data.aes"),
    DECRYPTED("/app/data/decrypted_data.csv");

    private final String path;

    SavePath(String path) {
        this.path = path;
    }
}
