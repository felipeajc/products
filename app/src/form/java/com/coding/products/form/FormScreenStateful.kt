package com.coding.products.form

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coding.products.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@Composable
fun FormScreenStateful(
    modifier: Modifier = Modifier,
    viewModel: FormViewModel = hiltViewModel()
) {
    val state by viewModel.formState.collectAsState()
    val focusManager = LocalFocusManager.current

    FormScreenStateless(
        state = state,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPhoneChange = { viewModel.onPhoneChange(it.take(12)) },
        onPromoCodeChange = { viewModel.onPromoCodeChange(it.take(7)) },
        onDateChange = viewModel::onDateChange,
        onRatingChange = viewModel::onRatingChange,
        onSubmit = {
            focusManager.clearFocus()
            viewModel.validateForm()
        },
        modifier = modifier
    )
}

@Composable
fun FormScreenStateless(
    state: FormState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPromoCodeChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onRatingChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberLazyListState()

    LazyColumn(
        state = scrollState,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            InputField(
                label = stringResource(R.string.form_name_label),
                value = state.name,
                onValueChange = onNameChange,
                error = state.nameError.asString(context),
                keyboardType = KeyboardType.Text
            )
        }
        item {
            InputField(
                label = stringResource(R.string.form_email_label),
                value = state.email,
                onValueChange = onEmailChange,
                error = state.emailError.asString(context),
                keyboardType = KeyboardType.Email
            )
        }
        item {
            InputField(
                label = stringResource(R.string.form_phone_label),
                value = state.phone,
                onValueChange = onPhoneChange,
                error = state.phoneError.asString(context),
                keyboardType = KeyboardType.Number
            )
        }
        item {
            InputField(
                label = stringResource(R.string.form_promo_code_label),
                value = state.promoCode,
                onValueChange = onPromoCodeChange,
                error = state.promoCodeError.asString(context),
                keyboardType = KeyboardType.Text
            )
        }
        item {
            RatingDropdown(
                selected = state.rating,
                onSelectedChange = onRatingChange,
                isError = state.ratingError != null,
                errorText = state.ratingError.asString(context),
            )
        }
        item {
            DatePickerField(
                date = state.deliveryDate,
                error = state.deliveryDateError.asString(context),
                onDateSelected = onDateChange,
                context = context
            )
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.form_submit))
            }
        }
        if (state.formSubmittedSuccessfully) {
            item {
                Text(
                    text = stringResource(R.string.form_success),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String?,
    keyboardType: KeyboardType
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            isError = error != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        error?.let { ErrorText(it) }
    }
}

@Composable
private fun ErrorText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
    )
}

@Composable
private fun DatePickerField(
    date: LocalDate?,
    error: String?,
    onDateSelected: (String) -> Unit,
    context: Context
) {
    val formattedDate = date?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        ?: stringResource(R.string.form_select_date)

    Column {
        Text(
            text = stringResource(R.string.form_date_label),
            style = MaterialTheme.typography.labelMedium
        )

        Button(
            onClick = { showDatePickerDialog(context, onDateSelected) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = formattedDate,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.desc_open_date_picker)
                )
            }
        }

        error?.let { ErrorText(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingDropdown(
    selected: String,
    onSelectedChange: (String) -> Unit,
    isError: Boolean,
    errorText: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(
        stringResource(R.string.rating_poor),
        stringResource(R.string.rating_fair),
        stringResource(R.string.rating_good),
        stringResource(R.string.rating_very_good),
        stringResource(R.string.rating_excellent)
    )

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                label = { Text(stringResource(R.string.form_rating_label)) },
                isError = isError,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelectedChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }

        if (isError && !errorText.isNullOrBlank()) {
            ErrorText(errorText)
        }
    }
}

fun showDatePickerDialog(
    context: Context,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selected = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            val formatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(selected.time)
            onDateSelected(formatted)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

@Preview(showBackground = true)
@Composable
fun FormScreenPreview() {
    MaterialTheme {
        FormScreenStateless(
            state = FormState(formSubmittedSuccessfully = true),
            onNameChange = {},
            onEmailChange = {},
            onPhoneChange = {},
            onPromoCodeChange = {},
            onDateChange = {},
            onRatingChange = {},
            onSubmit = {}
        )
    }
}
