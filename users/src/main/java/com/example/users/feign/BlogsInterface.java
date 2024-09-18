package com.example.users.feign;

import com.example.users.model.Blogs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@FeignClient("BLOGS")
public interface BlogsInterface {

    @GetMapping("/blogs/getAll")
    public ResponseEntity<List<Blogs>> getAll();

    @GetMapping("/blogs/info/{category}")
    public  ResponseEntity<List<Blogs>>  search(@PathVariable String category);

    @GetMapping("/blogs/info/author/{author}")
    public  ResponseEntity<List<Blogs>>  searchBlogByAuthor(@PathVariable String author);

    @PostMapping("/blogs")
    public ResponseEntity<String>  create(@RequestBody Blogs blog);

    @GetMapping("/blogs/get/{category}/{fromDate}/{toDate}")
    public ResponseEntity<List<Blogs>> searchByCategoryAndTimeRange(@PathVariable String category, @PathVariable String fromDate, @PathVariable String toDate) throws ParseException;
}
