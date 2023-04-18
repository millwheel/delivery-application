package msa.rider.controller;

import msa.rider.DTO.Blog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Blog home(){
        Blog blog = new Blog();
        blog.setTitle("Server test");
        blog.setContent("Rider server is activated successfully");
        return blog;
    }
}
