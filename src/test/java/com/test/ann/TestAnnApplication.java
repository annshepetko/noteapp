package com.test.ann;

import org.springframework.boot.SpringApplication;

public class TestAnnApplication {

	public static void main(String[] args) {
		SpringApplication.from(AnnApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
