import java.util.*;

abstract class Question {
    String text;
    String answer;

    Question(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    abstract boolean checkAnswer(String ans);
}

class MCQ extends Question {
    MCQ(String text, String answer) {
        super(text, answer);
    }

    boolean checkAnswer(String ans) {
        return answer.equalsIgnoreCase(ans);
    }
}

class Attempt {
    Student student;
    Examination exam;
    Map<Question, String> answers = new LinkedHashMap<>();
    boolean submitted = false;

    Attempt(Student student, Examination exam) {
        this.student = student;
        this.exam = exam;
    }

    void answer(Question q, String ans) {
        if (submitted) {
            System.out.println("Attempt already submitted.");
            return;
        }
        answers.put(q, ans);
        System.out.println(q.text + " answered with '" + ans + "'.");
    }

    void submit() {
        if (submitted) {
            System.out.println("Attempt already submitted.");
            return;
        }

        submitted = true;
        int score = 0;

        System.out.println("Examination '" + exam.name
                + "' submitted successfully.");

        for (Question q : exam.questions) {
            if (answers.containsKey(q)
                    && q.checkAnswer(answers.get(q))) {
                score++;
            }
        }

        System.out.println("Result for '" + exam.name
                + "' attempt: " + score + "/"
                + exam.questions.size() + " correct");
    }
}

class Student {
    String name;
    Set<Examination> submittedExams = new HashSet<>();

    Student(String name) {
        this.name = name;
    }

    Attempt startExam(Examination exam) {
        if (submittedExams.contains(exam)) {
            System.out.println("Exam already submitted.");
            return null;
        }

        System.out.println("Examination '" + exam.name
                + "' started by " + name + ".");
        return new Attempt(this, exam);
    }
}

class Examination {
    String name;
    List<Question> questions = new ArrayList<>();

    Examination(String name) {
        this.name = name;
    }

    void addQuestion(Question q) {
        questions.add(q);
    }
}

public class week8ppQ1 {
    public static void main(String[] args) {
        Student s = new Student("Student");
        Examination e = new Examination("Math Quiz");

        Question q1 = new MCQ("Question 1", "A");
        Question q2 = new MCQ("Question 2", "B");

        e.addQuestion(q1);
        e.addQuestion(q2);

        Attempt a = s.startExam(e);

        if (a != null) {
            a.answer(q1, "A");
            a.answer(q2, "C");
            a.submit();
            s.submittedExams.add(e);
        }
    }
}