package events.project;

import events.project.image.StorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import javax.annotation.Resource;

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
