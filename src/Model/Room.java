package Model;

public class Room {
    private long room_id;
    private Long exam_id;
    private String title;
    private int time_limit;
    private String password;
    private boolean is_available;

    public Room() {
    }

    public Room(Long exam_id, String title, int time_limit, String password, boolean is_available) {
        this.exam_id = exam_id;
        this.title = title;
        this.time_limit = time_limit;
        this.password = password;
        this.is_available = is_available;
    }

    public Room(long room_id, Long exam_id, String title, int time_limit, String password, boolean is_available) {
        this.room_id = room_id;
        this.exam_id = exam_id;
        this.title = title;
        this.time_limit = time_limit;
        this.password = password;
        this.is_available = is_available;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public Long getExam_id() {
        return exam_id;
    }

    public void setExam_id(Long exam_id) {
        this.exam_id = exam_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(int time_limit) {
        this.time_limit = time_limit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAvailable() {
        return is_available;
    }

    public void setAvailable(boolean is_available) {
        this.is_available = is_available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "room_id=" + room_id +
                ", exam_id=" + exam_id +
                ", title='" + title + '\'' +
                ", time_limit=" + time_limit +
                ", password='" + password + '\'' +
                ", is_available=" + is_available +
                '}';
    }
}
