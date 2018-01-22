package events.project;

import events.project.events.project.service.StorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


import javax.annotation.Resource;
import javax.validation.Validator;

@SpringBootApplication
public class EventApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventApplication.class, args);
	}

	@Resource
	StorageService storageService;


	public void run(String... arg) throws Exception {
		//storageService.deleteAll();
		storageService.init();
	}

}
