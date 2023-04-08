package com.laytin.SpringWebApp;

import com.laytin.SpringWebApp.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebAppApplication.class,args);
		//SpringApplication.run(SpringWebAppApplication.class, args);
	}

}
