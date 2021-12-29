package org.sid.secservice;

import java.util.stream.Stream;

import org.sid.secservice.entities.AppRole;
import org.sid.secservice.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start (AccountService accountService){
		System.out.println("Fuck you");
		return args->{
			accountService.saveRole(new AppRole(null, "USER"));
			accountService.saveRole(new AppRole(null, "ADMIN"));
			Stream.of("user1", "user2", "user3", "admin").forEach(un->{
				accountService.saveUser(un, "1234", "1234");
			});
			accountService.addRoleToUser("admin", "ADMIN");
		};
	}

	@Bean
	BCryptPasswordEncoder getBCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
