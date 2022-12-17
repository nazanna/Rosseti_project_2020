import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "agentConfig")
@XmlAccessorType(XmlAccessType.FIELD)
//класс для работы с config
public class Config {
    @XmlElement
    private String agentName;
    @XmlElement
    private String maxPower;
    @XmlElementWrapper(name="Power")
    @XmlElement(name="power")
    private List<ConfigAtr> Power=new ArrayList<>();

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public List<ConfigAtr> getPower() {
        return Power;
    }

    public void setPower(List<ConfigAtr> power) {
        Power = power;
    }

    public String getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(String maxPower) {
        this.maxPower = maxPower;
    }
}
