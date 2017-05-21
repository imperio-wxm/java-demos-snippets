import com.wxmimperio.Application;
import com.wxmimperio.mapper.UserMapper;
import com.wxmimperio.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Rollback
    public void findByName() {
        userMapper.insert("AAA", 20, "男");
        User u = userMapper.findByName("AAA");
        System.out.println(u.getName());
        System.out.println(u.getAge());
        System.out.println(u.getId());
        System.out.println(u.getGender());
        Assert.assertEquals(20, u.getAge().intValue());

        System.out.println(userMapper.findByName("测试姓名=1"));
    }

}
