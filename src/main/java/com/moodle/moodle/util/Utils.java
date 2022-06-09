package com.moodle.moodle.util;


import com.moodle.moodle.model.PatternQuestions;
import com.moodle.moodle.model.question.FormatTextValue;
import com.moodle.moodle.model.question.Question;
import com.moodle.moodle.model.question.TextValue;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.regex.Pattern;

/**
 * Вспомогательный класс, которые содержит вспомогательные методы для всего приложения
 */
public class Utils {


    /**
     * Метод определяет есть ли русское слово Вопрос
     * @param paragraph параграф
     * @param pattern нужно передать паттерн
     * @return Если есть слово Вопрос, вернет определенный тип
     */
    public static PatternQuestions patternQuestionTextRus(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.TEXT_QUESTION_RUS);
    }

    /**
     * Метод определяет есть ли английское слово Question
     * @param paragraph параграф
     * @param pattern нужно передать паттерн
     * @return Если есть слово Question, вернет определенный тип
     */
    public static PatternQuestions patternQuestionTextEng(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.TEXT_QUESTION_ENG);
    }


    /**
     * Метод определяет есть ли Пронумерованный список в word
     * @param paragraph параграф
     * @param pattern не нужно передавать
     * @return Если есть пронумерованный список, вернет определенный тип
     */
    public static PatternQuestions patternListQuestionWord(XWPFParagraph paragraph, Pattern pattern) {
        if (paragraph.getNumID() != null) {
            return PatternQuestions.LIST_QUESTION_WORD;
        }
        return null;
    }


    /**
     * Метод определяет есть ли скобка
     * @param paragraph параграф
     * @param pattern Нужно передать паттерн для скобка
     * @return Если есть скобка, то метод возвращает определенный тип, иначе вернет null
     */
    public static PatternQuestions patternQuestionsBracket(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.BRACKET);
    }

    /**
     * Метод определяет есть ли тире
     * @param paragraph параграф
     * @param pattern Нужно передать паттерн для тире
     * @return Если есть тире, то метод возвращает определенный тип, иначе вернет null
     */
    public static PatternQuestions patternQuestionsDash(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.DASH);
    }

    /**
     * Метод определяет есть ли точка
     * @param paragraph параграф
     * @param pattern Нужно передать паттерн для точки
     * @return Если есть точка, то метод возвращает определенный тип, иначе вернет null
     */
    public static PatternQuestions patternQuestionsPoint(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.POINT);
    }

    /**
     * Метод определяет есть ли точка с запятой
     * @param paragraph параграф
     * @param pattern Нужно передать паттерн для точки с запятой
     * @return Если есть точка с запятой, то метод возвращает определенный тип, иначе вернет null
     */
    public static PatternQuestions patternQuestionsSemicolon(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.SEMICOLON);
    }

    /**
     * Метод определяет есть ли двоеточие
     * @param paragraph параграф
     * @param pattern Нужно передать паттерн для двоеточия
     * @return Если есть двоеточие, то метод возвращает определенный тип, иначе вернет null
     */
    public static PatternQuestions patternQuestionsColon(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.COLON);
    }

    /**
     * Метод определяет есть ли жирный шрифт в параграфе
     * @param paragraph параграф
     * @param pattern поле не нужно передавать, из-за специфики языка пришлось оставить
     * @return Если есть жирный шрифт, то метод возвращает определенный тип, иначе вернет null
     */
    public static PatternQuestions patternQuestionsBold(XWPFParagraph paragraph, Pattern pattern) {
        return paragraph.getRuns().get(0).isBold() ? PatternQuestions.BOLD : null;
    }

    /**
     * Метод определяет есть ли курсив в параграфе
     * @param paragraph параграф
     * @param pattern поле не нужно передавать, из-за специфики языка пришлось оставить
     * @return Если есть курсив, то метод возвращает определенный тип, иначе вернет null
     */
    public static PatternQuestions patternQuestionsItalic(XWPFParagraph paragraph, Pattern pattern) {
        return paragraph.getRuns().get(0).isItalic() ? PatternQuestions.ITALIC : null;
    }

    /**
     * Метод определяет есть ли подчеркивание в параграфе
     * @param paragraph параграф
     * @param pattern поле не нужно передавать, из-за специфики языка пришлось оставить
     * @return Если есть подчеркивание, то метод возвращает определенный тип, иначе вернет null
     */
    public static PatternQuestions patternQuestionsUnderline(XWPFParagraph paragraph, Pattern pattern) {
        return ! paragraph.getRuns().get(0).getUnderline().equals(UnderlinePatterns.NONE) ? PatternQuestions.UNDERLINE : null;
    }

    /**
     * Метод определяет есть ли выделение в параграфе
     * @param paragraph параграф
     * @param pattern поле не нужно передавать, из-за специфики языка пришлось оставить
     * @return Если есть выделение, то метод возвращает определенный тип, иначе вернет null
     */
    public static PatternQuestions patternQuestionsHighlighting(XWPFParagraph paragraph, Pattern pattern) {
        return paragraph.getRuns().get(0).isHighlighted() ? PatternQuestions.HIGHLIGHTING : null;
    }


    /**
     * Метод выполняет регулярное вырожения к параграфу офисного документа
     * @param paragraph параграф офисного документа
     * @param pattern паттерн регулярного выражения
     * @param returnValue тип который нужно вернуть если он подходит
     * @return Если регулярное выражение подходит то метод возвращает тип вопроса, иначе возвращает null
     */
    public static PatternQuestions patternQuestions(XWPFParagraph paragraph, Pattern pattern, PatternQuestions returnValue) {
        if (paragraph.getText() != null) {
            return pattern.matcher(paragraph.getText().trim()).find() ? returnValue : null;
        }
        return null;
    }

    /**
     * @return Построитель категории вопроса
     */
    public static Question categoryBuilder() {
        return Question.builder()
                .type("category")
                .category(TextValue.builder().text("$course$/По умолчанию для ЭК").build())
                .build();
    }

    /**
     * Класс который собирает ответ вопроса
     * @param name имя вопроса
     * @param description описание вопроса
     * @return возвращает вопрос
     */
    public static Question quizBuilder(String name, String description) {
        return Question.builder()
                .name(TextValue.builder().text(name).build())
                .questionText(FormatTextValue.builder().format("html").text("<p>"+ description +"</p>").build())
                .generalFeedback(FormatTextValue.builder().format("html").build())
                .defaultGrade(1.0000000)
                .penalty(0.3333333)
                .hidden(0L)
                .single(true)
                .shuffleAnswers(true)
                .answerNumbering("abc")
                .correctFeedback(FormatTextValue.builder().format("html").text("Ваш ответ верный.").build())
                .partiallyCorrectFeedback(FormatTextValue.builder().format("html").text("Ваш ответ частично правильный.").build())
                .incorrectFeedback(FormatTextValue.builder().format("html").text("Ваш ответ неправильный.").build())
                .showNumCorrect(null)
                .type("multichoice")
                .build();

    }

}
