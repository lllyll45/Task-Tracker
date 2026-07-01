
import java.time.LocalDateTime;

public class Task {
    private int id;
    private String description;
    private Status status; 
    private String createdAt;
    private String updatedAt;

    public enum Status {
        TODO,
        IN_PROGRESS,
        DONE
    }

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = java.time.LocalDateTime.now().toString();
        this.updatedAt = java.time.LocalDateTime.now().toString();
    }

    public Task(int id, String description, String status, String createdAt, String updatedAt) {
        this.id = id;
        this.description = description;
        this.status = Status.valueOf(status.toUpperCase());
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescriprion){
        this.description = newDescriprion;
    }

    public String getStatus() {
        return status.name().toLowerCase();
    }
    public void setStatus(String status){
        this.status = Status.valueOf(status.toUpperCase());
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(){
        this.updatedAt =  java.time.LocalDateTime.now().toString();
    }

    


}
