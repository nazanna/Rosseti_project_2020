import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DistribBehSecond extends Behaviour {

    private AID topic;
    private AID[] resultsAID;
    private String consumer;
    private double request;
    private boolean flag = false;
    private JsonDistr json;
    private Time time;
    private String ontology;
    private int count = 0;

    public DistribBehSecond(AID topic, AID[] resultsAID, String consumer, double request, JsonDistr json, Time time, String ontology) {
        this.topic = topic;
        this.resultsAID = resultsAID;
        this.consumer = consumer;
        this.request = request;
        this.json = json;
        this.time = time;
        this.ontology = ontology;
    }




    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Ready"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);

        if (receivedMsg != null) {
            count++;
        }
        //ожидание сообщений о подтверждении от всех агентов
        if (count == resultsAID.length) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(topic);
            message.setProtocol("Power");
            message.setContent(String.valueOf(request));
            message.setOntology(ontology);
            myAgent.send(message);
            myAgent.addBehaviour(new DistribBehThird(topic, resultsAID, consumer, request, json, time));
            flag = true;
        }
    }

    @Override
    public boolean done() {
        return flag;
    }

}