package kazpost.nuris.gep.controllers;

import com.google.gson.Gson;
import kazpost.nuris.gep.models.Book;
import kazpost.nuris.gep.models.Form103Model;
import kazpost.nuris.gep.models.Form103XlsSheet;
import kazpost.nuris.gep.services.Form103XlsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/")
public class RestApiController {

    @Autowired
    private Form103XlsService form103XlsService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET, consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public String getTestGet() {
        System.out.println("GET/hello " + new Date());
        return "Hello";
    }
    @RequestMapping(value = "/string", method = RequestMethod.POST,produces = {"text/plain"})
    @ResponseStatus(HttpStatus.OK)
    public String getString(@RequestBody String s){
        System.out.println(s);
        Gson gson = new Gson();
        System.out.println(s.replaceAll("\\s+",""));
        Form103XlsSheet xlsSheet = gson.fromJson(s,Form103XlsSheet.class);
        System.out.println(xlsSheet);
        return s;
    }


    @RequestMapping(value = "/stringSave", method = RequestMethod.POST,produces = {"text/plain"})
    @ResponseStatus(HttpStatus.OK)
    public String getStringSave(@RequestBody String s){
        Gson gson = new Gson();
        Form103XlsSheet xlsSheet = gson.fromJson(s,Form103XlsSheet.class);

        try {
            form103XlsService.generateForm103XlsFile(xlsSheet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(xlsSheet);
        return s;
    }

    @RequestMapping(value = "/hello/{id}", method = RequestMethod.POST )
    @ResponseStatus(HttpStatus.OK)
    public String testPost(@RequestParam("name") String name, @PathVariable("id") String age) {
        System.out.println("POST/hello/{id} name:" + name + " age:" + age + "   " + new Date());
        return "My name is " + name + ". And my age: " + age;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST, consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public String getTestPost() {
        System.out.println("POST/hello " + new Date());
        return "Hello";
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST, consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public Book getBookPost(@RequestBody Book book) {
        System.out.println("POST/book " + book.getName());
        return book;
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET, consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public Book getBookGET() {
        Book book = new Book();
        book.setName("Nuris");
        System.out.println("GET/book " + book.getName());
        return book;
    }

    @RequestMapping(value = "/bookTime", method = RequestMethod.POST, consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public String getBookTimePost(@RequestBody Book book) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("POST/bookTime " + book.getName());
        return "Hello";
    }

    @RequestMapping(value = "/xlsExample", method = RequestMethod.GET, consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public Form103XlsSheet getForm103Xls() {
        Form103Model form103Model = new Form103Model();
        return form103Model.getSheet();
    }

    @RequestMapping(value = "/xlsExample", method = RequestMethod.POST, consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public Form103XlsSheet getForm103Xls(@RequestBody Form103XlsSheet form103XlsSheet) {
        return form103XlsSheet;
    }

    @RequestMapping(value = "/generateForm103", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
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
