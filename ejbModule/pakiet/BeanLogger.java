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

@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "jms.topic.sriTopic"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "jms.topic.sriTopic")
public class BeanLogger implements MessageListener {

    
    public BeanLogger() {}
	

    public void onMessage(Message message) {
    	try {
            if (message instanceof TextMessage) {
                System.out.println("BeanLogger: I received a TextMessage from sriTopic");
                TextMessage msg = (TextMessage) message;
                System.out.println("BeanLogger:Message is : " + msg.getText());
            } else if (message instanceof ObjectMessage) {
                System.out.println("BeanLogger: I received an ObjectMessage from sriTopic");
                ObjectMessage msg = (ObjectMessage) message;
                DTOState state = (DTOState) msg.getObject();
                File plik=new File("E:\\logSRI3.txt");
                plik.createNewFile();
                FileWriter fileWriter=new FileWriter(plik,true);
                fileWriter.write(state.toString()+"\n");
                fileWriter.flush();
                fileWriter.close();
                System.out.println("BeanLogger:I've written it to file E:/logSRI3.txt");
            } else {
                System.out.println("BeanLogger:Not a valid message");
            }
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        }
    }

}
