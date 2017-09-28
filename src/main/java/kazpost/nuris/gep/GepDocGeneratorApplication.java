package kazpost.nuris.gep;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages={"kazpost.nuris.gep"})
public class GepDocGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GepDocGeneratorApplication.class, args);
	}
}
