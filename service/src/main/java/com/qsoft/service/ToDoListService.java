package com.qsoft.service;

import com.qsoft.model.common.ToDoList;
import com.qsoft.model.dto.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * User: quynhtq
 * Date: 12/4/13
 */
public interface ToDoListService
{
    public ToDoListResponseDTO addAndUpdateToDoList(ToDoListDTO toDoListDTO);

    public ToDoListResponseDTO removeToDoList(ToDoListDTO toDoListDTO);

    public List<ToDoList> getToDoList(Long userId, Date latestTime);

}
