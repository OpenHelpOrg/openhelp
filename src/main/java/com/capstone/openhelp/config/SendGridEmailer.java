package com.capstone.openhelp.config;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import static com.capstone.openhelp.config.APIK.SENDGRID_API_KEY;

import java.io.IOException;

public class SendGridEmailer {

        public static void main(String[] args) throws IOException {
            Email from = new Email("test@example.com");
            String subject = "Sending with SendGrid is Fun";
            Email to = new Email("christian.crousserkaiman@gmail.com");
            Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sg.api(request);
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());
            } catch (IOException ex) {
                throw ex;
            }
        }
    }


//        Email from = new Email("test@example.com");
//        Email to = new Email("christian.crousserkaiman@gmail.com>"); // use your own email address here
//
//        String subject = "Sending with Twilio SendGrid is Fun";
//        Content content = new Content("text/html", "and <em>easy</em> to do anywhere with <strong>Java</strong>");
//
//        Mail mail = new Mail(from, subject, to, content);
//
//        SendGrid sg = new SendGrid(System.getenv("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"));
//        Request request = new Request();
//
//        request.setMethod(Method.POST);
//        request.setEndpoint("mail/send");
//        request.setBody(mail.build());
//
//        Response response = sg.api(request);
//
//        System.out.println(response.getStatusCode());
//        System.out.println(response.getHeaders());
//        System.out.println(response.getBody());
//    }
//}