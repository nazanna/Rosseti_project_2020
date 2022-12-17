import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GenBehForth extends Behaviour {

    private AID topic;
    private double request;
    private boolean flag = false;
//    private JsonDistr json;
    private Time time;
    private int count = 0;

    public GenBehForth(AID topic,  double request,  Time time) {
        this.topic = topic;
        this.request = request;
//        this.json = json;
        this.time = time;
//        this.ontology = ontology;
    }


    @Override
    public void action() {
        MessageTemplate mt = (MessageTemplate.MatchProtocol("Test"));
        ACLMessage receivedMsg = myAgent.receive(mt);
        if (receivedMsg != null) {

            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(topic);
            message.setProtocol("Control");
            message.setContent("OK");
            myAgent.send(message);
            flag = true;
        }
    }

    @Override
    public boolean done() {
        return flag;
    }

}