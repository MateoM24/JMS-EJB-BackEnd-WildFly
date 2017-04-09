package pakiet;

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

/**
 * Message-Driven Bean implementation class for: MessageRouter
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:/jms.queue.sriQueue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "java:/jms.queue.sriQueue")
public class MessageRouter implements MessageListener {

	@Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

	@Resource(lookup =  "java:/jms.queue.DriverQueue")
    private Queue driverQueue;
	
	@Resource(lookup =  "java:/jms.queue.MechanicQueue")
    private Queue mechanicQueue;
	
    public MessageRouter() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	Connection connection=null;
    	try {
            ObjectMessage msg = (ObjectMessage) message;
			DTOState state = (DTOState) msg.getObject();
			boolean driverAlert=state.isDriverAlert();
			boolean mecanicAlert=state.isMechanicAlert();
			//===
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        
	        ObjectMessage processedMsg=session.createObjectMessage(state);
	        System.out.println("MessageRouter-otrzyma³em: "+state);
			if(driverAlert==true){
			//wyslij do driverQueue	
				MessageProducer messageProducer = session.createProducer(driverQueue);
				messageProducer.send(processedMsg);
				System.out.println("MessageRouter=> wys³¹³em wiadomoœæ do driverQueue");
			}
			if(mecanicAlert==true){
				//wyœlij do mechanicQueue
				MessageProducer messageProducer = session.createProducer(mechanicQueue);
				messageProducer.send(processedMsg);
				System.out.println("MessageRouter=> wys³¹³em wiadomoœæ do mechanicQueue");
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
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

}
