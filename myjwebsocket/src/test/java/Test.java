import com.qsoft.demojwebsocket.middletierservice.BaseService;
import com.qsoft.model.dto.ToDoListDTO;
import com.qsoft.service.ToDoListService;

/**
 * User: luult
 * Date: 12/11/13
 * Time: 5:13 PM
 */
public class Test
{
    public static void main(String[] args)
    {
//        Date latestDate = new Date();
//        Long userId = 5l;
//        ToDoListDTO toDoListDTO = new ToDoListDTO();
//        toDoListDTO.setUserId(5l);
//        RequestLatestDTO requestLatestDTO = new RequestLatestDTO();
//        requestLatestDTO.setUserId(5l);
//        requestLatestDTO.setLatestTime(new Date());
//        String s;
//        Format formatter;
//        Date date = new Date();
//
//// 2012-12-01
//        formatter = new SimpleDateFormat("yyyy-MM-dd ");
//        s = formatter.format(date);
//        System.out.println(date.getTime());
//        ToDoListService toDoListService = BaseService.getToDoListService();
//
//        System.out.println(latestDate.toGMTString());
//        System.out.println(toDoListService.getToDoList(userId,latestDate));
//
//        Date date1 = new Date(date.getTime());
//        System.out.println(date1.toString());
        ToDoListDTO toDoListDTO = new ToDoListDTO();
        toDoListDTO.setUserId(5l);
        toDoListDTO.setWebId(73l);
        ToDoListService toDoListService = BaseService.getToDoListService();
        System.out.println(toDoListService.removeToDoList(toDoListDTO).getToDoListDTO());

    }
}
