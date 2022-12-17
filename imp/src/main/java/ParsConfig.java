import java.util.List;

public class ParsConfig {
//класс для работы с config

    public double pow(int time,String name) {
        Config config = WorkWithCfgs.unMarshalAny(Config.class,name);
        List<ConfigAtr> conf= config.getPower();
        return conf.get(time).getLoad();

    }
    public double maxPower(String name) {
        Config config = WorkWithCfgs.unMarshalAny(Config.class,name);

        return Double.parseDouble(config.getMaxPower());

    }
}
