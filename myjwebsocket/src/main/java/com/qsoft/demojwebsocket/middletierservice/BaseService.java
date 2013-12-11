package com.qsoft.demojwebsocket.middletierservice;

import com.caucho.hessian.client.HessianProxyFactory;
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

    private static String webServicesURL = "http://localhost:8181/userAndDevice";

    public static UserService getUserService ()
    {
        if (null == userService)
        {
            HessianProxyFactory factory = new HessianProxyFactory();
            try
            {
                userService = (UserService) factory.create(UserService.class, webServicesURL);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return null;
            }
        }
        return userService;
    }
}
