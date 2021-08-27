package com.polixis.task1.service.job;

import com.polixis.task1.service.PersonService;
import com.polixis.task1.service.dto.PersonDTO;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceItemWriter<T> implements ItemWriter<T> {

    @Autowired
    PersonService personService;

    @Override
    public void write(List<? extends T> items) throws Exception {
        for (T item : items) {
            PersonDTO personDTO = (PersonDTO) item;
            personService.save(personDTO);
        }
    }
}
