package nl.duckstudios.pintandpillage.controller;

import nl.duckstudios.pintandpillage.PintAndPillageApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// Thanks: https://www.baeldung.com/java-restart-spring-boot-app
@RestController
public class RestartController {

    @GetMapping("/restart")
    public void restart() {

        if (System.getenv("GODMODE") != null) {
            PintAndPillageApplication.restart();
        }
    }
}