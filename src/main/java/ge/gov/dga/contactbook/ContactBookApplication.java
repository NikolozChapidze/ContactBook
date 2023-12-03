package ge.gov.dga.contactbook;

import ge.gov.dga.contactbook.model.dto.RegisterRequest;
import ge.gov.dga.contactbook.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static ge.gov.dga.contactbook.model.enums.Role.ADMIN;
import static ge.gov.dga.contactbook.model.enums.Role.MANAGER;

@SpringBootApplication
public class ContactBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactBookApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
                var admin = RegisterRequest.builder()
                        .username("Admin")
                        .phone("+995579050577")
                        .password("password")
                        .role(ADMIN)
                        .build();
                System.out.println("Admin token: " + service.register(admin).getAccessToken());

                var manager = RegisterRequest.builder()
                        .username("Manager")
                        .phone("+995596323377")
                        .password("password")
                        .role(MANAGER)
                        .build();
                System.out.println("Manager token: " + service.register(manager).getAccessToken());
        };
    }

}
