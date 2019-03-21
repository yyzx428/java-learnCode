package scanner.annotation.domain;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class CycleEntity implements InitializingBean, SmartInitializingSingleton {
    @Autowired
    private CycleEntity cycleEntity;
    private String name;

    public CycleEntity(String name) {
        this.name = name;
    }

    @PostConstruct
    public void after() {
        System.out.println("CycleEntity初始化完成----1");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("CycleEntity初始化完成----2");
    }

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("所有的Bean初始化完成-----3");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CycleEntity getCycleEntity() {
        return cycleEntity;
    }


}
