package bay.university.resmon.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
