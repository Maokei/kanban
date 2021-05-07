package se.maokei.kanban.email;

public interface EmailSender {
    void sendEmail(String to, String content);
}
