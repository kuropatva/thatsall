package me.kuropatva.thatsall;

import me.kuropatva.thatsall.model.cards.RandomPowerCardGenerator;
import me.kuropatva.thatsall.model.lobby.LobbyGarbageCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThatsallApplication {

	public static void main(String[] args) {
		RandomPowerCardGenerator.verify();
		Thread.ofPlatform().start(new LobbyGarbageCollector());
		SpringApplication.run(ThatsallApplication.class, args);
	}

}
