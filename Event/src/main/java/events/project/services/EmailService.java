package events.project.services;

public interface EmailService {

    void sendEmail(String to, String subject, String content);
}
