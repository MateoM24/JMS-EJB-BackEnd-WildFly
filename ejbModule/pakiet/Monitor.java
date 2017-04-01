package pakiet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: Monitor
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "jms.topic.sriTopic"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "jms.topic.sriTopic")
public class Monitor implements MessageListener {
	
	public static enum Norm{
		ENGINE_MAX(85),
		OIL_MAX(8),
		OIL_MIN(2),
		TYRES_MAX(8),
		TYRES_MIN(2);
		private int value;
		private Norm(int value){
			this.value=value;
		}
	}
	
    /**
     * Default constructor. 
     */
    public Monitor() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	try {
            if (message instanceof ObjectMessage) {
                System.out.println("Monitor=> odebra³em wiadomoœæ z topic");
                ObjectMessage msg = (ObjectMessage) message;
                DTOState state = (DTOState) msg.getObject();
              
                
            } else {
                System.out.println("Not a valid message for this Queue MDB");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    private Boolean[] checkValues(){ 
    	Boolean[] results={true,true};
    	//tu dopisz sprawdzanie tych wartoœci i niech pierwsza wartosc rezults mówi czy przes³ac do kierwcy alarm,
    	//a druga czy do mechanikow tez
    	
    	return results;
    }

}
