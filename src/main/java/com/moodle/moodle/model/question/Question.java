package com.moodle.moodle.model.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Question {

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    private TextValue category;

    private TextValue name;

    @JacksonXmlProperty( localName = "questiontext")
    private TextValue questionText;

    @JacksonXmlProperty(localName = "generalfeedback")
    private TextValue generalFeedback;

    @JacksonXmlProperty(localName = "defaultgrade")
    private Double defaultGrade;

    private Double penalty;

    private Long hidden;

    private Boolean single;

    @JacksonXmlProperty(localName = "shuffleanswers")
    private Boolean shuffleAnswers;

    @JacksonXmlProperty(localName = "answernumbering")
    private String answerNumbering;

    @JacksonXmlProperty(localName = "correctfeedback")
    private TextValue correctFeedback;

    @JacksonXmlProperty(localName = "partiallycorrectfeedback")
    private TextValue partiallyCorrectFeedback;

    @JacksonXmlProperty(localName = "incorrectfeedback")
    private TextValue incorrectFeedback;

    @JacksonXmlProperty(localName = "shownumcorrect")
    private Object showNumCorrect;

    @Singular("answer")
    @JacksonXmlProperty(localName = "answer")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Answer> answers = new ArrayList<>();



}
