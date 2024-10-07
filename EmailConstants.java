package com.yourcompany.utils;

public class Constants {

    public static class MessageTemplates {
        public static final String NOTIFICATION_TEMPLATE = 
            "Dear %s,\n\n" +
            "This is a notification regarding %s.\n\n" +
            "Please take note of the following details:\n" +
            "%s\n\n" +
            "If you have any questions, please don't hesitate to contact us.\n\n" +
            "Best regards,\n" +
            "Your Company Team";

        public static final String REMINDER_TEMPLATE = 
            "Hello %s,\n\n" +
            "This is a friendly reminder about %s.\n\n" +
            "Important information:\n" +
            "%s\n\n" +
            "Please ensure you take the necessary action.\n\n" +
            "Thank you,\n" +
            "Your Company Team";

        public static final String NEWSLETTER_TEMPLATE = 
            "Dear Valued Customer,\n\n" +
            "Welcome to our latest newsletter!\n\n" +
            "This month's highlights:\n" +
            "%s\n\n" +
            "We hope you find this information useful.\n\n" +
            "Stay tuned for more updates!\n\n" +
            "Best wishes,\n" +
            "Your Company Newsletter Team";

        public static final String CUSTOM_TEMPLATE = 
            "%s\n\n" +
            "%s\n\n" +
            "Regards,\n" +
            "Your Company";
    }

    // You can add more constant classes here for other types of constants
}
