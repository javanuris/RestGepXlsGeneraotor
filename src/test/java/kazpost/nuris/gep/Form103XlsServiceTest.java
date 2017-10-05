package kazpost.nuris.gep;

import kazpost.nuris.gep.services.Form103XlsService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Form103XlsServiceTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPEWhenNullParam() throws Exception{

        Form103XlsService form103XlsService = new Form103XlsService();

        Method method = Form103XlsService.class.getDeclaredMethod("createXlsBodyPart", HSSFWorkbook.class , HSSFSheet.class);
        method.setAccessible(true);
        try {
            method.invoke(form103XlsService, new Object[]{null, null});
        }catch (InvocationTargetException e) {
            if (e.getCause() instanceof NullPointerException) {
                throw new NullPointerException();
            }
        }
    }
}
