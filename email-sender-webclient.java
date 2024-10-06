import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
public class EmailSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailSenderApplication.class, args);
    }

    // Assume this method is called after fetching email models from the database
    public void sendEmails(List<EmailModel> emailModels) {
        WebClient webClient = WebClient.create("http://app2-url");

        webClient.post()
                .uri("/send-emails")
                .body(Mono.just(emailModels), List.class)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // This makes the call synchronous. For async, use subscribe() instead
    }
}

class EmailModel {
    private String emailAddress;
    private int typeIdentifier;
    // getters, setters, constructor
}
