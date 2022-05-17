package com.moodle.moodle.model.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.moodle.moodle.model.PatternQuestions;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
public class Answer extends FormatTextValue {

    private FormatTextValue feedback;

    @JsonIgnore
    private List<PatternQuestions> patternQuestions;

    @JacksonXmlProperty(isAttribute = true, localName = "fraction")
    public Integer getFraction() {
        return patternQuestions != null && patternQuestions.size() > 1 ? 100 : 0;
    }
}
