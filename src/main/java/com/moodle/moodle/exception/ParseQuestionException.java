package com.moodle.moodle.exception;

import lombok.Getter;

/**
 * Эксепшен парсинга
 */
@Getter
public class ParseQuestionException extends CommonException {

    public ParseQuestionException(int sizeParsed, String rowError, String lastQuestionName, Exception e) {
        super("Всего распарсил = "+ sizeParsed + ", последний вопрос = '"+  lastQuestionName + "', упал на строке= '" + rowError + "'", e);
    }

}
