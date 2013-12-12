package com.qsoft.service.impl;

import com.qsoft.constant.Constants;
import com.qsoft.dao.DeviceDAO;
import com.qsoft.dao.ToDoListDAO;
import com.qsoft.dao.UserDAO;
import com.qsoft.model.dto.*;
import com.qsoft.model.common.ToDoList;
import com.qsoft.model.common.Users;
import com.qsoft.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * User: quynhtq
 * Date: 12/4/13
 */
@Component
@Transactional
public class ToDoListServiceImpl implements ToDoListService
{
    @Autowired
    UserDAO userDAO;
    @Autowired
    DeviceDAO deviceDAO;
    @Autowired
    ToDoListDAO toDoListDAO;

    public ToDoListResponseDTO addAndUpdateToDoList(ToDoListDTO toDoListDTO)
    {
        ToDoListResponseDTO responseDTO;
        Users users = userDAO.findOne(toDoListDTO.getUserId());
        if (users == null)
        {
            responseDTO = createResponse(Constants.FAIL, Constants.USER_DOES_NOT_EXIST);
            return responseDTO;
        }
        Long toDoListId = toDoListDTO.getWebId();
        if (toDoListId == null)
        {
            ToDoList toDoList = new ToDoList();
            toDoList.setDeleted(false);
            toDoList.setDescription(toDoListDTO.getDescription());
            toDoList.setUsers(users);
            toDoListDAO.save(toDoList);
            responseDTO = createResponse(Constants.SUCCESS, Constants.SUCCESS);
            responseDTO.getToDoListDTOs().add(new ToDoListDTO(toDoList));
        }
        else
        {
            ToDoList toDoList = toDoListDAO.findOne(toDoListId);
            if (toDoList == null)
            {
                responseDTO = createResponse(Constants.FAIL, Constants.TO_DO_LIST_DOES_NOT_EXIST);
            }
            else
            {
                toDoList.setDescription(toDoListDTO.getDescription());
                toDoListDAO.save(toDoList);
                responseDTO = createResponse(Constants.SUCCESS, Constants.SUCCESS);
                responseDTO.getToDoListDTOs().add(new ToDoListDTO(toDoList));
            }
        }
        return responseDTO;
    }

    public ToDoListResponseDTO removeToDoList(ToDoListDTO toDoListDTO)
    {
        ToDoListResponseDTO toDoListResponseDTO;
        Long toDoListId = toDoListDTO.getWebId();
        if (toDoListId == null)
        {
            toDoListResponseDTO = createResponse(Constants.FAIL, Constants.TO_DO_LIST_DOES_NOT_EXIST);
            return toDoListResponseDTO;
        }
        ToDoList toDoList = toDoListDAO.findOne(toDoListId);
        if (toDoList == null)
        {
            toDoListResponseDTO = createResponse(Constants.FAIL, Constants.TO_DO_LIST_DOES_NOT_EXIST);
            return toDoListResponseDTO;
        }
        else
        {
            toDoList.setDeleted(true);
            toDoListDAO.save(toDoList);
            toDoListResponseDTO = createResponse(Constants.SUCCESS, Constants.SUCCESS);
        }
        return toDoListResponseDTO;
    }

    public ToDoListResponseDTO getToDoList(ToDoListDTO toDoListDTO)
    {
        ToDoListResponseDTO toDoListResponseDTO;
        Long userId = toDoListDTO.getUserId();
        if (userId == null)
        {
            toDoListResponseDTO = createResponse(Constants.FAIL, Constants.USER_DOES_NOT_EXIST);
            return toDoListResponseDTO;
        }
        Users users = userDAO.findOne(userId);
        if (users == null)
        {
            toDoListResponseDTO = createResponse(Constants.FAIL, Constants.USER_DOES_NOT_EXIST);
            return toDoListResponseDTO;
        }
        List<ToDoList> toDoLists = toDoListDAO.findByUsers(users);
        toDoListResponseDTO = createResponse(Constants.SUCCESS, Constants.SUCCESS);
        for (ToDoList toDoList : toDoLists)
        {
            toDoListResponseDTO.getToDoListDTOs().add(new ToDoListDTO(toDoList));
        }
        return toDoListResponseDTO;
    }

    public ToDoListResponseDTO createResponse(String result, String message)
    {
        ToDoListResponseDTO toDoListResponseDTO = new ToDoListResponseDTO();
        toDoListResponseDTO.setResult(result);
        toDoListResponseDTO.setMessage(message);
        return toDoListResponseDTO;
    }
}
