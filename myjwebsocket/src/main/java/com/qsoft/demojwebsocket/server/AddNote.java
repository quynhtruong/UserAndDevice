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
        System.out.println("starting process ...");
        System.out.println(aToken);
        if (lType != null && lNS != null && lNS.equals(getNamespace()))
        {
            if (lType.equals("getAuthorName"))
            {
                authorNameHandle(aConnector, aToken);
            }
            else if (lType.equals("addToDoList"))
            {
                addToDoListHandle(aConnector, aToken);
            }
            else if (lType.equals("updateToDoList"))
            {
                updateToDoListHandle(aConnector, aToken);
            }
            else if (lType.equals("deleteToDoList"))
            {
                System.out.println(aToken);
                deleteToDoListHandle(aConnector, aToken);
            }
            else if (lType.equals("requestGetLatest"))
            {
                requestGetLatestHandle(aConnector, aToken);
            }
        }
    }

    private void addToDoListHandle(WebSocketConnector aConnector, Token aToken)
    {
        System.out.println("b2 add ToDoList");
        List<ToDoListDTO> responseForClient = addNoteToServer(aToken);

        Token lResponse = createResponse(aToken);
        Gson gson = new Gson();
        String result = gson.toJson(responseForClient);
        lResponse.setString("msg", result);

        lResponse.setString("reqType", "responseAddFromOther");
        broadcastToken(aConnector, lResponse);
        lResponse.setString("reqType", "responseAddFromSever");
        sendToken(aConnector, aConnector, lResponse);
    }

    private void requestGetLatestHandle(WebSocketConnector aConnector, Token aToken)
    {
        System.out.println("b2 RequestGetLatest");
        Token lResponse = createResponse(aToken);

        List<ToDoList> responseForClient = getLatestUpdate(aToken);
        lResponse.setLong("latestUpdated", (new Date()).getTime());

        Gson gson = new Gson();
        String result = gson.toJson(responseForClient);
        lResponse.setString("msg", result);
        lResponse.setString("reqType", "responseGetLatest");
        sendToken(aConnector, aConnector, lResponse);
    }

    private void updateToDoListHandle(WebSocketConnector aConnector, Token aToken)
    {
        System.out.println("b2 Update AToDoList");
        List<ToDoListDTO> responseForClient = addNoteToServer(aToken);

        Token lResponse = createResponse(aToken);
        Gson gson = new Gson();
        String result = gson.toJson(responseForClient);
        lResponse.setString("msg", result);

        lResponse.setString("reqType", "responseUpdateFromOther");
        broadcastToken(aConnector, lResponse);
        lResponse.setString("reqType", "responseUpdateFromSever");
        sendToken(aConnector, aConnector, lResponse);
    }

    private void authorNameHandle(WebSocketConnector aConnector, Token aToken)
    {
        System.out.println("b2 Author name");
        Token lResponse = createResponse(aToken);//create the response
        lResponse.setString("name", "Laurid Meyer");
        sendToken(aConnector, aConnector, lResponse);//send the response
    }

    private List<ToDoList> getLatestUpdate(Token aToken)
    {
        String latestUpdatedString = aToken.getString("latestUpdated");
        System.out.println("latest time " + latestUpdatedString);
        Date latestDate;
        try
        {
            if (latestUpdatedString == null || latestUpdatedString.equals("null"))
            {
                System.out.println("b4");
                latestDate = new Date(0);
            }
            else
            {
                System.out.println("b5");
                latestDate = new Date(Long.parseLong(latestUpdatedString));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            latestDate = new Date(0);
        }

        System.out.println("time " + latestDate.toGMTString());

        ToDoListService toDoListService = BaseService.getToDoListService();
        List<ToDoList> toDoListResponseDTO = toDoListService.getToDoList(5l, latestDate);

        System.out.println("to do list size: " + toDoListResponseDTO.size());
        System.out.println(toDoListResponseDTO);
        System.out.println(toDoListResponseDTO);
        System.out.println(toDoListResponseDTO);

        return toDoListResponseDTO;
    }

    private List<ToDoListDTO> addNoteToServer(Token aToken)
    {
        System.out.println("adding note...");
        String msg = aToken.getString("msg");
        System.out.println("message: " + msg);
        Gson gson = new Gson();
        Type type = new TypeToken<List<ToDoListDTO>>()
        {
        }.getType();

        List<ToDoListDTO> listDTOList = gson.fromJson(msg, type);
        ToDoListService toDoListService = BaseService.getToDoListService();

        List<ToDoListDTO> result = new ArrayList<ToDoListDTO>();

        for (ToDoListDTO toDoListDTO : listDTOList)
        {
            System.out.println(toDoListDTO.getWebId());
            System.out.println(toDoListDTO.getDescription());
            toDoListDTO.setUserId(5l);
            ToDoListResponseDTO toDoListResponseDTO = toDoListService.addAndUpdateToDoList(toDoListDTO);
            System.out.println(toDoListResponseDTO.getToDoListDTO().getDescription());
            toDoListResponseDTO.getToDoListDTO().set_id(toDoListDTO.get_id());
            result.add(toDoListResponseDTO.getToDoListDTO());
        }
        System.out.println("list size " + listDTOList.size());
        System.out.println(listDTOList);
        return result;
    }

    private void deleteToDoListHandle(WebSocketConnector aConnector, Token aToken)
    {
        System.out.println("b2 delete AToDoList");
        System.out.println(aToken);
        System.out.println(aToken.getString("msg"));
        List<ToDoListDTO> responseForClient = deleteNote(aToken);

        Token lResponse = createResponse(aToken);
        Gson gson = new Gson();
        String result = gson.toJson(responseForClient);
        lResponse.setString("msg", result);

        lResponse.setString("reqType", "responseDeleteFromOther");
        broadcastToken(aConnector, lResponse);
        lResponse.setString("reqType", "responseDeleteFromSever");
        sendToken(aConnector, aConnector, lResponse);
    }

    private List<ToDoListDTO> deleteNote(Token aToken)
    {
        System.out.println("deleting note...");
        String msg = aToken.getString("msg");
        System.out.println("message: " + msg);
        Gson gson = new Gson();
        Type type = new TypeToken<List<ToDoListDTO>>()
        {
        }.getType();

        List<ToDoListDTO> listDTOList = gson.fromJson(msg, type);
        ToDoListService toDoListService = BaseService.getToDoListService();

        List<ToDoListDTO> result = new ArrayList<ToDoListDTO>();

        for (ToDoListDTO toDoListDTO : listDTOList)
        {
            System.out.println(toDoListDTO.getWebId());
            System.out.println(toDoListDTO.getDescription());
            toDoListDTO.setUserId(5l);
            ToDoListResponseDTO toDoListResponseDTO = toDoListService.removeToDoList(toDoListDTO);
            toDoListDTO.setDeleted(true);
            result.add(toDoListDTO);
        }
        System.out.println("list size " + listDTOList.size());
        System.out.println(listDTOList);
        return result;
    }

}
