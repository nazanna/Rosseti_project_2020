import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
public class WorkWithCfgs {
//класс для работы с config
    public static <T> T unMarshalAny(Class<T> clazz, String outPutFileName) {
        T object = null;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object obj = jaxbUnmarshaller.unmarshal(new File(outPutFileName));
            object = (T) obj;
        } catch (JAXBException | ClassCastException e) {
            e.printStackTrace();
        }
        return object;
    }


}
