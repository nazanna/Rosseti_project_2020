import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DistribBehFirst extends Behaviour {
    private boolean flag = false;
    private JsonDistr json = new JsonDistr();
    private Time time;

    public DistribBehFirst(Time time) {
        this.time = time;
    }

    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.MatchProtocol("NeedAuction");
        ACLMessage receivedMsg = myAgent.receive(mt);
        AID[] resultsAID;
//        поиск агентов, зарегестрированных в DF с генерацией
        resultsAID = DfSearch("Generation");

        if (receivedMsg != null) {

            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            AID topic = new Topic(myAgent).createTopic(receivedMsg.getSender().getLocalName() + "t");
            topic = new Topic(myAgent).subsTopic(topic.getLocalName());
            message.setProtocol("Start");
            message.setContent(topic.getLocalName());

            for (AID aid : resultsAID) {
                message.addReceiver(aid);
            }
            myAgent.send(message);
            System.out.println(receivedMsg.getSender().getLocalName() + "  запросил  " + receivedMsg.getContent());
//            создание нового поведения
            myAgent.addBehaviour(new DistribBehSecond(topic, resultsAID, receivedMsg.getSender().getLocalName(),
                    Double.parseDouble(receivedMsg.getContent()), json, time, receivedMsg.getOntology()));
        }
    }

    @Override
    public boolean done() {
        return flag;
    }


    public AID[] DfSearch(String type) {
        DFAgentDescription dfc = new DFAgentDescription();
        ServiceDescription dfs = new ServiceDescription();
        dfs.setType(type);
        dfc.addServices(dfs);


        DFAgentDescription[] results = new DFAgentDescription[0];
        try {
            results = DFService.search(myAgent, dfc);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        AID[] resultsAID = new AID[results.length];
        for (int i = 0; i < results.length; i++) {
            resultsAID[i] = results[i].getName();
        }

        return resultsAID;
    }

}