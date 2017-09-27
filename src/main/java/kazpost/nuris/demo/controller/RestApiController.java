package kazpost.nuris.demo.controller;


import kazpost.nuris.demo.model.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @RequestMapping(value = "/book/", method = RequestMethod.GET)
    public Book getBook() {
        Book book = new Book();
        book.setId(1236654789);
        return book;
    }

}
