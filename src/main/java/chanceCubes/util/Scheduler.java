package chanceCubes.util;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private static List<Task> tasks = new ArrayList<>();

    public static boolean scheduleTask(Task task) {
        return tasks.add(task);
    }

    public static boolean removeTask(Task task) {
        return tasks.remove(task);
    }

    public static void tickTasks() {
        int numTasks = tasks.size();

        for (int i = numTasks - 1; i >= 0; i--) {
            Task task = tasks.get(i);
            if (task.tickTask()) {
                task.callback();
                tasks.remove(i);
            }
        }
    }
}
