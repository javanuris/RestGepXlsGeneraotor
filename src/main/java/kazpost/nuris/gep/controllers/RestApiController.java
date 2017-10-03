package kazpost.nuris.gep.controllers;

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

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getTestGet() {
        System.out.println("GET/hello "+new Date());
        return "Hello";
    }

    @RequestMapping(value = "/hello/{id}", method = RequestMethod.POST)
    public String testPost(@RequestParam("name") String name, @PathVariable("id") String age) {
        System.out.println("POST/hello/{id} name:" + name +" age:" +age +"   "+new Date());
        return "My name is " + name + ". And my age: " + age;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String getTestPost() {
        System.out.println("POST/hello "+new Date());
        return "Hello";
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public Book getBookPost(@RequestBody Book book) {
        System.out.println("POST/book "+book.getName());
        return book;
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public Book getBookGET() {
        Book book = new Book();
        book.setName("Nuris");
        System.out.println("GET/book "+book.getName());
        return book;
    }


    @RequestMapping(value = "/bookTime", method = RequestMethod.POST)
    public String getBookTimePost(@RequestBody Book book) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("POST/bookTime "+book.getName());
        return "Hello";
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
