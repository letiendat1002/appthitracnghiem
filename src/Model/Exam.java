package Model;

public class Exam {
    private long exam_id;
    private String subject;
    private int total_question;
    private int total_score;
    private double score_per_question;

    public Exam() {
    }

    public Exam(String subject, int total_question, int total_score, double score_per_question) {
        this.subject = subject;
        this.total_question = total_question;
        this.total_score = total_score;
        this.score_per_question = score_per_question;
    }

    public Exam(long exam_id, String subject, int total_question, int total_score, double score_per_question) {
        this.exam_id = exam_id;
        this.subject = subject;
        this.total_question = total_question;
        this.total_score = total_score;
        this.score_per_question = score_per_question;
    }

    public long getExam_id() {
        return exam_id;
    }

    public void setExam_id(long exam_id) {
        this.exam_id = exam_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTotal_question() {
        return total_question;
    }

    public void setTotal_question(int total_question) {
        this.total_question = total_question;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public double getScore_per_question() {
        return score_per_question;
    }

    public void setScore_per_question(double score_per_question) {
        this.score_per_question = score_per_question;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "exam_id=" + exam_id +
                ", subject='" + subject + '\'' +
                ", total_question=" + total_question +
                ", total_score=" + total_score +
                ", score_per_question=" + score_per_question +
                '}';
    }
}
