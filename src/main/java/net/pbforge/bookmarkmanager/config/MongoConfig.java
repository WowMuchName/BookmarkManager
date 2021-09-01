package net.pbforge.bookmarkmanager.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.MongoConfigurationSupport;

@Configuration
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MongoConfig extends MongoConfigurationSupport {
    @Override
    protected String getDatabaseName() {
        return "bookmark";
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
