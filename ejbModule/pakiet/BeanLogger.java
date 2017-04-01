package pakiet;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: BeenLogger
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "jms.topic.sriTopic"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "jms.topic.sriTopic")
public class BeanLogger implements MessageListener {

    /**
     * Default constructor. 
     */
    public BeanLogger() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	try {
            if (message instanceof TextMessage) {
                System.out.println("Topic: I received a TextMessage");
                TextMessage msg = (TextMessage) message;
                System.out.println("Message is : " + msg.getText());
            } else if (message instanceof ObjectMessage) {
                System.out.println("Topic: I received an ObjectMessage");
                ObjectMessage msg = (ObjectMessage) message;
                DTOState state = (DTOState) msg.getObject();
                System.out.println(state);
            } else {
                System.out.println("Not a valid message for this Queue MDB");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        
    }

}
