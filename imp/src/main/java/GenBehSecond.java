import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GenBehSecond extends Behaviour {
    private AID topic;
    private GenerationInfo generationPower;
    private Time time;
    private boolean flag = false;
    private  JsonGen jsonGen;

    public GenBehSecond(Time time, AID topic, GenerationInfo generationPower, JsonGen jsonGen) {//убрать агента
        this.time = time;
        this.topic = topic;
        this.generationPower = generationPower;
        this.jsonGen = jsonGen;
    }



    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Power"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);

        if ((receivedMsg != null)) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(topic);
            message.setProtocol("Price");
            double request=Double.parseDouble(receivedMsg.getContent());

//          переменная для проверки соответствия онтологии и имени агента (при онтологии "System" действовать должен только агент "System",
//          агент "System" должен действовать только при онтологии "System"
            boolean smth=(((!receivedMsg.getOntology().equals("System")) && (myAgent.getLocalName().equals("System"))) ||
                    ((receivedMsg.getOntology().equals("System")) && (!myAgent.getLocalName().equals("System"))));
            if (smth) {
                message.setContent("Left");

            } else {
                String price= generationPower.FormPrice(time.getCurrentTime(), myAgent.getLocalName(), request);
                message.setContent(price);
            }
            if (message.getContent().equals("Left")) {
                System.out.println(myAgent.getLocalName() + " в " + topic.getLocalName() + " " + message.getContent());
            }
            else {
                System.out.println(myAgent.getLocalName() + " в " + topic.getLocalName() + " за " + message.getContent());
            }
//            резервирование запрошенной мощности
            generationPower.reservePower(request, myAgent.getLocalName());
            myAgent.send(message);
            myAgent.addBehaviour(new GenBehThird(topic, generationPower, jsonGen, time));
            flag = true;
        }
    }

    @Override
    public boolean done() {
        return flag;
    }

}
