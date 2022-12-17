import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class GenBehThird extends Behaviour {
    private boolean flag = false;//private
    private AID topic;
    private GenerationInfo generationPower;
    private JsonGen jsonGen;
    private Time time;

    public GenBehThird(AID topic, GenerationInfo generationPower, JsonGen jsonGen, Time time) {
        this.topic = topic;
        this.generationPower = generationPower;
        this.jsonGen = jsonGen;
        this.time = time;
    }


    @Override
    public void action() {
        double powerBefore, powerAfter;
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Winner"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);

        if (receivedMsg != null) {
//            System.out.println(receivedMsg);
//            запрос полной мощности без учета зарезервированной до сделки
            powerBefore = generationPower.allPower(myAgent.getLocalName());

            if ((receivedMsg.getContent().equals(myAgent.getLocalName()))) {
                //вычитание проданной мощности при победе
                generationPower.minPower(Double.parseDouble(receivedMsg.getOntology()), myAgent.getLocalName());
//
            }
//            запрос полной мощности без учета зарезервированной после сделки
            powerAfter = generationPower.allPower(myAgent.getLocalName());
            generationPower.reservePower(-Double.parseDouble(receivedMsg.getOntology()), myAgent.getLocalName());
            //создание словаря, содержащего информацию о мощности до сделки, после сделки, о времени для текущего агента и запись его в файл
            Map<String, String> data = jsonGen.dataGen(powerBefore, powerAfter, time.getCurrentTime() / 60 % 24,time.day);
            String stroka = jsonGen.stroka(data, myAgent.getLocalName());
            String fileName = String.format("C:\\Users\\anna\\IdeaProjects\\imp\\%s.json", myAgent.getLocalName());
            try {
                FileOutputStream file = new FileOutputStream(fileName);
                file.write(stroka.getBytes());
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            myAgent.addBehaviour(new GenBehForth(topic,Double.parseDouble(receivedMsg.getOntology()),time));
            flag = true;
        }
    }

    @Override
    public boolean done() {
        return flag;
    }
}
