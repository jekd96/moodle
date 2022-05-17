package com.moodle.moodle.util;


import com.moodle.moodle.model.PatternQuestions;
import com.moodle.moodle.model.question.FormatTextValue;
import com.moodle.moodle.model.question.Question;
import com.moodle.moodle.model.question.TextValue;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.regex.Pattern;

public class Utils {


    public static PatternQuestions patternQuestionTextRus(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.TEXT_QUESTION_RUS);
    }

    public static PatternQuestions patternQuestionTextEng(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.TEXT_QUESTION_ENG);
    }

    public static PatternQuestions patternListQuestionWord(XWPFParagraph paragraph, Pattern pattern) {
        if (paragraph.getNumID() != null) {
            return PatternQuestions.LIST_QUESTION_WORD;
        }
        return null;
    }

    public static PatternQuestions patternQuestionsBracket(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.BRACKET);
    }

    public static PatternQuestions patternQuestionsPoint(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.POINT);
    }


    public static PatternQuestions patternQuestionsSemicolon(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.SEMICOLON);
    }

    public static PatternQuestions patternQuestionsColon(XWPFParagraph paragraph, Pattern pattern) {
        return patternQuestions(paragraph, pattern, PatternQuestions.COLON);
    }

    public static PatternQuestions patternQuestionsBold(XWPFParagraph paragraph, Pattern pattern) {
        return paragraph.getRuns().get(0).isBold() ? PatternQuestions.BOLD : null;
    }

    public static PatternQuestions patternQuestionsItalic(XWPFParagraph paragraph, Pattern pattern) {
        return paragraph.getRuns().get(0).isItalic() ? PatternQuestions.ITALIC : null;
    }

    public static PatternQuestions patternQuestionsUnderline(XWPFParagraph paragraph, Pattern pattern) {
        return ! paragraph.getRuns().get(0).getUnderline().equals(UnderlinePatterns.NONE) ? PatternQuestions.UNDERLINE : null;
    }

    public static PatternQuestions patternQuestionsHighlighting(XWPFParagraph paragraph, Pattern pattern) {
        return paragraph.getRuns().get(0).isHighlighted() ? PatternQuestions.HIGHLIGHTING : null;
    }

    public static PatternQuestions patternQuestions(XWPFParagraph paragraph, Pattern pattern, PatternQuestions returnValue) {
        if (paragraph.getText() != null) {
            return pattern.matcher(paragraph.getText().trim()).find() ? returnValue : null;
        }
        return null;
    }

    public static Question categoryBuilder() {
        return Question.builder()
                .type("category")
                .category(TextValue.builder().text("$course$/По умолчанию для ЭК").build())
                .build();
    }

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
