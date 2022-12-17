
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
//класс для работы с json
public class JsonCons {
    JSONObject Json= new JSONObject();
    JSONArray JsonArray=new JSONArray();

    public  String stroka(Map<String, String> data,String name){
        Json.put("name",name);
        JsonArray.add(data);
        Json.put("data",JsonArray);
        return Json.toJSONString();

    }
    public static Map<String,String> data(double price, double power, String verdict, String name, int time){
        Map<String, String> data = new HashMap<>();
        data.put("Price", String.valueOf(price));
        data.put("Power", String.valueOf(power));
        data.put("Verdict",verdict);

        data.put("Time", String.valueOf(time));
        return data;
    }
}

