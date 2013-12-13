import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qsoft.demojwebsocket.middletierservice.BaseService;
import com.qsoft.model.dto.RequestLatestDTO;
import com.qsoft.model.dto.ToDoListDTO;
import com.qsoft.model.dto.UserResponseDTO;
import com.qsoft.service.ToDoListService;
import com.qsoft.service.UserService;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * User: luult
 * Date: 12/11/13
 * Time: 5:13 PM
 */
public class Test
{
    public static void main(String[] args)
    {
        Date latestDate = new Date();
        Long userId = 5l;
        ToDoListDTO toDoListDTO = new ToDoListDTO();
        toDoListDTO.setUserId(5l);
        RequestLatestDTO requestLatestDTO = new RequestLatestDTO();
        requestLatestDTO.setUserId(5l);
        requestLatestDTO.setLatestTime(new Date());
        ToDoListService toDoListService = BaseService.getToDoListService();
        System.out.println(toDoListService.getToDoList(userId,latestDate));

    }
}
