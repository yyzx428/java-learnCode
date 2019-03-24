package spring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class Test {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\class");
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register( AppComponent.class,AppConfig.class);
        context.refresh();
        ClientServiceImpl clientService1 = context.getBean("clientService1", ClientServiceImpl.class);
        ClientServiceImpl clientService2 = context.getBean("clientService2", ClientServiceImpl.class);
        ClientServiceImpl clientService3 = context.getBean("clientService3", ClientServiceImpl.class);
        ClientServiceImpl clientService4 = context.getBean("clientService4", ClientServiceImpl.class);
        System.out.println(clientService1.getClientDao().equals(clientService2.getClientDao()));
        System.out.println(clientService3.getClientDao().equals(clientService4.getClientDao()));
    }

    interface ClientService {

    }

    interface ClientDao {

    }

    @Configuration
    public static class AppConfig {

        @Bean
        public ClientService clientService1() {
            ClientServiceImpl clientService = new ClientServiceImpl();
            clientService.setClientDao(clientDao());
            return clientService;
        }

        @Bean
        public ClientService clientService2() {
            ClientServiceImpl clientService = new ClientServiceImpl();
            clientService.setClientDao(clientDao());
            return clientService;
        }

        @Bean
        public ClientDao clientDao() {
            return new ClientDaoImpl("AppConfig");
        }
    }

    @Component
    public static class AppComponent {

        @Bean
        public ClientService clientService3() {
            ClientServiceImpl clientService = new ClientServiceImpl();
            clientService.setClientDao(clientDao());
            return clientService;
        }

        @Bean
        public ClientService clientService4() {
            ClientServiceImpl clientService = new ClientServiceImpl();
            clientService.setClientDao(clientDao());
            return clientService;
        }

        @Bean
        public ClientDao clientDao() {
            return new ClientDaoImpl("AppComponent");
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    private static class ClientDaoImpl implements ClientDao {
        private String index;


    }

    @Setter
    @Getter
    private static class ClientServiceImpl implements ClientService {
        ClientDao clientDao;
    }
}
