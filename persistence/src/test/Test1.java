import com.qsoft.dao.ToDoListDAO;
import com.qsoft.dao.UserDAO;
import com.qsoft.model.common.ToDoList;
import com.qsoft.model.common.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.Date;
import java.util.List;

/**
 * User: luult
 * Date: 12/13/13
 * Time: 3:19 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/persistence-bundle-context.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class Test1
{
    @Autowired
    ToDoListDAO toDoListDAO;
    @Autowired
    UserDAO userDAO;

    @Test
    public void test1()
    {
        int xx;
        xx = 1;
        Users users = userDAO.findOne(5l);
        Date date = new Date();
        List<ToDoList> toDoList = toDoListDAO.findByUsersAndLastUpdatedGreaterThan(users, date );
        System.out.println(toDoList.size());
        System.out.println("hello");
    }

}
