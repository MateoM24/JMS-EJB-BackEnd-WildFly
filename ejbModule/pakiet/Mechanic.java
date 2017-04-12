package pakiet;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: Mechanic
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:/jms.queue.PitStopQueue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "java:/jms.queue.PitStopQueue")
public class Mechanic implements MessageListener {

	@Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
	
	//@Resource(lookup =  "java:/jms.queue.ResponseQueue")
   // private Queue ResponseQueue;

    public Mechanic() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)//M
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        String answer=null;
    	if(Math.random()>=0.5){
        	answer="Yes";
        }else{
        	answer="No";
        }
    	Connection connection=null;
		try {
			TextMessage msg = (TextMessage) message;
			String receivedMessage =msg.getText();
			System.out.println("Mecanic: I've received text message->"+receivedMessage);
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(message.getJMSReplyTo());
			TextMessage messageToSend=session.createTextMessage();
			messageToSend.setText(answer);
			System.out.println("Mechanic wysy³a wiadomoœæ:"+messageToSend.getText());
			messageProducer.send(messageToSend);
			
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
