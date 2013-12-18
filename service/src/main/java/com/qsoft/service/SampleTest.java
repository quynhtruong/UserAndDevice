package com.qsoft.service;

import com.qsoft.model.dto.ToDoListDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: quynhtq
 * Date: 12/5/13
 */
public class SampleTest
{
    @Autowired
    ToDoListService toDoListService;
    @Autowired
    UserService userService;

    public void testGetToDOList()
    {
//        ToDoListDTO toDoListDTO = createToDoListDTO();
//        toDoListService.getToDoList(toDoListDTO);
    }

    public ToDoListDTO createToDoListDTO()
    {
        ToDoListDTO toDoListDTO = new ToDoListDTO();
        toDoListDTO.setUserId(5L);
        return toDoListDTO;
    }
}
