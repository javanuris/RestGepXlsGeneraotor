package kazpost.nuris.gep.controllers;

import kazpost.nuris.gep.models.Form103Model;
import kazpost.nuris.gep.models.Form103XlsSheet;
import kazpost.nuris.gep.services.Form103XlsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class RestApiController {

    @Autowired
    private Form103XlsService form103XlsService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getTestGet() {
        return "Hello";
    }

    @RequestMapping(value = "/hello/{id}", method = RequestMethod.POST)
    public String testPost(@RequestParam("name") String name, @PathVariable("id") String age) {
        return "My name is " + name + ". And my age: " + age;
    }

    @RequestMapping(value = "/xlsExample", method = RequestMethod.GET)
    public Form103XlsSheet getForm103Xls() {
        Form103Model form103Model = new Form103Model();
        return form103Model.getSheet();
    }

    @RequestMapping(value = "/xlsExample", method = RequestMethod.POST)
    public Form103XlsSheet getForm103Xls(@RequestBody Form103XlsSheet form103XlsSheet) {
        return form103XlsSheet;
    }

    @RequestMapping(value = "/generateForm103", method = RequestMethod.POST)
    public ResponseEntity generateForm103(@RequestBody Form103XlsSheet form103XlsSheet) {
        try {
            //Метод не кидает исключний, это временная заглушка.
            form103XlsService.generateForm103XlsFile(form103XlsSheet);
            return new ResponseEntity("OK", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

}
