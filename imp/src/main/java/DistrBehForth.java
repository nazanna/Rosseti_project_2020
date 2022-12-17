import jade.core.BehaviourID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DistrBehForth extends Behaviour {
    private boolean flag;
    private int count;
    @Override
    public void action() {

            MessageTemplate mt = (MessageTemplate.MatchProtocol("Control"));
            ACLMessage receivedMsg = myAgent.receive(mt);
            if (receivedMsg != null) {
                    count++;
//                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//                message.addReceiver(topic);
//                message.setProtocol("Control");
//                message.setContent("OK");
//                myAgent.send(message);
                if (count==4) {
                    flag = true;
                    count=4;
                    System.out.println("Yuhuu");
                }

        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
