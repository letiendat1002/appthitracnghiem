package Model;

public class EnrollmentAnswer {
    private long enrollment_answer_id;
    private long enrollment_id;
    private long question_id;
    private long question_answer_id;

    public EnrollmentAnswer() {
    }

    public EnrollmentAnswer(long enrollment_answer_id, long enrollment_id, long question_id, long question_answer_id) {
        this.enrollment_answer_id = enrollment_answer_id;
        this.enrollment_id = enrollment_id;
        this.question_id = question_id;
        this.question_answer_id = question_answer_id;
    }

    public long getEnrollment_answer_id() {
        return enrollment_answer_id;
    }

    public void setEnrollment_answer_id(long enrollment_answer_id) {
        this.enrollment_answer_id = enrollment_answer_id;
    }

    public long getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(long enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }

    public long getQuestion_answer_id() {
        return question_answer_id;
    }

    public void setQuestion_answer_id(long question_answer_id) {
        this.question_answer_id = question_answer_id;
    }

    @Override
    public String toString() {
        return "EnrollmentAnswer{" +
                "enrollment_answer_id=" + enrollment_answer_id +
                ", enrollment_id=" + enrollment_id +
                ", question_id=" + question_id +
                ", question_answer_id=" + question_answer_id +
                '}';
    }
}
