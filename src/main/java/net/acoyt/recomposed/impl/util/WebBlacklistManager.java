package net.acoyt.recomposed.impl.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.acoyt.acornlib.impl.AcornLib;
import net.acoyt.acornlib.impl.util.supporter.PlayerInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author AcoYT
 */
public class WebBlacklistManager {
    public static final List<UUID> MODERATORS = Arrays.asList(
            UUID.fromString("6035af5b-a74c-4838-8fab-e4c7a76e42d6"), // 4kio
            UUID.fromString("017f5cdc-086b-4d98-a0c2-7dc43d5117bd"), // AcoYT
            UUID.fromString("dd129c8b-d3c6-4553-92fe-8ba2f0d021c6"), // Mythorical
            UUID.fromString("a2625733-af02-44be-a33a-8ae793d60613") // LOOPINGsss
    );

    private long lastFetchTime = 0;
    private List<PlayerInfo> cachedValue = new ArrayList<>();

    public List<PlayerInfo> fetchWhitelisted() {
        return fetchWhitelisted(false);
    }

    public List<PlayerInfo> fetchWhitelisted(boolean forced) {
        long now = System.currentTimeMillis();
        long CACHE_DURATION = 5 * 60 * 1000;

        if (now - lastFetchTime < CACHE_DURATION && !forced) {
            return cachedValue;
        }

        List<PlayerInfo> whitelisted = new ArrayList<>();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://raw.githubusercontent.com/AcoYTMC/Data/main/whitelist.json").openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

                if (jsonObject.has("players") && jsonObject.get("players").isJsonArray()) {
                    JsonArray supporterArray = jsonObject.getAsJsonArray("supporters");
                    for (var element : supporterArray) {
                        JsonObject playerObj = element.getAsJsonObject();
                        String uuid = playerObj.get("uuid").getAsString();
                        String username = playerObj.get("username").getAsString();
                        whitelisted.add(new PlayerInfo(uuid, username));
                    }

                    cachedValue = whitelisted;
                    lastFetchTime = now;
                } else {
                    AcornLib.LOGGER.error("Error: one of the following fields are missing, or are not an array: 'supporters' 'friends' 'blacklisted'");
                }
                reader.close();
            } else {
                AcornLib.LOGGER.error("HTTP Error: {}", connection.getResponseCode());
            }
            connection.disconnect();
        } catch (IOException e) {
            AcornLib.LOGGER.error(e.getMessage());
        }

        return cachedValue;
    }

    public boolean isWhitelisted(UUID uuid) {
        return fetchWhitelisted(true).stream().anyMatch(info -> info.uuid().equals(uuid.toString())) || MODERATORS.contains(uuid);
    }
}
