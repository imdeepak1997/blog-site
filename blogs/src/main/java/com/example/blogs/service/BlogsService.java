package com.example.blogs.service;

import com.example.blogs.model.Blog;
import com.example.blogs.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogsService {
    @Autowired
    BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }
     public Blog saveBlog(Blog blog) {

         return blogRepository.save(blog);
     }
     public String validateBlog(Blog blog) {
         if (blog.getName() == null || blog.getName().isEmpty() || blog.getName().length()<=20) {
            return "Name length should be more than 20 characters";
         }
         if (blog.getArticle() == null || blog.getArticle().isEmpty() || blog.getArticle().length()<=100) {
             return "Article length should be more than 1000 characters";
         }
         if(blog.getCategory() == null || blog.getCategory().isEmpty() || blog.getCategory().length()<=20) {
             return "Category length should be more than 20 characters";
         }

         return "";
     }
}
