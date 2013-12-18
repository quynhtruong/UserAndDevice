package com.qsoft.demojwebsocket.middletierservice;

import com.caucho.hessian.client.HessianProxyFactory;
import com.qsoft.service.ToDoListService;
import com.qsoft.service.UserService;

import java.net.MalformedURLException;

/**
 * User: luult
 * Date: 12/11/13
 * Time: 2:43 PM
 */
public class BaseService
{

    private static UserService userService;
    private static ToDoListService todoListService;

    private static String userServicesURL = "http://localhost:8181/userService";
    private static String toDoListServicesURL = "http://localhost:8181/toDoListService";

    public static UserService getUserService()
    {
        if (null == userService)
        {
            HessianProxyFactory factory = new HessianProxyFactory();
            try
            {
                userService = (UserService) factory.create(UserService.class, userServicesURL);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return null;
            }
        }
        return userService;
    }

    public static ToDoListService getToDoListService()
    {
        if (null == todoListService)
        {
            HessianProxyFactory factory = new HessianProxyFactory();
            try
            {
                todoListService = (ToDoListService) factory.create(ToDoListService.class, toDoListServicesURL);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return null;
            }
        }
        return todoListService;
    }
}
