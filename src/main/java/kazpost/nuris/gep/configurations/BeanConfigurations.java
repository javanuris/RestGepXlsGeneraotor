package kazpost.nuris.gep.configurations;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfigurations {

    @Bean()
    @Scope("prototype")
    public HSSFWorkbook getHSSFWorkbook(){
        return new HSSFWorkbook();
    }
}
