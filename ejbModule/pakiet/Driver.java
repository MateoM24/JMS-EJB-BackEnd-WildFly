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
	
    public Driver() {}
	
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)//M
    public void onMessage(Message message) {
        //mozna dopisaÊ jakies warunki przesy≥ania proüby do mechanikÛw
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
			System.out.println("Driver: sent message: Can I go to pit stop?");
			QueueReceiver receiver = session.createReceiver(tempQ);
			connection.start();
			TextMessage returnedMsg=(TextMessage)receiver.receive();
			System.out.println("Driver: otrzyma≥em odpowiedü: "+returnedMsg.getText());
			
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
}