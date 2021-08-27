package com.polixis.task1.service.mapper;


import com.polixis.task1.domain.*;
import com.polixis.task1.service.dto.PersonDTO;

import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {

    @Mapping(target = "id", ignore = true)
    @Override
    Person toEntity(PersonDTO dto);

    @Override
    PersonDTO toDto(Person entity);


    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
