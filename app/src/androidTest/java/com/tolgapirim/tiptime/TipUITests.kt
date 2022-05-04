package com.tolgapirim.tiptime

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.tolgapirim.tiptime.ui.theme.TipTimeTheme
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TipUITests {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Before
    fun setContent(){
        composeTestRule.setContent {
            TipTimeTheme {
                MyApp()
            }
        }
    }

    @Test
    fun calculate_20_percent_tip_no_roundup() {

        composeTestRule.onNodeWithText("Cost of Service")
            .performTextInput("73")

        composeTestRule.onNodeWithTag("roundUp")
            .performClick()

        composeTestRule.onNodeWithText("CALCULATE")
            .performClick()

        composeTestRule.onNodeWithText("Tip Amount: $15.00")
    }

    @Test
    fun calculate_20_percent_tip_roundup() {

        composeTestRule.onNodeWithText("Cost of Service")
            .performTextInput("73")

        composeTestRule.onNodeWithText("CALCULATE")
            .performClick()

        composeTestRule.onNodeWithText("Tip Amount: $15.00")
    }

    @Test
    fun calculate_18_percent_tip_no_roundup() {
        composeTestRule.onNodeWithText("Cost of Service")
            .performTextInput("73")

        composeTestRule.onNodeWithText("Good (18%)")
            .performClick()

        composeTestRule.onNodeWithTag("roundUp")
            .performClick()

        composeTestRule.onNodeWithText("CALCULATE")
            .performClick()

        composeTestRule.onNodeWithText("Tip Amount: $13.14")


    }

    @Test
    fun calculate_18_percent_tip_roundup() {
        composeTestRule.onNodeWithText("Cost of Service")
            .performTextInput("73")

        composeTestRule.onNodeWithText("Good (18%)")
            .performClick()


        composeTestRule.onNodeWithText("CALCULATE")
            .performClick()

        composeTestRule.onNodeWithText("Tip Amount: $14.00")


    }

    @Test
    fun calculate_15_percent_tip_no_roundup() {

        composeTestRule.onNodeWithText("Cost of Service")
            .performTextInput("73")

        composeTestRule.onNodeWithText("Okay (15%)")
            .performClick()

        composeTestRule.onNodeWithTag("roundUp")
            .performClick()

        composeTestRule.onNodeWithText("CALCULATE")
            .performClick()

        composeTestRule.onNodeWithText("Tip Amount: $10.95")

    }

    @Test
    fun calculate_15_percent_tip_roundup() {
        composeTestRule.onNodeWithText("Cost of Service")
            .performTextInput("73")

        composeTestRule.onNodeWithText("Okay (15%)")
            .performClick()


        composeTestRule.onNodeWithText("CALCULATE")
            .performClick()

        composeTestRule.onNodeWithText("Tip Amount: $11.00")

    }
}