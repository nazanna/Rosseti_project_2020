import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
//класс для работы с config
public class ConfigAtr {
    @XmlAttribute
    private int time;
    @XmlAttribute
    private double load;



    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public ConfigAtr(int time, double load) {
        this.time = time;
        this.load = load;
    }
    public ConfigAtr() {

    }
}
