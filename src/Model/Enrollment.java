package Model;

public class Enrollment {
    private long enrollment_id;
    private String user_id;
    private long room_id;
    private double score;

    public Enrollment() {
    }

    public Enrollment(String user_id, long room_id, double score) {
        this.user_id = user_id;
        this.room_id = room_id;
        this.score = score;
    }

    public Enrollment(long enrollment_id, String user_id, long room_id, double score) {
        this.enrollment_id = enrollment_id;
        this.user_id = user_id;
        this.room_id = room_id;
        this.score = score;
    }

    public long getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(long enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollment_id=" + enrollment_id +
                ", user_id='" + user_id + '\'' +
                ", room_id=" + room_id +
                ", score=" + score +
                '}';
    }
}
