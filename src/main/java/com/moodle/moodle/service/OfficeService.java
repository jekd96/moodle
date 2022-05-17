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
import java.util.List;
import java.util.stream.Collectors;

import static com.moodle.moodle.model.PatternQuestions.*;

@Slf4j
@Service
public class OfficeService {


    public List<Question> readAndParseDocument(String path, PatternQuestions patternQuestions) throws IOException, CommonException {
        try (XWPFDocument doc = new XWPFDocument(
                Files.newInputStream(Paths.get(path)))) {
            List<Question> questions = new ArrayList<>();
            Question currentQuestion;
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
                if (pattern == BOLD || pattern == ITALIC || pattern == UNDERLINE || pattern == LIST_QUESTION_WORD) {
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
                    if (patternQuestions == BOLD || patternQuestions == ITALIC || patternQuestions == UNDERLINE) {
                        currentQuestion = Utils.quizBuilder(row, row);
                    } else {
                        currentQuestion = Utils.quizBuilder(replaceText, replaceText);
                    }
                    currentAnswers = new ArrayList<>();
                    currentQuestion.setAnswers(currentAnswers);
                    questions.add(currentQuestion);
                } else {

                    if (pattern == BOLD || pattern == ITALIC || pattern == UNDERLINE) {
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
            return resultQuestions;
        }
    }



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
