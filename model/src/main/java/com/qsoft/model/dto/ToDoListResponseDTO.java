package com.qsoft.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: quynhtq
 * Date: 12/5/13
 */
public class ToDoListResponseDTO implements Serializable
{
    private String result;
    private String message;
    private List<ToDoListDTO> toDoListDTOs = new ArrayList<ToDoListDTO>();
    //constructor
    public ToDoListResponseDTO()
    {
    }

    //setter and getter
    public List<ToDoListDTO> getToDoListDTOs()
    {
        return toDoListDTOs;
    }

    public void setToDoListDTOs(List<ToDoListDTO> toDoListDTOs)
    {
        this.toDoListDTOs = toDoListDTOs;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
