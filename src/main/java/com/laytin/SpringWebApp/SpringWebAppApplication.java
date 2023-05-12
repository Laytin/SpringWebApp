package com.laytin.SpringWebApp;

import com.laytin.SpringWebApp.models.*;
import com.laytin.SpringWebApp.services.ProductService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;

@SpringBootApplication
@EnableJpaRepositories
public class SpringWebAppApplication implements CommandLineRunner {

	@Resource
	ProductService productService;
	public static void main(String[] args) {
		SpringApplication.run(SpringWebAppApplication.class,args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<Address, OrdAddress>() {
			@Override
			protected void configure() {
				skip(destination.getId());
			}
		});
		return modelMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		productService.initImageStorage();
	}
}
