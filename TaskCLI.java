import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.nio.file.*;
import java.util.*; 

public class TaskCLI {
    private static final Map<String, CommandHandler> tasks = new HashMap<>();
    private static List<Task> taskList = new ArrayList<>();
    private static int nextId = 1;
    private static final String FILE_NAME = "task.txt";

    interface CommandHandler {
        void execute(String[] args);
    }

    public  static void main(String[] args){
         loadTasks(); 

        if (args.length==0){
            showHelp();
            return;
        }

        registerCommands();

        String command = args[0];
        CommandHandler handler = tasks.get(command);

        if (handler != null) {
            handler.execute(args);
        } else {
            System.out.println("Unknown command: " + command);
            showHelp();
        }

    }

    private static void registerCommands(){
        tasks.put("add",args->{
            if (args.length < 2) {
                System.out.println("Error: Please provide task description");
                return;
            }
            addTask(args[1]);
        });
        tasks.put("list", args -> {
            if (args.length == 1) {
                listAllTasks();
            } else {
                listTasksByStatus(args[1]);
            }
        });

        tasks.put("update", args -> {
            if (args.length < 3) {
                System.out.println("Error: Usage: update <id> <new description>");
                return;
            }
            int id = Integer.parseInt(args[1]);
            updateTask(id, args[2]);
        });

        tasks.put("delete", args -> {
            if (args.length < 2) {
                System.out.println("Error: Please provide task ID");
                return;
            }
            int id = Integer.parseInt(args[1]);
            deleteTask(id);
        });

        tasks.put("mark-in-progress", args -> {
            if (args.length < 2) {
                System.out.println("Error: Please provide task ID");
                return;
            }
            int id = Integer.parseInt(args[1]);
            markTaskStatus(id, "in-progress");
        });

        tasks.put("mark-done", args -> {
            if (args.length < 2) {
                System.out.println("Error: Please provide task ID");
                return;
            }
            int id = Integer.parseInt(args[1]);
            markTaskStatus(id, "done");
        });
    }

    private static void showHelp() {
        System.out.println("Usage: task-cli <command> [arguments]");
        System.out.println("Commands:");
        System.out.println("  add <description>         - Add a new task");
        System.out.println("  list                      - List all tasks");
        System.out.println("  list <status>            - List tasks by status (todo, done, in-progress)");
        System.out.println("  update <id> <description> - Update task description");
        System.out.println("  delete <id>              - Delete task");
        System.out.println("  mark-in-progress <id>    - Mark task as in-progress");
        System.out.println("  mark-done <id>           - Mark task as done");
    }


            
    private static void saveTask() {
        try {
            List<String> lines = new ArrayList<>();

            for (Task task : taskList) {
                String line = task.getId() + "|" +
                            task.getDescription() + "|" +
                            task.getStatus() + "|" +
                            task.getCreatedAt() + "|" +
                            task.getUpdatedAt();
                lines.add(line);
            }

            Files.write(Paths.get(FILE_NAME), lines);
            
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
    
    private static void loadTasks() {
        try{
            if (!Files.exists(Paths.get(FILE_NAME))){
                return;
            }

            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));

            taskList.clear();

            int maxId = 0; 

            for(String line:lines){
                String[] parts = line.split("\\|");
                if (parts.length == 5){
                    int id = Integer.parseInt(parts[0]);
                    String description = parts[1];
                    String status = parts[2];
                    String createdAt = parts[3];
                    String updatedAt = parts[4];

                    Task task = new Task(id, description, status, createdAt, updatedAt);
                    taskList.add(task);
                        
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }

            nextId = maxId + 1;

        }catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }


    private static void addTask(String description) { 
        Task task = new Task(nextId, description);
        taskList.add(task);
        nextId += 1;
        System.out.println("Task added successfully (ID:" + task.getId()+")");
        saveTask();

     }

    private static void listAllTasks() {
        System.out.println("Tasks:");
        if (taskList.isEmpty()){
            System.out.println("No tasks");
            return;
        }

        for(Task task:taskList){
            System.out.println(task.getId()+". "+task.getDescription()+" - "+task.getStatus()+" (created: "+task.getCreatedAt()+")");
        }
    }

    public static Task findById(int id){
        if (taskList.isEmpty()){
            System.out.println("No tasks");
            return null;
        }

        for (Task task:taskList){
            if (task.getId() == id){
                return task;
            }
        }

        System.out.println("No task with id = "+ id );
        return null;
    }

    private static void listTasksByStatus(String status) { 
        if (taskList.isEmpty()){
            System.out.println("No tasks");
            return;
        }
        System.out.println("Tasks ( " + status + " ):");
        for (Task task:taskList){
            if (task.getStatus().equalsIgnoreCase(status)){
                System.out.println(task.getId()+"."+task.getDescription()+"-"+task.getStatus()+"(created:"+task.getCreatedAt()+")");
            }
        }

    }

    private static void updateTask(int id, String description) {
        Task task = findById(id);
        if (task == null) { 
            return; 
        }

        task.setDescription(description);

        task.setUpdatedAt();
        saveTask();
        System.out.println("Task updated");
    }

    private static void deleteTask(int id) { 
        Task task = findById(id);
        if (task == null) { 
            return; 
        }

        taskList.remove(task);
        System.out.println("Task removed");

        saveTask();
    }

    private static void markTaskStatus(int id, String status) { 
        Task task = findById(id);

        if(task == null){
            return;
        }

        task.setStatus(status);

        try {
            task.setUpdatedAt();
            System.out.println("Status updated");
            saveTask();
        }catch(IllegalArgumentException e){
            System.out.println("Error: Invalid status. Use: todo, done, or in-progress");
        }
     }

}