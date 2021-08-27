package com.polixis.task1.service;

import com.polixis.task1.service.dto.PersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<PersonDTO, PersonDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public PersonDTO process(PersonDTO person) throws Exception {

        LOGGER.info("processing person data.....{}", person);

        PersonDTO transformedPerson = new PersonDTO(person.getFirstName(), person.getLastName(), person.getDate());
        LOGGER.info("Converting ( {} ) into ( {} )", person, transformedPerson);

        return transformedPerson;
    }

}
