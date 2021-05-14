package com.agile.petclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.agile.petclinic.entities.User;
import com.agile.petclinic.repositories.UserRepository;

@Configuration
@Profile("prod")
public class ProdConfig implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		
		User usr = userRepository.findByUsername("admin");
				
		if (usr == null) {			
			User usr1 = new User(null, "admin", "admin", pe.encode("admin123"));
			
			userRepository.save(usr1);
		}

	}

}
