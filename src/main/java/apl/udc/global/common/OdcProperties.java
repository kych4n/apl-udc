package apl.udc.global.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "odc")
public record OdcProperties(
        String address
) {
}
