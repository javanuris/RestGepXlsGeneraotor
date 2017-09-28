package kazpost.nuris.gep.configurations;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigurations {

    @Bean()
    public HSSFWorkbook getHSSFWorkbook(){
        return new HSSFWorkbook();
    }
}
