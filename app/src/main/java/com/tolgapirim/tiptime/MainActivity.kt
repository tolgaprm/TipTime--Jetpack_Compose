package com.tolgapirim.tiptime


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
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
import androidx.compose.ui.res.stringResource
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
            TipTimeTheme {
                MyApp()
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

    Scaffold(
        topBar = { AppBar() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {


            Row(
                modifier = Modifier.padding(bottom = 30.dp),
                verticalAlignment = Alignment.CenterVertically
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
                    label = { Text(text = stringResource(R.string.cost_of_service)) }

                )


            }

            HowWasTheService()

            // Radio Button
            RadioGroup(onClickButton = { tipPercentage.value = it })


            // Switch Button
            RoundUpTip(selected.value) {
                selected.value = !selected.value
            }


            // Calculate Button
            Button(
                onClick = {
                    formattedTip.value =
                        calculateTipPercantage(costOfService, tipPercentage.value, selected.value)
                },
                modifier = Modifier
                    .padding(top = 20.dp, start = 45.dp, end = 0.dp)
                    .fillMaxWidth()
                    .height(40.dp)

            ) {
                Text(text = stringResource(R.string.calculate))
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


}


@Composable
fun AppBar() {
    TopAppBar(
        {
            Text(
                text = stringResource(id = R.string.tip_time),
                fontSize = 20.sp,

                )
        }
    )
}

@Composable
fun HowWasTheService() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_room_service_24),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 10.dp),
            tint = MaterialTheme.colors.primary
        )
        Text(text = stringResource(R.string.how_was_the_service))
    }
}


@Composable
fun RadioGroup(onClickButton: (Double) -> Unit) {
    val radioOptions = listOf(
        stringResource(R.string.amazing), stringResource(R.string.good), stringResource(
            R.string.okay
        )
    )
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
                            onClickButton(
                                when (text) {
                                    "Amazing (20%)" -> 0.20
                                    "Good (18%)" -> 0.18
                                    else -> 0.15
                                }
                            )
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
                        onClickButton(
                            when (text) {
                                "Amazing (20%)" -> 0.20
                                "Good (18%)" -> 0.18
                                else -> 0.15
                            }
                        )

                    }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 16.dp)

                )
            }
        }
    }
}


@Composable
fun RoundUpTip(selected: Boolean, onCheckedChange: (Boolean) -> Unit) {


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
            text = stringResource(R.string.round_up_tip),
            modifier = Modifier.padding(start = 10.dp)
        )
        Switch(
            checked = selected,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(start = 170.dp)
        )


    }
}

@VisibleForTesting
fun calculateTipPercantage(
    costOfService: String,
    tipPercantage: Double,
    selected: Boolean
): String {
    var tip: Double = tipPercantage * costOfService.toDouble()

    if (selected == true) {
        tip = kotlin.math.ceil(tip)
    }

    // Her ülkenin ayrı parabirimi ve bu para birimlerinin gösterimleri farklı
    /* örneğin Dolar $ olarak gösterilirken Euro €
    NumberFormat.getCurrencyInstance()
    ile bulunduğun ülkenin para birim kodunu otamatik olarak ayarlar.
    * */
    val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
    return formattedTip
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}

