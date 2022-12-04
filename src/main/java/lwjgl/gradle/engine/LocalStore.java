package lwjgl.gradle.engine;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class LocalStore {
    static JSONObject data = new JSONObject();
    static JSONParser parser = new JSONParser();
    static FileWriter file;

    public static JSONObject getObject() {
        return data;
    }

    public static void save() {
        try {
            file = new FileWriter("./store.json");
            file.write(getObject().toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (file == null) return;
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void load() {
        try {
            data = (JSONObject) parser.parse(new FileReader("./store.json"));
        } catch (FileNotFoundException e) {
            init();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            init();
            e.printStackTrace();
        }
    }

    private static void init() {
        data = new JSONObject();
        JSONArray players = new JSONArray();
        getObject().put("players", players);

        save();
        load();
    }

    public static JSONObject createPlayer(String name) {
        JSONObject playerExisting = pullPlayer(name);
        if (playerExisting != null) {
            updatePlayerScore(name, 0);

            return playerExisting;
        }

        JSONObject player = new JSONObject();
        player.put("name", name);
        player.put("score", 0);

        JSONArray players = (JSONArray) getObject().get("players");
        players.add(player);

        save();

        return player;
    }

    public static void updatePlayerScore(String name, int score) {
        JSONObject player = pullPlayer(name);
        player.put("score", score);
        pushPlayer(player);

        save();
    }

    public static void updatePlayerSkin(String name, int skin) {
        JSONObject player = pullPlayer(name);
        player.put("skin", skin);
        pushPlayer(player);

        save();
    }

    private static JSONObject pullPlayer(String name) {
        AtomicReference<JSONObject> result = new AtomicReference<>();
        JSONArray players = (JSONArray) getObject().get("players");

        players.forEach((player) -> {
            String playerName = (String) ((JSONObject) player).get("name");
            if (Objects.equals(playerName, name)) {
                result.set((JSONObject) player);
            }
        });

        return result.get();
    }

    private static void pushPlayer(JSONObject player) {
        JSONArray players = (JSONArray) getObject().get("players");
        JSONObject playerExisting = pullPlayer((String) player.get("name"));
        if (playerExisting != null) {
            players.remove(playerExisting);
        }

        players.add(player);
    }

    public static JSONObject getCurrentPlayer() {
        String playerName = (String) getObject().get("currentPlayerName");
        if (playerName == null) {
            return null;
        }

        return pullPlayer(playerName);
    }

    public static void setCurrentPlayer(JSONObject player) {
        JSONObject playerCheck = pullPlayer((String) player.get("name"));
        if (playerCheck == null) {
            return;
        }

        getObject().put("currentPlayerName", player.get("name"));

        save();
    }

    public static JSONArray getPlayers() {
        return (JSONArray) getObject().get("players");
    }

    public static JSONObject getFirstPlayer() {
        return (JSONObject) getPlayers().get(0);
    }
}
