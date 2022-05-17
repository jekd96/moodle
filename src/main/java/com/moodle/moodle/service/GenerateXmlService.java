package com.moodle.moodle.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.moodle.moodle.exception.CommonException;
import com.moodle.moodle.model.PatternQuestions;
import com.moodle.moodle.model.Quiz;
import com.moodle.moodle.model.question.Question;
import com.moodle.moodle.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Service
public class GenerateXmlService {

    private final XmlMapper mapper;
    private final OfficeServiceOld officeServiceOld;
    private final OfficeService officeService;

    @SneakyThrows
    public void generate(String officePath, String xmlPath) {

        List<Question> questions = officeServiceOld.readAndParseDocument(officePath);
        questions.add(Utils.categoryBuilder());
        mapper.writeValue(new File(xmlPath), Quiz.builder()
                .questions(questions)
                .build());
    }

    public void generate(String officePath, String xmlPath, PatternQuestions patternQuestions) throws CommonException, IOException {

        List<Question> questions = officeService.readAndParseDocument(officePath, patternQuestions);
        questions.add(Utils.categoryBuilder());
        mapper.writeValue(new File(xmlPath), Quiz.builder()
                .questions(questions)
                .build());
    }


}
