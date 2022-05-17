package com.moodle.moodle.model;


import com.moodle.moodle.util.Utils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.function.BiFunction;
import java.util.regex.Pattern;

public enum PatternQuestions {

    POINT(Utils::patternQuestionsPoint, ".", "Точка", "^[0-9]+\\.|^[a-z]+\\.|^[A-Z]+\\.|^[А-Я]+\\.|^[а-я]+\\."),
    BRACKET(Utils::patternQuestionsBracket, ")", "Скобка", "^[0-9]+\\)|^[a-z]+\\)|^[A-Z]+\\)|^[А-Я]+\\)|^[а-я]+\\)"),
    SEMICOLON(Utils::patternQuestionsSemicolon, ";", "Точка с запятой", "^[0-9]+\\;|^[a-z]+\\;|^[A-Z]+\\;|^[А-Я]+\\;|^[а-я]+\\;"),
    COLON(Utils::patternQuestionsColon, ":", "Двоеточие", "^[0-9]+\\:|^[a-z]+\\:|^[A-Z]+\\:|^[А-Я]+\\:|^[а-я]+\\:"),
    TEXT_QUESTION_RUS(Utils::patternQuestionTextRus, null, "Слово 'Вопрос'", "^Вопрос [0-9]+|^Вопрос[0-9]+|^вопрос [0-9]+|^вопрос[0-9]+"),
    TEXT_QUESTION_ENG(Utils::patternQuestionTextEng, null, "Слово 'Question'", "^Question [0-9]+|^Question[0-9]+|^question [0-9]+|^question[0-9]+" ),
    LIST_QUESTION_WORD(Utils::patternListQuestionWord, null, "Пронумерованный список в word", null),
    BOLD(Utils::patternQuestionsBold, null, "Bold", null),
    ITALIC(Utils::patternQuestionsItalic, null, "Курсив", null),
    UNDERLINE(Utils::patternQuestionsUnderline, null, "Подчеркивание", null),
    HIGHLIGHTING(Utils::patternQuestionsHighlighting, null, "Выделение", null);

    public final BiFunction<XWPFParagraph, Pattern, PatternQuestions> function;
    public final String value;
    public final String titleRus;
    public final Pattern pattern;

    PatternQuestions(BiFunction<XWPFParagraph, Pattern, PatternQuestions> function, String value, String titleRus, String pattern) {
        this.function = function;
        this.value = value;
        this.titleRus = titleRus;
        this.pattern = pattern != null ? Pattern.compile(pattern) : null;
    }

    @Override
    public String toString() {
        return titleRus;
    }
}
