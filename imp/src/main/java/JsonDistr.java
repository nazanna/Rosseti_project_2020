import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
//класс для работы с json
public class JsonDistr {
    JSONObject Json= new JSONObject();
    JSONArray JsonArray=new JSONArray();

    public  String stroka(Map<String, String> data){
        Json.put("name","dis");
        JsonArray.add(data);
        Json.put("data",JsonArray);
        return Json.toJSONString();

    }
    public static Map<String,String> data(double price, double power, String from, String to, int time){
        Map<String, String> data = new HashMap<>();
        data.put("Price", String.valueOf(price));
        data.put("Power", String.valueOf(power));
        data.put("From",from);
        data.put("To",to);
        data.put("Time", String.valueOf(time));
        return data;
    }
}

