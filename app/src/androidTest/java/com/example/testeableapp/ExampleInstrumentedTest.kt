package com.example.testeableapp

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.testeableapp.ui.Screens.TipCalculatorScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.testeableapp", appContext.packageName)
    }

    //    Test 1: Redondear propina y validar cambio de cálculo
    @Test
    fun roundUpTipTest() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

//      Establecer valor de propina
        composeTestRule.onNodeWithTag("tipInput").performTextInput("95")

//      Chequear propina sin redoneo
        composeTestRule.onNodeWithTag("tipValue").assertTextEquals("Propina: $14.25")

//      Click en checkbox para redondear
        composeTestRule.onNodeWithTag("roundUpCheckbox").performClick()

//      Chequear nuevo valor de propina
        composeTestRule.onNodeWithTag("tipValue").assertTextEquals("Propina: $15.00")
    }

    //    Test 2: Cambiar slider del porcentaje de propina y verificar cálculo
    @Test
    fun changeTipPercentageWithSlider() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

//      Establecer valor de propina
        composeTestRule.onNodeWithTag("tipInput").performTextInput("100")

//      Cambiar valor del slider a: 20%
        composeTestRule.onNodeWithTag("tipSlider")
            .performSemanticsAction(SemanticsActions.SetProgress) { it(20f) }

//      Chequear nueva propina
        composeTestRule.onNodeWithTag("tipValue")
            .assertTextEquals("Propina: $20.00")
    }

    //    Test 3: Validar presencia de elementos UI: monto, porcentaje, número de personas
    @Test
    fun validateComponentsPresenceTest() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

//      Monto (solo input)
        composeTestRule.onNodeWithTag("tipInput").assertExists()

//      Porcentaje (texto y slider)
        composeTestRule.onNodeWithTag("tipPercentage").assertExists()
        composeTestRule.onNodeWithTag("tipSlider").assertExists()

//      Numero de personas (texto e input personalizado)
        composeTestRule.onNodeWithTag("numberOfPeople").assertExists()
        composeTestRule.onNodeWithTag("numberOfPeopleInput").assertExists()
    }
}