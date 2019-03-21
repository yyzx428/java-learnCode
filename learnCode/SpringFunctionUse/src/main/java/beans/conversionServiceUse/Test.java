package beans.conversionServiceUse;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) throws NoSuchFieldException {
        DefaultFormattingConversionService service = new DefaultFormattingConversionService();
        Template template = new Template();
        template.setTime(LocalDate.now());
        String value = (String) service.convert(template.getTime(),
                new TypeDescriptor(Template.class.getDeclaredField("time")), TypeDescriptor.valueOf(String.class));
        System.out.println(value);
    }

    static class Template {
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate time;

        public LocalDate getTime() {
            return time;
        }

        public void setTime(LocalDate time) {
            this.time = time;
        }
    }
}
