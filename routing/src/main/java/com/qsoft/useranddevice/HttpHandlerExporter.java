package com.qsoft.useranddevice;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Simple HttpServlet that delegates to an {@link HttpRequestHandler} bean defined
 * in Spring's root web application context. The target bean name must match the
 * HttpRequestHandlerServlet servlet-name as defined in <code>web.xml</code>.
 *
 * <p>This can for example be used to expose a single Spring remote exporter,
 * such as {@link org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter}
 * or {@link org.springframework.remoting.caucho.HessianServiceExporter},
 * per HttpRequestHandlerServlet definition. This is a minimal alternative
 * to defining remote exporters as beans in a DispatcherServlet context
 * (with advanced mapping and interception facilities being available there).
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see org.springframework.web.HttpRequestHandler
 * @see org.springframework.web.servlet.DispatcherServlet
 */


/**
 * Created by IntelliJ IDEA.
 * User: wadeb
 * Date: 6/29/12
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpHandlerExporter extends HttpServlet
{
    private HttpRequestHandler target;

    public void setTarget(HttpRequestHandler target)
    {
        this.target = target;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        LocaleContextHolder.setLocale(request.getLocale());
        try
        {
            this.target.handleRequest(request, response);
        }
        catch (HttpRequestMethodNotSupportedException ex)
        {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, ex.getMessage());
        }
        finally
        {
            LocaleContextHolder.resetLocaleContext();
        }
    }
}
