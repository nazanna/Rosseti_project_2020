import java.util.HashMap;
import java.util.Map;

public class GenerationInfo {
    private Map<String, Double> MapWithMinPrice = new HashMap<>();
    private Map<String, Double> MapWithMaxNakopSize = new HashMap<>();
    private Map<String, Double> MapWithCurrentNakop = new HashMap<>();
    private Map<String, Double> MapWithActualPower = new HashMap<>();
    private Map<String, Integer> MapWithLastAskTime = new HashMap<>();
    private Map<String, Double> MapWithReservePower = new HashMap<>();
    private int StartTime;


    public GenerationInfo(int startTime) {
        StartTime = startTime;
    }

    public void setAll() {

        String agent = "Sun";
        MapWithMinPrice.put(agent, 3.0);
        MapWithMaxNakopSize.put(agent, 32.0);
        MapWithCurrentNakop.put(agent, 2.0);
        MapWithLastAskTime.put(agent, StartTime);
        MapWithActualPower.put(agent, 0.0);
        MapWithReservePower.put(agent, 0.0);

        agent = "Wind";
        MapWithMinPrice.put(agent, 3.2);
        MapWithMaxNakopSize.put(agent, 36.0);
        MapWithCurrentNakop.put(agent, 5.0);
        MapWithLastAskTime.put(agent, StartTime);
        MapWithActualPower.put(agent, 0.0);
        MapWithReservePower.put(agent, 0.0);

        agent = "Heat";
        MapWithMinPrice.put(agent, 4.3);
        MapWithMaxNakopSize.put(agent, new GenHour().PowHour(agent,2));
        MapWithCurrentNakop.put(agent, new GenHour().PowHour(agent,2));
        MapWithLastAskTime.put(agent, StartTime);
        MapWithActualPower.put(agent, 0.0);
        MapWithReservePower.put(agent, 0.0);

        agent = "System";
        MapWithMinPrice.put(agent, 4.7);
        MapWithMaxNakopSize.put(agent, 300.0);
        MapWithCurrentNakop.put(agent, 300.0);
        MapWithLastAskTime.put(agent, StartTime);
        MapWithActualPower.put(agent, 0.0);
        MapWithReservePower.put(agent, 0.0);
    }

//

    public String FormPrice(int time, String agent, double request) {//лажа (переползать в минуты)  комментарии о содержании

        double power = 0;

        //если был переход через сутки, то к времени добавить время прошедших суток
        while (time - MapWithLastAskTime.get(agent) < 0) {
            time += 24 * 60;
        }

        //если был переход через час, то обновить актуальную мощность на данный час и мощность в накопителе "System" и "Heat"
        if ((time / 60 - MapWithLastAskTime.get(agent) / 60) >= 1) {
            MapWithActualPower.replace(agent, new GenHour().PowHour(agent, (time / 60) % (24)) / 60);
            if ((agent.equals("System") || agent.equals("Heat"))) {
                MapWithCurrentNakop.replace(agent, 0.0);
                power = MapWithActualPower.get(agent) * 60;

            }
        }
        //добавить накопленную мощность за время от прошлого запроса
        if (!(agent.equals("System") || agent.equals("Heat"))) {

            power = MapWithActualPower.get(agent) * (time - MapWithLastAskTime.get(agent));

        }
        MapWithCurrentNakop.replace(agent, MapWithCurrentNakop.get(agent) + power);

        //если накопленное больше максимальной емкости накопителя
        if (MapWithCurrentNakop.get(agent) > MapWithMaxNakopSize.get(agent))
            MapWithCurrentNakop.replace(agent, MapWithMaxNakopSize.get(agent));

        //изменить время последнего запроса
//        System.out.println(time+"  "+ MapWithLastAskTime.get(agent)+"  "+agent+"  "+MapWithReservePower.get(agent)+"  "
//                +MapWithActualPower.get(agent)*60);
        MapWithLastAskTime.replace(agent, time);
        double currentPower;
//        System.out.println(MapWithLastAskTime.get(agent)+"  "+MapWithCurrentNakop.get(agent)+"  "+agent);

            currentPower = (MapWithCurrentNakop.get(agent));
//            - MapWithReservePower.get(agent));

        //если доступная мощность больше требуемого запроса, то вернуть цену, иначе "Left"
        if (request <= (currentPower)) {
            if ((agent.equals("System") || agent.equals("Heat"))) {
                currentPower = MapWithMaxNakopSize.get(agent);
            }
            return String.valueOf(MapWithMinPrice.get(agent) * MapWithMaxNakopSize.get(agent) / currentPower);
//            * request);
        } else {
            return "Left";
        }
    }

    public double allPower(String agent) {
        return MapWithCurrentNakop.get(agent);
    }

    public void reservePower(double elem, String agent) {
        MapWithReservePower.replace(agent, MapWithReservePower.get(agent) + elem);
    }

    public void minPower(double elem, String agent) {
        MapWithCurrentNakop.replace(agent, MapWithCurrentNakop.get(agent) - elem);
    }


}