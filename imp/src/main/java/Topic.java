import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.messaging.TopicManagementHelper;
//класс для работы с Topic
public class Topic {
    public Agent agent;

    public Topic(Agent agent) {
        this.agent = agent;
    }

    public AID createTopic(String topicName) {
        TopicManagementHelper topicHelper;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    agent.getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(topicName);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }
    public AID subsTopic(String topicName) {
        TopicManagementHelper topicHelper = null;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    agent.getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(topicName);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }
}
