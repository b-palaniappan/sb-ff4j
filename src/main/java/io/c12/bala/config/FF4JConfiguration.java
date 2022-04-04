package io.c12.bala.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.lettuce.core.RedisClient;
import org.ff4j.FF4j;
import org.ff4j.cache.FF4JCacheManager;
import org.ff4j.cache.FF4jCacheManagerRedisLettuce;
import org.ff4j.mongo.store.EventRepositoryMongo;
import org.ff4j.mongo.store.FeatureStoreMongo;
import org.ff4j.mongo.store.PropertyStoreMongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FF4JConfiguration {

    // https://github.com/ff4j/ff4j-samples/blob/master/snippets/ff4j-sample-mongodb/src/test/java/org/ff4j/sample/mongo/MongoDbSampleUsage.java
    @Bean
    public FF4j getFF4j() {
        FF4j ff4j = new FF4j();

        MongoClient mongoClient = MongoClients.create();

        ff4j.audit(true);
        ff4j.setFeatureStore(new FeatureStoreMongo(mongoClient));
        ff4j.setPropertiesStore(new PropertyStoreMongo(mongoClient));
        ff4j.setEventRepository(new EventRepositoryMongo(mongoClient));

        // Using Jedis
//        RedisConnection redisConnection = new RedisConnection("localhost", 6379);
//        FF4JCacheManager ff4JCacheManager = new FF4jCacheManagerRedis(redisConnection);

        // Using Lettuce
        FF4JCacheManager ff4JCacheManager =new FF4jCacheManagerRedisLettuce(RedisClient.create("redis://localhost:6379"));

        ff4j.cache(ff4JCacheManager);

        return ff4j;
    }
}
