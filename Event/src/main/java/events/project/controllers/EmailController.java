package events.project.controllers;

import events.project.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.security.Principal;

@RestController
public class EmailController {

    private final EmailService emailSender;
    //private final TemplateEngine templateEngine;

    @Autowired
    public EmailController(EmailService emailSender,
                           TemplateEngine templateEngine){
        this.emailSender = emailSender;
       // this.templateEngine = templateEngine;
    }

    @RequestMapping(value = "/userRegistered", method = RequestMethod.GET)
    public ResponseEntity send(Principal user) {
        Context context = new Context();
        context.setVariable("header", "Rejestracja - EventProject");
        context.setVariable("title", "#8 Spring Boot – email - szablon i wysyłanie");
        context.setVariable("description", "Drogi użytkowniku, od teraz masz dostęp do największej bazy wydarzeń w Polsce!");

        //String body = templateEngine.process("template", context);
        emailSender.sendEmail("", "EventProject", "ggggggggggggggggggg");
        return   ResponseEntity.ok().build();
    }
}