package com.example.blogs.repository;


import com.example.blogs.model.Blog;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
public class SearchRepositoryImpl implements SearchRepository{

    @Autowired
    MongoClient client;

    @Autowired
    MongoConverter converter;

    @Override
    public List<Blog> findByCategory(String text, String column) {

        final List<Blog> blogs = new ArrayList<>();

        MongoDatabase database = client.getDatabase("blogs");
        MongoCollection<Document> collection = database.getCollection("blogs_collection");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("text",
                                new Document("query", text)
                                        .append("path", Arrays.asList(column)))),
                new Document("$sort",
                        new Document("creationDate", 1L))));

        result.forEach(doc -> blogs.add(converter.read(Blog.class,doc)));

        return blogs;
    }

    @Override
    public List<Blog> findByCategoryAndTimeRange(String category,Date fromDate, Date toDate) {
        final List<Blog> blogs = new ArrayList<>();

        MongoDatabase database = client.getDatabase("blogs");
        MongoCollection<Document> collection = database.getCollection("blogs_collection");

        AggregateIterable<Document> result = collection.aggregate( Arrays.asList(new Document("$match",
                new Document("creationDate",new Document("$gte", fromDate)
                                .append("$lt",toDate))
                        .append("category", category))));


        result.forEach(doc -> blogs.add(converter.read(Blog.class,doc)));

        return blogs;
    }
}