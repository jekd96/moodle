package com.moodle.moodle.service;

import com.moodle.moodle.exception.CommonException;
import com.moodle.moodle.exception.NoAnswerQuestionException;
import com.moodle.moodle.exception.ParseQuestionException;
import com.moodle.moodle.model.PatternQuestions;
import com.moodle.moodle.model.question.Answer;
import com.moodle.moodle.model.question.Question;
import com.moodle.moodle.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.moodle.moodle.model.PatternQuestions.*;

/**
 * Класс занимается чтение docx документа и парсинга его в модель Question (Вопросов)
 */
@Slf4j
@Service
public class OfficeService {


    /**
     * Метод для чтения и парсинга документа в модель Question
     * @param path Путь в docx документу
     * @param patternQuestions Формат вопроса
     * @return Возвращает коллекцию вопросов которые распарсились из docx документа
     * @throws IOException ошибка при чтении docx документа
     * @throws CommonException ошибка при папрсинга документа
     */
    public List<Question> readAndParseDocument(String path, PatternQuestions patternQuestions) throws IOException, CommonException {
        try (XWPFDocument doc = new XWPFDocument(
                Files.newInputStream(Paths.get(path)))) {
            List<Question> questions = new ArrayList<>();
            Question currentQuestion = null;
            List<Answer> currentAnswers = new ArrayList<>();
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                String row = paragraph.getText();
                if (row == null || row.isEmpty() || row.equals("\t") || row.isBlank() || row.trim().isBlank()) {
                    continue;
                }
                row = row.trim();
                List<PatternQuestions> patters = getParseQuestionTypes(paragraph);
                if (patters.isEmpty()) {
                    continue;
                }
                PatternQuestions pattern = patters.get(0);
                String replaceText;
                if (pattern == BOLD || pattern == ITALIC || pattern == UNDERLINE || pattern == LIST_QUESTION_WORD || pattern == HIGHLIGHTING) {
                    replaceText = row;
                } else {
                    try {
                        replaceText = row.substring(row.indexOf(pattern.value) + 1);
                    } catch (Exception e) {
                        log.error("Error replace text = {}, pattern = {}", row, pattern);

                        if (questions.isEmpty()) {
                            throw new ParseQuestionException(0, "" , row, e);
                        }
                        throw new ParseQuestionException(questions.size(), questions.get(questions.size()-1).getName().getText(), row, e);

                    }
                }

                if (pattern == patternQuestions) {
                    if (patternQuestions == BOLD || patternQuestions == ITALIC || patternQuestions == UNDERLINE || pattern == HIGHLIGHTING) {
                        currentQuestion = Utils.quizBuilder(row, row);
                    } else {
                        currentQuestion = Utils.quizBuilder(replaceText, replaceText);
                    }
                    currentAnswers = new ArrayList<>();
                    currentQuestion.setAnswers(currentAnswers);
                    questions.add(currentQuestion);
                } else {

                    if (pattern == BOLD || pattern == ITALIC || pattern == UNDERLINE || pattern == HIGHLIGHTING) {
                        Answer itemAnswer = Answer.builder()
                                .text(row)
                                .fraction(0)
                                .patternQuestions(patters)
                                .build();
                        currentAnswers.add(itemAnswer);

                    } else {
                        Answer itemAnswer =Answer.builder()
                                .text(replaceText)
                                .fraction(0)
                                .patternQuestions(patters)
                                .build();
                        currentAnswers.add(itemAnswer);
                    }


                }
            }
            List<Question> resultQuestions = questions.stream()
                    .filter(question -> ! question.getName().getText().isEmpty() || ! question.getName().getText().isBlank())
                    .collect(Collectors.toList());
            for (Question question : resultQuestions) {
                if (question.getAnswers().isEmpty()) {
                    throw new NoAnswerQuestionException(question, new Exception());
                }
            }
            setFraction(resultQuestions);
            return resultQuestions;
        }
    }

    /**
     * Проставляет правильные ответы у вопросов
     * @param questions Список вопросов
     */
    private void setFraction(List<Question> questions) {
        for (Question question : questions) {
            question.getAnswers().stream().max(Comparator.comparingInt(o -> o.getPatternQuestions().size())).ifPresent(answer -> answer.setFraction(100));
        }
    }

    /**
     * Вспомогательный метод которые возвращает из строки список паттернов регулярных выражений и стилей которые
     * использовались в этой строке
     * @param paragraph Принимает параграф из doc документа
     * @return Возвращает список паттернов, которые использованы в этом параграфе
     */
    private List<PatternQuestions> getParseQuestionTypes(XWPFParagraph paragraph) {
        List<PatternQuestions> patternQuestions = new ArrayList<>();
        for (PatternQuestions value : PatternQuestions.values()) {
            PatternQuestions item = value.function.apply(paragraph, value.pattern);
            if (item != null) {
                patternQuestions.add(item);
            }
        }
        return patternQuestions;
    }



}
