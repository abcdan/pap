package nl.duckstudios.pintandpillage;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PintAndPillageApplication {

    private static ConfigurableApplicationContext context;
    public static void main(String[] args) {
        context = SpringApplication.run(PintAndPillageApplication.class, args);
    }


    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(PintAndPillageApplication.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }

}
