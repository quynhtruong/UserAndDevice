package com.qsoft.demojwebsocket.server;

import com.qsoft.demojwebsocket.middletierservice.BaseService;
import com.qsoft.model.dto.ToDoListDTO;
import com.qsoft.model.dto.ToDoListResponseDTO;
import com.qsoft.service.ToDoListService;
import org.apache.log4j.Logger;
import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.logging.Logging;
import org.jwebsocket.plugins.TokenPlugIn;
import org.jwebsocket.token.Token;
import com.google.gson.Gson;
/**
 * User: luult
 * Date: 12/12/13
 * Time: 9:27 AM
 */
public class AddNote  extends TokenPlugIn
{
    private static Logger mLog = Logging.getLogger(AddNote.class);
    // if you change the namespace, don't forget to change the ns_sample!
    private final static String NS_SAMPLE = "com.qsoft.demojwebsocket.addNote";

    //Constructor
    public AddNote(PluginConfiguration aConfiguration)
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
        String lType = aToken.getType();
        String lNS = aToken.getNS();

        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        System.out.println(lNS);
        System.out.println(lType);
        if (lType != null && lNS != null && lNS.equals(getNamespace()))
        {
            System.out.println("b1");
            if (lType.equals("getAuthorName"))
            {
                System.out.println("b2");
                System.out.println("Authorname was requested");
                Token lResponse = createResponse(aToken);//create the response
                lResponse.setString("name", "Laurid Meyer");
                sendToken(aConnector, aConnector, lResponse);//send the response
            }
            else if (lType.equals("addOrUpdateAToDoList"))
            {
                System.out.println("b3");
                ToDoListResponseDTO toDoListResponseDTO = addNoteToServer(aToken);
                System.out.println(toDoListResponseDTO.getToDoListDTOs());
                System.out.println(toDoListResponseDTO.getToDoListDTOs().size());

                Token lResponse = createResponse(aToken);
//                toDoListResponseDTO.getToDoListDTOs().
                Gson gson = new Gson();
                String result = gson.toJson(toDoListResponseDTO.getToDoListDTOs());
                lResponse.setString("msg",result);

                lResponse.setString("reqType","updateFromOther");
                sendToken(aConnector, aConnector, lResponse);
                broadcastToken(aConnector, lResponse);

            }
                System.out.println("b4");
        }
    }

    private ToDoListResponseDTO addNoteToServer(Token aToken)
    {
        String description = aToken.getString("description");
        ToDoListService toDoListService = BaseService.getToDoListService();

        ToDoListDTO toDoListDTO = new ToDoListDTO();
        toDoListDTO.setDescription(description);
        toDoListDTO.setWebId(null);
        toDoListDTO.setUserId(5l);

        return  toDoListService.addAndUpdateToDoList(toDoListDTO);
    }
}
