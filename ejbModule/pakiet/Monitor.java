package pakiet;

import java.time.LocalDateTime;
import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

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
	
	@Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

	@Resource(lookup =  "java:/jms.queue.sriQueue")
    private Queue queue;
	
	public static enum Norm{
		ENGINE_MAX(85),
		OIL_MAX(7),
		OIL_MIN(3),
		TYRES_MAX(7),
		TYRES_MIN(3);
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
    	Connection connection=null;
    	try {
                System.out.println("Monitor=> odebra³em wiadomoœæ z sriTopic");
                ObjectMessage msg = (ObjectMessage) message;
                DTOState state = (DTOState) msg.getObject();
                boolean[] results=checkValues(state);
                state.setAlerts(results);
                //=========
                connection = connectionFactory.createConnection();
    			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	        MessageProducer messageProducer = session.createProducer(queue);
    	        ObjectMessage processedMsg=session.createObjectMessage(state);
    	        messageProducer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }finally{
			if(connection!=null)
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
    }
    private boolean[] checkValues(DTOState state){ 
    	boolean[] results={false,false};
    	//tu dopisz sprawdzanie tych wartoœci i niech pierwsza wartosc rezults mówi czy przes³ac do kierowcy alarm,
    	//a druga czy do mechanikow tez
    	int engine=state.getEngineTemp();
    	int oil=state.getOilPressure();
    	int tyres=state.getTyresPressure();
    	int alerts=0;
    	//if (engine>Norm.ENGINE_MAX.value){alerts=+1;}
    	//if (oil>Norm.OIL_MAX.value||oil<Norm.OIL_MIN.value){alerts=+1;}
    	//if (tyres>Norm.TYRES_MAX.value||tyres<Norm.TYRES_MIN.value){alerts=+1;}
    	if (engine>80){alerts+=1;}
    	if (oil>7||oil<3){alerts+=1;}
    	if (tyres>7||tyres<3){alerts+=1;}
    	if (alerts>0){
    		results[0]=true;
    		if (alerts>1){
    			results[1]=true;
    		}
    	}
    	return results;
    }

}
