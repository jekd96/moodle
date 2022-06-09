package com.moodle.moodle.exception;

import com.moodle.moodle.model.question.Question;

/**
 * Эксепшен который не находит в вопросе ответов
 */
public class NoAnswerQuestionException extends CommonException {

    public NoAnswerQuestionException(Question question, Exception e) {
        super("У вопроса = '" + question.getName().getText() + "' 0 ответов", e);
    }

}
