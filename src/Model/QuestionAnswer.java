package Model;

public class QuestionAnswer {
    private long question_answer_id;
    private long question_id;
    private String content;
    private boolean is_correct;

    public QuestionAnswer() {
    }

    public QuestionAnswer(long question_id, String content, boolean is_correct) {
        this.question_id = question_id;
        this.content = content;
        this.is_correct = is_correct;
    }

    public QuestionAnswer(long question_answer_id, long question_id, String content, boolean is_correct) {
        this.question_answer_id = question_answer_id;
        this.question_id = question_id;
        this.content = content;
        this.is_correct = is_correct;
    }

    public long getQuestion_answer_id() {
        return question_answer_id;
    }

    public void setQuestion_answer_id(long question_answer_id) {
        this.question_answer_id = question_answer_id;
    }

    public long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return is_correct;
    }

    public void setCorrect(boolean is_correct) {
        this.is_correct = is_correct;
    }

    @Override
    public String toString() {
        return "QuestionAnswer{" +
                "question_answer_id=" + question_answer_id +
                ", question_id=" + question_id +
                ", content='" + content + '\'' +
                ", is_correct=" + is_correct +
                '}';
    }
}
