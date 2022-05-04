package com.tolgapirim.tiptime

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateTipTests {

    @Test
    fun calculate_20_percent_tip_no_roundup() {
        val costOfService = "73.00"
        val tipPercent = 0.20
        val actualTip = calculateTipPercantage(costOfService, tipPercent, false)

        assertEquals("₺14,60", actualTip)
    }

    @Test
    fun calculate_20_percent_tip_roundup() {
        val costOfService = "73.00"
        val tipPercent = 0.20
        val actualTip = calculateTipPercantage(costOfService, tipPercent, true)

        assertEquals("₺15,00", actualTip)
    }

    @Test
    fun calculate_18_percent_tip_no_roundup() {
        val costOfService = "73.00"
        val tipPercent = 0.18
        val actualTip = calculateTipPercantage(costOfService, tipPercent, false)

        assertEquals("₺13,14", actualTip)
    }

    @Test
    fun calculate_18_percent_tip_roundup() {
        val costOfService = "73.00"
        val tipPercent = 0.18
        val actualTip = calculateTipPercantage(costOfService, tipPercent, true)

        assertEquals("₺14,00", actualTip)
    }

    @Test
    fun calculate_15_percent_tip_no_roundup() {
        val costOfService = "73.00"
        val tipPercent = 0.15
        val actualTip = calculateTipPercantage(costOfService, tipPercent, false)

        assertEquals("₺10,95", actualTip)
    }

    @Test
    fun calculate_15_percent_tip_roundup() {
        val costOfService = "73.00"
        val tipPercent = 0.15
        val actualTip = calculateTipPercantage(costOfService, tipPercent, true)

        assertEquals("₺11,00", actualTip)
    }
}