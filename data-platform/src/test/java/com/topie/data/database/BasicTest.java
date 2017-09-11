package com.topie.data.database;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:config/spring.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class BasicTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private MongoClient mongoClient;

    @Test
    public void name() throws Exception {
        MongoDatabase database = mongoClient.getDatabase("dvd_user");
        MongoCollection<Document> coll = database.getCollection("d_user");
        List list = new ArrayList<>();
        Block<Document> block = document -> {
            list.add(document.toJson());
        };
        List<Bson> l = new ArrayList<>();
        Bson b = gte("user_base.age", 36);
        l.add(b);
        coll.find(and(l)).limit(10).forEach(block);
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
