package com.qsoft.demojwebsocket.server;


import org.apache.log4j.Logger;
import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.logging.Logging;
import org.jwebsocket.plugins.TokenPlugIn;
import org.jwebsocket.token.Token;

public class LauridsPlugIn extends TokenPlugIn
{

    //change the Apache logger to your Classname
    private static Logger mLog = Logging.getLogger(LauridsPlugIn.class);
    // if you change the namespace, don't forget to change the ns_sample!
    private final static String NS_SAMPLE = "com.lauridmeyer.tests.LauridsPlugIn";

    //Constructor
    public LauridsPlugIn(PluginConfiguration aConfiguration)
    {
        super(aConfiguration);
        if (mLog.isDebugEnabled())
        {
            mLog.debug("Instantiating Laurid's PlugIn ...");
        }
        // specify your namespace
        this.setNamespace(NS_SAMPLE);
    }

    //Method is called when a token has to be progresed
    @Override
    public void processToken(PlugInResponse aResponse, WebSocketConnector aConnector, Token aToken)
    {
        // get the type of the token
        // the type can be associated with a "command"
        String lType = aToken.getType();

        // get the namespace of the token
        // each plug-in should have its own unique namespace
        String lNS = aToken.getNS();

        // check if token has a type and a matching namespace
        if (lType != null && lNS != null && lNS.equals(getNamespace()))
        {
            if (lType.equals("getAuthorName"))
            {//if the request is "getAuthorName"
                mLog.debug("Authorname was requested");
                Token lResponse = createResponse(aToken);//create the response
                //add the variable "name" with the value "Laurid Meyer" to the response
                lResponse.setString("name", "Laurid Meyer");
                sendToken(aConnector, aConnector, lResponse);//send the response
            }
            else if (lType.equals("calculate"))
            {//if the request is "calculate"
                int square = aToken.getInteger("myNumber");//get the Value of the variable "myNumber"
                mLog.debug("trying to calculate:" + square);
                //square *= square;// square the input
//                NumberService numberService = BaseService.getNumberService();
//
//                NumberDTO numberDTO = numberService.findNumberById(0l+ square);


                Token lResponse = createResponse(aToken);//create the response
                //add the variable "calNumber" with the value -square- to the response
//                lResponse.setDouble("calNumber", numberDTO.getData());
                sendToken(aConnector, aConnector, lResponse);//send the response
            }
        }
    }
}