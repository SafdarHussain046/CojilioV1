package utilities;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.io.File;

public class EmailUtils {
 
    public static void sendEmailWithAttachment(String toEmail, String subject, String body, String attachmentPath) {
        
        final String fromEmail = "safdarh462@gmail.com";
        final String password = "rdub jumb zajf cepb"; // Gmail App Passwd

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject(subject);

            // Email Body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
 
            // Attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(attachmentPath));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            msg.setContent(multipart);

            Transport.send(msg);
            System.out.println("Email Sent Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
