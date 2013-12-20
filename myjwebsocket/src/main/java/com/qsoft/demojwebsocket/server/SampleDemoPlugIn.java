package com.qsoft.demojwebsocket.server;

import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.plugins.TokenPlugIn;
import org.jwebsocket.token.Token;

/**
 * User: luult
 * Date: 12/20/13
 * Time: 11:31 AM
 */
public class SampleDemoPlugIn extends TokenPlugIn
{
    private final static String NS_SAMPLE = "jwebsocket";

    //Constructor
    public SampleDemoPlugIn(PluginConfiguration aConfiguration)
    {
        super(aConfiguration);
        // specify your namespace
        this.setNamespace(NS_SAMPLE);
    }

    //Method is called when a token has to be progresed
    @Override
    public void processToken(PlugInResponse aResponse, WebSocketConnector aConnector, Token aToken)
    {
        System.out.println("Sample demo processing... ");
        System.out.println(aToken);

        Token lResponse = createResponse(aToken);//create the response
        lResponse.setString("reqType", "response");
        lResponse.setString("msg",  aToken.getString("msg"));
        sendToken(aConnector, aConnector, lResponse);//send the response
    }
}
