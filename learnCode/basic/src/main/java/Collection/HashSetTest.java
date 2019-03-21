package Collection;

import java.beans.Introspector;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class HashSetTest {

    public static void main(String[] args) {
        List<Student> lists = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        lists.add(new Student("1", now));
        lists.add(new Student("1", now));
        System.out.println(lists.toString());
        System.out.println(new HashSet<>(lists).toString());

        System.out.println(Introspector.decapitalize("ASsdsasdadsAA"));
    }

    static class Student {
        private String id;
        private LocalDateTime now;

        public Student(String id, LocalDateTime now) {
            this.id = id;
            this.now = now;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public LocalDateTime getNow() {
            return now;
        }

        public void setNow(LocalDateTime now) {
            this.now = now;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Student)) return false;
            Student student = (Student) o;
            return Objects.equals(id, student.id) &&
                    Objects.equals(now, student.now);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, now);
        }
    }
}
