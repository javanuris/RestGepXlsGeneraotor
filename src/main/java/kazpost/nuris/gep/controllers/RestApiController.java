package kazpost.nuris.gep.controllers;

import kazpost.nuris.gep.models.Form103Path;
import kazpost.nuris.gep.models.Form103XlsSheet;
import kazpost.nuris.gep.services.Form103ConvertToXlsService;
import kazpost.nuris.gep.services.Form103XlsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class RestApiController {
    Logger log = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private Form103XlsService form103XlsService;
    @Autowired
    private Form103ConvertToXlsService form103ConvertToXlsService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getTestGet() {
        log.info("GET/hello");
        return "Hello";
    }

    @RequestMapping(value = "/generateForm103", method = RequestMethod.POST, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity generateForm103(@RequestBody Form103XlsSheet form103XlsSheet) {
        // log.info(form103XlsSheet.toString());
        try {
            //Метод не кидает исключний, это временная заглушка.
            form103XlsService.generateForm103XlsFile(form103XlsSheet);
            return new ResponseEntity("OK", HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error Gen", e);
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/jsonString", method = RequestMethod.POST, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public void getJsonString(@RequestBody String json) {
        log.info("!!#$@@{String -*-*->" + json + " <-*-*-String}@@$#!!");
    }

    @RequestMapping(value = "/form103Path", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Form103Path getForm103Path() {
        Form103Path path = new Form103Path();

        path.setToPath("/test");
        path.setFileNameAfter("testAfter");
        path.setFromPath("/160000/160000/COMMON");
        path.setFileNameBefore("test");

        return path;
    }

    @RequestMapping(value = "/convertForm103", method = RequestMethod.POST, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity convertForm103(@RequestBody Form103Path form103Path) {
        try {
            form103ConvertToXlsService.convertForm103Xls(form103Path);
            return new ResponseEntity("OK", HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error Gen", e);
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }




}
