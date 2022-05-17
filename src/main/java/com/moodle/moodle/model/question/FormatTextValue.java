package com.moodle.moodle.model.question;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
public class FormatTextValue extends TextValue {

    @JacksonXmlProperty(isAttribute = true)
    private String format = "html";

}
