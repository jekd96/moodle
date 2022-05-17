package com.moodle.moodle;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

@SpringBootTest
public class RegexTest {

    @Test
    public void someTest() {
        String text = "100.Что такое осень";
        Pattern p = Pattern.compile("^[0-9]+\\.|^[a-z]+\\.|^[A-Z]+\\.|^[А-Я]+\\.|^[а-я]+\\.");
        Assert.assertTrue(p.matcher(text).find());
        text = "Что такое осень";
        Assert.assertFalse(p.matcher(text).find());
    }

}
