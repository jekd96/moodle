package com.moodle.moodle.exception;

import com.moodle.moodle.model.question.Question;

public class NoAnswerQuestionException extends CommonException {

    public NoAnswerQuestionException(Question question, Exception e) {
        super("У вопроса = '" + question.getName().getText() + "' 0 ответов", e);
    }

}
