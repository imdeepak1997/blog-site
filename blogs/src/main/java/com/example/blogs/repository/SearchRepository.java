package com.example.blogs.repository;

import com.example.blogs.model.Blog;

import java.util.Date;
import java.util.List;

public interface SearchRepository {

    List<Blog> findByCategory(String text,String column);

    List<Blog> findByCategoryAndTimeRange(String category, Date fromDate, Date toDate);
}