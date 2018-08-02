package com.ikee.batch.dynamodb.infrastructure;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.ikee.batch.dynamodb.entity.Person;

/**
 * 
 * PersonItemProcessor
 * 
 * @author hujianfeng
 * @date 2018/07/12
 *
 */
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Person process(Person item) throws Exception {
        final String firstName = item.getFirstName();
        final String lastName = item.getLastName();

        final Person transformedPerson = new Person(firstName.toUpperCase(), lastName.toUpperCase());

        log.info("convert {} into {}", item, transformedPerson);

        return transformedPerson;
    }

}
