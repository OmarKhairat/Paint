package com.example.backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RestController;
import javax.swing.*;


@RestController
@SpringBootApplication
public class BackendApplication extends JFrame {


	public static void main(String[] args) {

		var ctx = new SpringApplicationBuilder(BackendApplication.class)
				.headless(false).run(args);


	}
}