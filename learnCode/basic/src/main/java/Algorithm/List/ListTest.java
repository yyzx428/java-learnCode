package Algorithm.List;

public class ListTest {

    public static void main(String[] args) {
        // 运行链表反转
        // runReverse();

        // 运行链表合并
        runListMerge();
    }

    private static void runListMerge() {
        Entity head = new Entity(1);
        Entity current = head;
        for (int i = 3; i <= 10; i += 2) {
            current.next = new Entity(i);
            current = current.next;
        }
        current = head;
        System.out.print("firstList:");
        while (current.next != null) {
            System.out.print(current.value + ",");
            current = current.next;
        }
        System.out.println(current.value);

        Entity otherHead = new Entity(2);
        current = otherHead;
        for (int i = 4; i <= 10; i += 2) {
            current.next = new Entity(i);
            current = current.next;
        }

        current = otherHead;
        System.out.print("secondList:");
        while (current.next != null) {
            System.out.print(current.value + ",");
            current = current.next;
        }
        System.out.println(current.value);
        current = OrderedMerge(head, otherHead);

        while (current.next != null) {
            System.out.print(current.value + ",");
            current = current.next;
        }
        System.out.println(current.value);
    }

    public static Entity reverse(Entity head) {
        if (head == null || head.next == null) {
            return head;
        }

        Entity current = head.next;
        Entity pre = head;
        head.next = null;
        Entity newHead = null;
        while (current != null) {
            Entity next = current.next;
            current.next = pre;
            pre = current;
            if (next == null) {
                newHead = current;
            }
            current = next;
        }
        return newHead;
    }

    public static Entity OrderedMerge(Entity head, Entity otherHead) {

        if (head == null || otherHead == null) {
            if (head != null) {
                return head;
            } else if (otherHead != null) {
                return otherHead;
            } else {
                return null;
            }
        }

        Entity newHead;

        if (head.value < otherHead.value) {
            newHead = head;
            head = head.next;
        } else {
            newHead = otherHead;
            otherHead = otherHead.next;
        }

        Entity current = newHead;

        while (head != null && otherHead != null) {
            if (head.value <= otherHead.value) {
                current.next = head;
                head = head.next;
            } else {
                current.next = otherHead;
                otherHead = otherHead.next;
            }
            current = current.next;
        }

        while (head != null) {
            current.next = head;
            current = current.next;
            head = head.next;
        }

        while (otherHead != null) {
            current.next = otherHead;
            current = current.next;
            otherHead = otherHead.next;
        }

        return newHead;
    }

    private static void runReverse() {
        Entity head = new Entity(1);
        Entity current = head;
        for (int i = 2; i <= 10; i++) {
            current.next = new Entity(i);
            current = current.next;
        }
        current = reverse(head);
        while (current.next != null) {
            System.out.println(current.value);
            current = current.next;
        }
        System.out.println(current.value);
    }

    static class Entity {
        public int value;
        public Entity next;

        public Entity(int value) {
            this.value = value;
        }
    }
}
