package app;

import app.main.GameFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WwApplication {

	public static void main(String[] args) {
		new GameFrame();
	}

}
