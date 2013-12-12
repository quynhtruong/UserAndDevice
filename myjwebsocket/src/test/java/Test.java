import com.qsoft.demojwebsocket.middletierservice.BaseService;
import com.qsoft.model.dto.UserResponseDTO;
import com.qsoft.service.ToDoListService;
import com.qsoft.service.UserService;

/**
 * User: luult
 * Date: 12/11/13
 * Time: 5:13 PM
 */
public class Test
{
    public static void main(String[] args)
    {
        UserService userService = BaseService.getUserService();
        UserResponseDTO userResponseDTO = userService.getUser(5l);
        System.out.println(userResponseDTO.getUserDTO().getName());

        ToDoListService toDoListService = BaseService.getToDoListService();
    }
}
