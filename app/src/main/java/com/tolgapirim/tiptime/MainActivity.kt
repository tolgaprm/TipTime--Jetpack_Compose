package com.tolgapirim.tiptime


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tolgapirim.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme() {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            {
                                Text(
                                    text = "Tip Time",
                                    fontSize = 20.sp,

                                    )
                            }
                        )


                    }
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {

    var costOfService by remember { mutableStateOf("") }
    val tipPercentage: MutableState<Double> = remember { mutableStateOf(0.20) }
    val selected: MutableState<Boolean> = remember { mutableStateOf(true) }
    val formattedTip = remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {

        // CostOfService EditText
        Row(
            modifier = Modifier.padding(bottom = 30.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_store_24),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 10.dp)
            )

            OutlinedTextField(
                value = costOfService,
                onValueChange = {
                    costOfService = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Cost of Service") }

            )


        }

        HowWasTheService()

        // Radio Button
        RadioGroup(tipPercentage)


        // Switch Button
        RoundUpTip(selected)


        // Calculate Button
        Button(
            onClick = {
                formattedTip.value =
                    calculateTipPercantage(costOfService, tipPercentage, selected)
            },
            modifier = Modifier
                .padding(top = 20.dp, start = 45.dp, end = 0.dp)
                .fillMaxWidth()
                .height(40.dp)

        ) {
            Text(text = "CALCULATE")
        }


        // Tip Amount Text
        Text(
            text = "Tip Amount: ${formattedTip.value}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, end = 5.dp)
                .alpha(0.5f),
            textAlign = TextAlign.End
        )
    }


}


@Composable
fun HowWasTheService() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_room_service_24),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 10.dp)
        )
        Text(text = "How was the service?")


    }
}


@Composable
fun RadioGroup(tipPercantage: MutableState<Double>) {
    val radioOptions = listOf("Amazing (20%)", "Good (18%)", "Okay (15%)")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column(modifier = Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            tipPercantage.value = when (text) {
                                "Amazing (20%)" -> 0.20
                                "Good (18%)" -> 0.18
                                else -> 0.15
                            }
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)

                        tipPercantage.value = when (text) {
                            "Amazing (20%)" -> 0.20
                            "Good (18%)" -> 0.18
                            else -> 0.15
                        }

                    }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 16.dp)

                )
            }
        }

        println(tipPercantage.value)

    }
}


@Composable
fun RoundUpTip(selected: MutableState<Boolean>) {


    Row(
        modifier = Modifier.padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_call_made_24),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = "Round up Tip?",
            modifier = Modifier.padding(start = 10.dp)
        )
        Switch(
            checked = selected.value,
            onCheckedChange = { selected.value = !selected.value },
            modifier = Modifier.padding(start = 170.dp)
        )


    }
}


fun calculateTipPercantage(
    costOfService: String,
    tipPercantage: MutableState<Double>,
    selected: MutableState<Boolean>
): String {
    var tip: Double = tipPercantage.value * costOfService.toDouble()

    if (selected.value == true) {
        tip = kotlin.math.ceil(tip)
    }

    // Her ülkenin ayrı parabirimi ve bu parbirimlerinin gösterimleri farklı
    /* örneğin Dolar $ olarak gösterilirken Euro € NumberFormat.getCurrencyInstance()
    ile bulunduğun ülkenin para birim kodunu otamatik olarak ayarlar.
    * */
    val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
    println(formattedTip)
    return formattedTip
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}

