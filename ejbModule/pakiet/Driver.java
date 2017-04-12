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
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: Driver
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:/jms.queue.DriverQueue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "java:/jms.queue.DriverQueue")
public class Driver implements MessageListener {

	@Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
	
	@Resource(lookup =  "java:/jms.queue.PitStopQueue")
    private Queue PitStopQueue;
	
	//@Resource(lookup =  "java:/jms.queue.ResponseQueue")
    //private Queue ResponseQueue;
	
    public Driver() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)//M
    public void onMessage(Message message) {
        //DOPISAC jakies warunki przesy�ania pro�by do mechanik�w
    	QueueConnection connection=null;
		try {
			ObjectMessage msg = (ObjectMessage) message;
			DTOState state = (DTOState) msg.getObject();
			System.out.println("Driver: I've received->"+state);
			connection = (QueueConnection)connectionFactory.createConnection();
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			TemporaryQueue tempQ = session.createTemporaryQueue();
			MessageProducer messageProducer = session.createProducer(PitStopQueue);
			TextMessage messageToSend=session.createTextMessage();
			messageToSend.setJMSReplyTo(tempQ);
			messageToSend.setText("Can I go to pit stop?");
			messageProducer.send(messageToSend);
			QueueReceiver receiver = session.createReceiver(tempQ);
			connection.start();
			TextMessage returnedMsg=(TextMessage)receiver.receive();
			System.out.println("Driver: otrzyma�em odpowied�: "+returnedMsg.getText());
			
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
