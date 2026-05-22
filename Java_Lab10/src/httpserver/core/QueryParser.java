package httpserver.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class QueryParser {
    private QueryParser() {
    }

    public static Map<String, String> parse(String query) {
        Map<String, String> params = new LinkedHashMap<>();
        if (query == null || query.isEmpty()) {
            return params;
        }

        String[] pairs = query.split("&", -1);
        for (String pair : pairs) {
            if (pair.isEmpty()) {
                continue;
            }

            int separatorIndex = pair.indexOf('=');
            String rawKey = separatorIndex >= 0 ? pair.substring(0, separatorIndex) : pair;
            String rawValue = separatorIndex >= 0 ? pair.substring(separatorIndex + 1) : "";
            String key = decode(rawKey);
            String value = decode(rawValue);

            if (!key.isEmpty()) {
                params.put(key, value);
            }
        }

        return params;
    }

    public static String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 is not supported", e);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid URL encoding");
        }
    }
}
