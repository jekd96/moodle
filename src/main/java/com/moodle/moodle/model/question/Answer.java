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

    @JacksonXmlProperty(isAttribute = true)
    private Integer fraction;

    private FormatTextValue feedback;

    @JsonIgnore
    private List<PatternQuestions> patternQuestions;

}
