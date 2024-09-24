package com.example.blogs.controller;

import com.example.blogs.model.Blog;
import com.example.blogs.repository.SearchRepository;
import com.example.blogs.service.BlogsService;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class BlogsController {

    private static final Logger log = LoggerFactory.getLogger(BlogsController.class);
    @Autowired
    BlogsService blogsService;

    @Autowired
    SearchRepository srepo;


    @GetMapping("/blogs/getAll")
    public ResponseEntity<List<Blog>> getAll() {
        return new ResponseEntity<>(blogsService.getAllBlogs(), HttpStatus.OK);
    }

    @GetMapping("/blogs/info/{category}")
    public  ResponseEntity<List<Blog>>  search(@PathVariable String category)
    {
        return new ResponseEntity<>(srepo.findByCategory(category,"category"), HttpStatus.OK);
    }

    @GetMapping("/blogs/info/author/{author}")
    public  ResponseEntity<List<Blog>>  searchBlogByAuthor(@PathVariable String author)
    {
        return new ResponseEntity<>(srepo.findByCategory(author,"author"), HttpStatus.OK);
    }

    @PostMapping("/blogs")
    public ResponseEntity<String>  create(@RequestBody Blog blog) {
        blog.setCreationDate(new Date());
        String error = blogsService.validateBlog(blog);
        if(!error.isEmpty()){
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        blogsService.saveBlog(blog);
        return new ResponseEntity<>("blogs saved successfully", HttpStatus.OK);
    }

    @GetMapping("/blogs/get/{category}/{fromDate}/{toDate}")
    public ResponseEntity<Object> searchByCategoryAndTimeRange(@PathVariable String category, @PathVariable String fromDate, @PathVariable String toDate) throws ParseException {
        if(category.isEmpty()){
            return ResponseEntity.unprocessableEntity().build();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fDate,tDate;
        try{
            fDate = formatter.parse(fromDate);
            tDate = formatter.parse(toDate);
        }catch (ParseException e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        List<Blog> blogs = srepo.findByCategoryAndTimeRange(category, fDate,tDate);
        return ResponseEntity.status(200).body(blogs);
    }
}
