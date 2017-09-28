package kazpost.nuris.gep;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"kazpost.nuris.gep"})
public class GepDocGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GepDocGeneratorApplication.class, args);
	}
}
