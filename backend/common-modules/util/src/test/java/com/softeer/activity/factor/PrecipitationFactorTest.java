package com.softeer.activity.factor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


class PrecipitationFactorTest {

    @ParameterizedTest(name = "Input \"{0}\" → expected score {1}")
    @CsvSource({
            "강수없음, 4",
            "1mm 미만, 3",
            "1.0mm, 3",
            "1.2mm, 3",
            "1.3mm, 2",
            "2.4mm, 2",
            "2.5mm, 1",
            "7.5mm, 1",
            "7.6mm, 1",
            "29.9mm, 1",
            "30.0~50.0mm, 1",
            "50.0mm 이상, 1",
    })
    void testPrecipitationFactor(String input, int expected) {
        int actual = PrecipitationFactor.calculateFactor(input);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "Invalid input \"{0}\" should throw IllegalArgumentException")
    @ValueSource(strings = {
            "",
            "abc",
            "1.2cm",
            "1.2 mm",
            "mm",
            "0.5",
            "100.0mm 이하",
            "2.0mm 이상",
            "30.0~50.0",
            "−1.0mm"
    })
    void testCalculateFactor_invalidInput_throws(String input) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> PrecipitationFactor.calculateFactor(input));
    }
}
