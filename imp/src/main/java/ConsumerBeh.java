import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ConsumerBeh extends TickerBehaviour {
    private Time time;
    private ParsConfig getConfig;//private+name
    private double requementPower;
    private int period;
    private JsonCons jsonCons = new JsonCons();
    private int StartTime;

    public ConsumerBeh(Agent a, int period, Time time, ParsConfig getConfig) {
        super(a, period);
        this.time = time;
        this.getConfig = getConfig;
        this.period = period;

    }


    @Override
    protected void onTick() {
        StartTime = time.getCurrentTime();

//        запрос требуемой мощности на ближайший отрезок времени:

//        requementPower = 0.1;
        requementPower = getConfig.pow(time.getCurrentTime() / 60, myAgent.getLocalName() + ".xml")
                *getConfig.maxPower(myAgent.getLocalName()+".xml");
//        if (myAgent.getLocalName().equals("Consumer1")){
//        System.out.println(getConfig.maxPower(time.getCurrentTime(),myAgent.getLocalName()+".xml"));}
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);//имена сообщениям
        message.setContent(String.valueOf(requementPower));
        message.setProtocol("NeedAuction");
        message.setOntology("All");
        message.addReceiver(myAgent.getAID("Distributor"));
        myAgent.send(message);

//        новое поведение, ожидающее сообщений о вердикте сделки, если необходимо, запрашивает еще раз
        myAgent.addBehaviour(new Behaviour() {
            boolean flag = false;

            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchProtocol("End");
                ACLMessage receivedMsg = myAgent.receive(mt);

                if (receivedMsg != null) {
                    Map<String, String> data;

                    if (!(receivedMsg.getContent().equals("No"))) {
                        System.out.println(myAgent.getLocalName() + " купил за  " + receivedMsg.getContent());
                        System.out.println("");
                        data = jsonCons.data(Double.parseDouble(receivedMsg.getContent()),
                                Double.parseDouble(receivedMsg.getOntology()),
                                "Ok", myAgent.getLocalName(), time.getCurrentTime() / 60);

                        flag = true;
                    } else {
                        System.out.println(myAgent.getLocalName() + " не купил ");
                        System.out.println("");
                        data = jsonCons.data(0.0,
                                Double.parseDouble(receivedMsg.getOntology()),
                                "No", myAgent.getLocalName(), time.getCurrentTime() / 60);
                        if (time.getCurrentTime() - StartTime < 0.85 * period / time.minute) {

//                        запрос минимальной мощности
                            try {
                                Thread.sleep(time.minute );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                            message.setContent(String.valueOf(requementPower));
                            message.addReceiver(myAgent.getAID("Distributor"));
                            message.setProtocol("NeedAuction");
                            message.setOntology("All");
                            myAgent.send(message);

                        } else {
                            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                            message.setContent(String.valueOf(requementPower));
                            message.addReceiver(myAgent.getAID("Distributor"));
                            message.setProtocol("NeedAuction");
                            message.setOntology("System");
                            myAgent.send(message);
                        }
//

                    }

                    String stroka = jsonCons.stroka(data, myAgent.getLocalName());
                    String fileName = String.format("C:\\Users\\anna\\IdeaProjects\\imp\\%s.json", myAgent.getLocalName());
                    try {
                        FileOutputStream file = new FileOutputStream(fileName);
                        file.write(stroka.getBytes());
                        file.flush();
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public boolean done() {
                return flag;
            }
        });

    }


}
