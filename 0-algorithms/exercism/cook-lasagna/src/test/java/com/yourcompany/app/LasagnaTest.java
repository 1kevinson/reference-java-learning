package com.yourcompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LasagnaTest {

    @Test
    void shouldExpectMinutesInOven() {
        assertEquals(40, new Lasagna().expectedMinutesInOven());
    }

    @Test
    void shouldShowRemainingMinutesInOven() {
        assertEquals(20, new Lasagna().remainingMinutesInOven(20));
    }

    @Test
    void shouldShowPreparationTimeInMinutes() {
        assertEquals(4, new Lasagna().preparationTimeInMinutes(2));
    }

    @Test
    void shouldDisplayTotalTimeInMinutes() {
        assertEquals(34, new Lasagna().totalTimeInMinutes(2, 10));
    }
}