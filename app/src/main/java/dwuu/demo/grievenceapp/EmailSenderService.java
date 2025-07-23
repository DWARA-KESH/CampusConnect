package dwuu.demo.grievenceapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSenderService extends IntentService {

    private static final String ADMIN_EMAIL = "dwarakeswaran2002@ptuniv.edu.in";
    private static final String ADMIN_PASSWORD = "icqxtpocabdhqsxg";
    //icqxtpocabdhqsxg
    //iwgihesnmihtzxmq
    public EmailSenderService() {
        super("EmailSenderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ComplaintForm complaintForm = intent.getParcelableExtra("complaint_form");
            sendEmail(getApplicationContext(), complaintForm);
        }
    }

    private void sendEmail(Context context, ComplaintForm complaintForm) {

        String emailBody = composeEmailBody(complaintForm);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(ADMIN_EMAIL));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ADMIN_EMAIL));

            message.setSubject(complaintForm.getSubject());

            message.setText(emailBody);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String composeEmailBody(ComplaintForm complaintForm) {

        StringBuilder builder = new StringBuilder();
        builder.append("New Complaint Details:\n\n");
        builder.append("Full Name: ").append(complaintForm.getFullName()).append("\n");
        builder.append("Email: ").append(complaintForm.getEmail()).append("\n");
        builder.append("Student Id: ").append(complaintForm.getStudentId()).append("\n");
        builder.append("Phone Number: ").append(complaintForm.getPhoneNumber()).append("\n");
        builder.append("Subject: ").append(complaintForm.getSubject()).append("\n");
        builder.append("Description: ").append(complaintForm.getDescription()).append("\n");
        builder.append("Location: ").append(complaintForm.getLocation()).append("\n");
        builder.append("Witness Name: ").append(complaintForm.getWitnessName()).append("\n");
        builder.append("Witness Id: ").append(complaintForm.getWitnessId()).append("\n");
        builder.append("Witness Contact: ").append(complaintForm.getWitnessNum()).append("\n");
        builder.append("Expected Resolution: ").append(complaintForm.getResolution()).append("\n");

        return builder.toString();
    }
}
