import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Notification
{

    private String notificationId;
    private String userName;
    private String email;
    private String mobile;
    private String message;
    private String notificationType;
    private String priority;


    public Notification(String notificationId, String userName, String email, String mobile, String message, String notificationType, String priority) {
        this.notificationId = notificationId;
        this.userName = userName;
        this.email = email;
        this.mobile = mobile;
        this.message = message;
        this.notificationType = notificationType;
        this.priority = priority;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId='" + notificationId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", message='" + message + '\'' +
                ", notificationType='" + notificationType + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}


@FunctionalInterface
interface NotificationSender {
    void send(Notification notification);
}


public class NotificationRoutingEngine
{
    public static void main(String[] args)
    {


        List<Notification> notifications = Arrays.asList(

                new Notification(    "N001",  "Ravi", "ravi@gmail.com", "9876543210","Your class starts at 7:30 AM",  "EMAIL",   "HIGH"),
                new Notification( "N002",    "Priya","priya@gmail.com", "9123456789", "Your salary has been credited", "SMS", "NORMAL"),
                new Notification("N003","Amit", null, "9988776655", "Your order is shipped",   "WHATSAPP", "NORMAL"),
                new Notification(   "N004",  "Sneha", "sneha@gmail.com", null, "Special Offer Available", "EMAIL", "NORMAL"),
                new Notification(  "N005", "Karan", "karan@gmail.com",  "9871234567", "Security Alert",  "PUSH", "HIGH")
        );



        NotificationSender emailSender = n ->
        {
            if (n.getEmail() == null || n.getEmail().isBlank())
            {
                System.out.println("Email skipped. Email not available.");
                return;
            }

            System.out.println("Email Sent to " + n.getEmail());
            System.out.println("Message : " + n.getMessage());
        };


        NotificationSender smsSender = n ->
        {
            if (n.getMobile() == null || n.getMobile().isBlank())
            {
                System.out.println("SMS skipped. Mobile not available.");
                return;
            }
            System.out.println("SMS Sent to " + n.getMobile());
            System.out.println("Message : " + n.getMessage());
        };


        NotificationSender whatsappSender = n ->
        {

            if (n.getMobile() == null || n.getMobile().isBlank()) {

                System.out.println("WhatsApp skipped. Mobile not available.");
                return;
            }

            System.out.println("WhatsApp Sent to " + n.getMobile());
            System.out.println("Message : " + n.getMessage());
        };

        NotificationSender pushSender = n -> {

            System.out.println("Push Notification Sent");
            System.out.println("Message : " + n.getMessage());
        };



        Map<String, NotificationSender> senders = new HashMap<>();

        senders.put("EMAIL", emailSender);
        senders.put("SMS", smsSender);
        senders.put("WHATSAPP", whatsappSender);
        senders.put("PUSH", pushSender);



        notifications.forEach(notification -> {

            System.out.println("\n===================================");

            System.out.println("Notification Id : "
                    + notification.getNotificationId());



            if ("HIGH".equalsIgnoreCase(notification.getPriority()))
            {

                System.out.println("Sending HIGH Priority Notification...");

                emailSender.send(notification);

                whatsappSender.send(notification);
            }

            else
            {

                System.out.println("Sending NORMAL Priority Notification...");

                NotificationSender sender =
                        senders.get(notification.getNotificationType());

                if (sender != null) {
                    sender.send(notification);
                }
            }
        });




    }
}
