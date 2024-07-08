package entity;public class Todo {
    public String  title;
    public final String id;

    public Todo(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public boolean isCompleted() {
        return false;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

}
