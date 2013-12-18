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
    private ToDoListDTO toDoListDTO;
    private List<ToDoListDTO> toDoListDTOs = new ArrayList<ToDoListDTO>();
    //constructor
    public ToDoListResponseDTO()
    {
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public ToDoListDTO getToDoListDTO()
    {
        return toDoListDTO;
    }

    public void setToDoListDTO(ToDoListDTO toDoListDTO)
    {
        this.toDoListDTO = toDoListDTO;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public List<ToDoListDTO> getToDoListDTOs()
    {
        return toDoListDTOs;
    }

    public void setToDoListDTOs(List<ToDoListDTO> toDoListDTOs)
    {
        this.toDoListDTOs = toDoListDTOs;
    }
}
