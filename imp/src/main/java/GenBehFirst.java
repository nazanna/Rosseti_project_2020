import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GenBehFirst extends Behaviour {
    private boolean flag = false;
    private AID topic;
    private Time time;
    private GenerationInfo generationPower;
    private JsonGen jsonGen = new JsonGen();



    public GenBehFirst(Time time, GenerationInfo generationPower) {
        this.time = time;
        this.generationPower = generationPower;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchProtocol("Start");
        ACLMessage receivedMsg = myAgent.receive(mt);

        //ожидание сообщения о начале торгов и подтверждение участия
        if (receivedMsg != null) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            topic = new Topic(myAgent).subsTopic(receivedMsg.getContent());
            message.addReceiver(topic);
            message.setProtocol("Ready");
            message.setContent("OK");
            myAgent.send(message);
//            System.out.println(myAgent.getLocalName() + "  подписался   " + topic.getLocalName());
            myAgent.addBehaviour(new GenBehSecond(time, topic, generationPower, jsonGen));
        }
    }

    @Override
    public boolean done() {
        return flag;
    }

}
