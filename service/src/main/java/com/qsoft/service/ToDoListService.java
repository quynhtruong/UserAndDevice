package com.qsoft.service;

import com.qsoft.model.dto.*;

/**
 * User: quynhtq
 * Date: 12/4/13
 */
public interface ToDoListService
{
    public ToDoListResponseDTO addAndUpdateToDoList(ToDoListDTO toDoListDTO);

    public ToDoListResponseDTO removeToDoList(ToDoListDTO toDoListDTO);

    public ToDoListResponseDTO getToDoList(ToDoListDTO toDoListDTO);
}
