package Model;

public class Question {
    private long question_id;
    private long exam_id;
    private int level;
    private String content;

    public Question() {
    }

    public Question(long exam_id, int level, String content) {
        this.exam_id = exam_id;
        this.level = level;
        this.content = content;
    }

    public Question(long question_id, long exam_id, int level, String content) {
        this.question_id = question_id;
        this.exam_id = exam_id;
        this.level = level;
        this.content = content;
    }

    public long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }

    public long getExam_id() {
        return exam_id;
    }

    public void setExam_id(long exam_id) {
        this.exam_id = exam_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question_id=" + question_id +
                ", exam_id=" + exam_id +
                ", level=" + level +
                ", content='" + content + '\'' +
                '}';
    }
}
