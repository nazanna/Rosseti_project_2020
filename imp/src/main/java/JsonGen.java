import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
//класс для работы с json
public class JsonGen {
    JSONObject Json = new JSONObject();
    JSONArray JsonArray = new JSONArray();

    public String stroka(Map<String, String> data, String name) {
        Json.put("name", name);
        if (!JsonArray.contains(data)) {
            JsonArray.add(data);
            System.out.println(data+" "+name);
        }

        Json.put("data", JsonArray);
        return Json.toJSONString();

    }

    public Map<String, String> dataGen(double powerBefore, double powerAfter, int time,int day) {
        Map<String, String> data = new HashMap<>();
        data.put("Power Before", String.valueOf(powerBefore));
        data.put("Power After", String.valueOf(powerAfter));
        data.put("Time", String.valueOf(time));
        data.put("Day", String.valueOf(day));
        return data;
    }
}

