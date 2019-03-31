package druid.wall;

import com.alibaba.druid.wall.WallCheckResult;
import com.alibaba.druid.wall.spi.MySqlWallProvider;

public class Test {
    public static void main(String[] args) {
        MySqlWallProvider provider = new MySqlWallProvider();
        WallCheckResult check = provider.check("select * from student;delete from student");
//        check.getFunctionStats().forEach((key, value) -> {
//            System.out.println(key + " : " + value.getInvokeCount());
//        });
        System.out.println(check.getSql());
    }
}

