package ru.puchkova.gradwork;

public class Notes {
    private int id;
    private String title;
    private String description;
    private String deadline;
    private boolean check;

    public Notes(int id, String title, String description, String deadline, boolean check) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
