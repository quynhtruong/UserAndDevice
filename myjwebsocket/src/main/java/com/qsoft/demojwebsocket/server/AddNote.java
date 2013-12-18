package com.qsoft.demojwebsocket.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qsoft.demojwebsocket.middletierservice.BaseService;
import com.qsoft.model.common.ToDoList;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: luult
 * Date: 12/12/13
 * Time: 9:27 AM
 */
public class AddNote extends TokenPlugIn
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
                List<ToDoListDTO> responseForClient = addNoteToServer(aToken);

                Token lResponse = createResponse(aToken);
                Gson gson = new Gson();
                String result = gson.toJson(responseForClient);
                lResponse.setString("msg", result);

                lResponse.setString("reqType", "updateFromOther");
                broadcastToken(aConnector, lResponse);
                lResponse.setString("reqType", "responseFromSever");
                sendToken(aConnector, aConnector, lResponse);
            }
            else if (lType.equals("requestGetLatest"))
            {
                System.out.println("b1 requestGetLatest");
                Token lResponse = createResponse(aToken);

                List<ToDoList> responseForClient = getLatestUpdate(aToken);
                lResponse.setLong("latestUpdated", (new Date()).getTime());

                Gson gson = new Gson();
                String result = gson.toJson(responseForClient);
                lResponse.setString("msg", result);
                lResponse.setString("reqType", "responseGetLatest");
                sendToken(aConnector, aConnector, lResponse);
            }
            System.out.println("b4");
        }
    }

    private List<ToDoList> getLatestUpdate(Token aToken)
    {
        System.out.println("b2");
        String latestUpdatedString = aToken.getString("latestUpdated");
        System.out.println(latestUpdatedString);
        Date latestDate;
        System.out.println("b3 "  );
        if (latestUpdatedString == null ||latestUpdatedString.equals("null"))
        {
            System.out.println("b4");
            latestDate = new Date(0);
        }
        else
        {
            System.out.println("b5");
            latestDate = new Date(Long.parseLong(latestUpdatedString));
        }
        System.out.println("b6");
        System.out.println("time " + latestDate.toGMTString());

        ToDoListService toDoListService = BaseService.getToDoListService();
        List<ToDoList> toDoListResponseDTO = toDoListService.getToDoList(5l, latestDate);
        return toDoListResponseDTO;
    }

    private List<ToDoListDTO> addNoteToServer(Token aToken)
    {
        String msg = aToken.getString("msg");
        System.out.println(msg);
        Gson gson = new Gson();
        Type type = new TypeToken<List<ToDoListDTO>>()
        {
        }.getType();

        List<ToDoListDTO> listDTOList = gson.fromJson(msg, type);
        ToDoListService toDoListService = BaseService.getToDoListService();

        List<ToDoListDTO> result = new ArrayList<ToDoListDTO>();

        for (ToDoListDTO toDoListDTO : listDTOList)
        {
            System.out.println("abc");
            System.out.println(toDoListDTO.getWebId());
            System.out.println(toDoListDTO.getDescription());
            toDoListDTO.setUserId(5l);
            ToDoListResponseDTO toDoListResponseDTO = toDoListService.addAndUpdateToDoList(toDoListDTO);
            System.out.println(toDoListResponseDTO.getToDoListDTO().getDescription());
            result.add(toDoListResponseDTO.getToDoListDTO());
        }
        System.out.println(listDTOList.size());
        System.out.println(listDTOList);
        System.out.println(msg);
        return result;
    }
}
