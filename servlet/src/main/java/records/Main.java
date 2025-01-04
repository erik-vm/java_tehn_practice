package records;

public class Main {

    public static void main(String[] args) {

        Task t1 = new Task(null, "Task 1", null, false, null);

        // Copy from task t1 with id value
        Task t2 = t1.withId(3L).withCompleted(true);


        Task t3 = Task.builder()
                .id(4L)
                .name("Task 3")
                .build();

        System.out.println(t1);

    }
}
