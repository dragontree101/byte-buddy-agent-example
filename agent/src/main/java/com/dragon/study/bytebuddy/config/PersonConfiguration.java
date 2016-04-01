package com.dragon.study.bytebuddy.config;

import com.dragon.study.bytebuddy.bean.Person;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dragon on 16/3/28.
 */
@Configuration
public class PersonConfiguration {

  @Bean
  public Person person() {
    Person person = new Person();
    person.setName("longzhe");
    person.setAge(29);
    return person;
  }

}
