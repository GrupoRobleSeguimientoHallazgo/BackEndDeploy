package com.example.SeguimientoHallazgo.Util.Email;

import com.example.SeguimientoHallazgo.Domain.DTO.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    public void sendMail(EmailDTO pEmail) throws MessagingException {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");

            helper.setTo(pEmail.getToDestination());
            helper.setSubject(pEmail.getEmailTitle());

            Context context = new Context();
            context.setVariable("emailBody", pEmail.getEmailBody());
            context.setVariable("emailTitle", pEmail.getEmailTitle());
            context.setVariable("recipientName", pEmail.getRecipientName());
            String contentHTML = templateEngine.process("email.html", context);

            helper.setText(contentHTML, true);
            javaMailSender.send(message);
        }catch (Exception e){
            throw  new RuntimeException("Error al enviar correo: "+ e.getMessage());
        }


    }
}
