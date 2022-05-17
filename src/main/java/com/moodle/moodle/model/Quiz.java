package com.moodle.moodle.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.moodle.moodle.model.question.Question;
import lombok.*;

import java.util.List;

@JacksonXmlRootElement(localName = "quiz")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Quiz {

    @Singular("question")
    @JacksonXmlProperty(localName = "question")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Question> questions;

}
