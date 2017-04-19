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
	

    public Monitor() {}
	
    public void onMessage(Message message) {
    	Connection connection=null;
    	try {
                System.out.println("Monitor=> odebra³em wiadomoœæ z sriTopic");
                ObjectMessage msg = (ObjectMessage) message;
                DTOState state = (DTOState) msg.getObject();
                DTOState checkedState=checkValues(state);
                System.out.println("checkedState"+checkedState);
                connection = connectionFactory.createConnection();
    			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	        MessageProducer messageProducer = session.createProducer(queue);
    	        ObjectMessage processedMsg=session.createObjectMessage(checkedState);
    	        messageProducer.send(processedMsg);
        } catch (JMSException e) {
            e.printStackTrace();
        }finally{
			if(connection!=null)
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
		}
    }
    private DTOState checkValues(DTOState state){ 
    	int engine=state.getEngineTemp();
    	int oil=state.getOilPressure();
    	int tyres=state.getTyresPressure();
    	int alerts=0;
    	System.out.println("Monitor:sprawdzam obiekt");
    	System.out.println(state);
    	System.out.println("engine: "+engine);
    	System.out.println("oil: "+oil);
    	System.out.println("tyres: "+tyres);
    	if (engine>80){alerts+=1;}
    	if (oil>7||oil<3){alerts+=1;}
    	if (tyres>7||tyres<3){alerts+=1;}
    	DTOState processedState=new DTOState(engine, tyres, oil, LocalDateTime.now(), (alerts>0)? true:false, (alerts>1)?true:false);
    	System.out.println("Monitor:ile alertów nastawionych? : "+alerts);
    	return processedState;
    }
}
