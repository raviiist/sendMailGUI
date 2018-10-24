package sample;
// Java program to send email

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane anchorLayout;
    @FXML
    private Label SendMailLabel;
    @FXML
    private Button sendButton;
    @FXML
    private Label sentLabel;
    @FXML
    private Label failedLabel;
    @FXML
    private TextField version1;
    @FXML
    private Hyperlink closeLink;
    @FXML
    private PasswordField passwd;
    @FXML
    void systemClose(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sentLabel.setVisible(false);
        failedLabel.setVisible(false);
    }
    @FXML
    void freshInputs(MouseEvent event) {
        sentLabel.setVisible(false);
        failedLabel.setVisible(false);
    }
    @FXML
    void clickSend(ActionEvent event) {

        String javaproject="AllInOne";
        //int v=1;
        CharSequence v=version1.getCharacters();
        String version="v"+v;
        System.out.println(version);
        //Creating zip and adding files into it...
        zipFile czf =new zipFile();
        try {
            czf.createZipFile("Time.java");
            czf.createZipFile("Main.java");
            czf.createZipFile("Controller.java");
            czf.createZipFile("sample.fxml");
            czf.createZipFile("testStyle.css");
            czf.createZipFile("TestScene.java");
            czf.createZipFile("testScene.fxml");
            czf.createZipFile("background.jpg");
            czf.createZipFile("u1F913.png");
            czf.createZipFile("icons8-gps-antenna-96.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //making smtp configuration...
        String host="smtp.gmail.com";
        final String user="experimentxcosmo@gmail.com";//change accordingly
        //final String password="*********";//change accordingly
        final String password=passwd.getText();     //change accordingly
        String to="ravi.kumar@istrac.org";//change accordingly

        //Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        //Compose the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Java_Idea "+javaproject+version);
            //message content can be put in 2 ways:
            //message.setText("Simple program of sending email using JavaMail API1");
            //create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("This is the src of "+javaproject);
            Multipart multipart = new MimeMultipart();
            //multipart.addBodyPart(messageBodyPart1);
            //message.setContent(multipart);

            //With attachment
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            String filename = "/home/ravi/Idea_Projects/allinoneRS2/src/sample.zip";//change accordingly
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);

            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
            message.setContent(multipart);

            //send the message
            //if (Transport.send(message))
            Transport.send(message);
            if (true) {
                System.out.println("message sent successfully...");
                sentLabel.setVisible(true);
                failedLabel.setVisible(false);
            }else {
                failedLabel.setVisible(true);
                sentLabel.setVisible(false);
            }
        } catch (MessagingException e) {e.printStackTrace();
        failedLabel.setVisible(true);
        sentLabel.setVisible(false);
        }

    }


}
class zipFile {
    Map<String, String> env = new HashMap<>();
    String flpath="/home/ravi/Idea_Projects/allinoneRS2/src/sample/";
    String zippath="/home/ravi/Idea_Projects/allinoneRS2/src/";
    String zipname="sample.zip";
    URI uri = URI.create("jar:file:"+zippath+zipname);
    void createZipFile(String file) throws IOException {
        env.put("create", "true");

        try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
            Path externalTxtFile = Paths.get(flpath+file);
            Path pathInZipfile = zipfs.getPath(file);
            // copy a file into the zip file
            Files.copy(externalTxtFile, pathInZipfile, StandardCopyOption.REPLACE_EXISTING);
        }

    }
}