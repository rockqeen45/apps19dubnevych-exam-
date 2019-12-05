package json;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class JsonObject extends Json {
    List<JsonPair> jsonPairs;

    public JsonObject(JsonPair... jsonPairs) {
        this.jsonPairs = new LinkedList<>();
        this.jsonPairs.addAll(Arrays.asList(jsonPairs));
    }

    @Override
    public String toJson() {
        return "{" + getJsonObjBody() + "}";
    }

    private String getJsonObjBody() {
        String jsonStr = "";
        Iterator<JsonPair> jsonIterator = jsonPairs.iterator();
        while (jsonIterator.hasNext()) {
            JsonPair pair = jsonIterator.next();
            jsonStr += pair.toString();
            if (jsonIterator.hasNext())
                jsonStr += ", ";
        }
        return jsonStr;
    }

    public void add(JsonPair jsonPair) {
        for (int i = 0; i < jsonPairs.size(); i++) {
            JsonPair pair = jsonPairs.get(i);
            if (pair.key.equals(jsonPair.key)) {
                jsonPairs.set(i, new JsonPair(jsonPair.key, jsonPair.value));
                return;
            }
        }
        this.jsonPairs.add(jsonPair);
    }

    public Json find(String name) {
        for (JsonPair pair: jsonPairs) {
            if (pair.key.equals(name)) {
                return pair.value;
            }
        }
        return null;
    }

    public JsonObject projection(String... names) {
        JsonObject json = new JsonObject();
        for (String name: names) {
            Json value = this.find(name);
            if (value != null) {
                json.add(new JsonPair(name, value));
            }
        }
        return json;
    }
}
