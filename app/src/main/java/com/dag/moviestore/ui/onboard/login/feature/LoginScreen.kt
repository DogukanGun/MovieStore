package com.dag.moviestore.ui.onboard.login.feature

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dag.moviestore.R
import com.dag.moviestore.base.compose.button.CustomButton
import com.dag.moviestore.base.compose.navcontroller.NavScreen
import com.dag.moviestore.base.compose.roundedtextfield.RoundedTextField
import com.dag.moviestore.base.general.BaseDialogBoxUtil
import com.dag.moviestore.ext.findActivity
import com.dag.moviestore.ext.startNewActivity
import com.dag.moviestore.ui.mainscreen.HomeActivity
import com.dag.moviestore.ui.onboard.OnboardSurface
import com.dag.moviestore.ui.onboard.login.data.EmailLogin
import com.dag.moviestore.ui.onboard.login.data.LoginInputType
import com.dag.moviestore.ui.theme.MovieStoreTheme


@Composable
fun LoginScreen(
    viewModel: LoginVM = viewModel(),
) {
    val context = LocalContext.current
    val loginState = viewModel.loginState
    val tabIndex = remember { mutableStateOf(0) }
    val inputText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }
    val loginType by remember(key1 = tabIndex.value) {
        mutableStateOf(
            LoginInputType
                .values()
                .toList()
                .firstOrNull {
                    it.id == tabIndex.value
                } ?: LoginInputType.EMAIL
        )
    }
    if (viewModel.loginState.value.loginCompleted.value){
        context.startNewActivity(
            killFirst = true,
            intent = Intent(context,HomeActivity::class.java)
        )
    }
    PinErrorPopup(loginState, viewModel)
    ErrorPopup(loginState)
    OnboardSurface {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(30.dp),
                text = stringResource(id = R.string.login_screen_tablayout_text),
                style = MaterialTheme.typography.titleLarge
                    .copy(color = MaterialTheme.colorScheme.primary)
            )
            TabLayout(
                modifier = Modifier
                    .padding(
                        start = 30.dp,
                        end = 30.dp,
                        bottom = 30.dp
                    ),
                tabIndex = tabIndex
            )
            LoginInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                loginType = loginType,
                onValueChanged = {
                    inputText.value = it
                }
            )
            if (loginType.passwordRequired) {
                LoginInput(modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                    loginType = LoginInputType.PASSWORD,
                    onValueChanged = {
                        passwordText.value = it
                    })
            }
            CustomButton(onClick = {
                controlInputValues(
                    inputText = inputText.value,
                    passwordText = passwordText.value,
                    loginType = loginType,
                    notValid = {
                       loginState.value.errorMessage.value = "Fields are empty"
                    },
                    valid = {
                        val data = prepareData(
                            inputText = inputText.value,
                            passwordText = passwordText.value,
                            loginType = loginType
                        )
                        viewModel.loginCommand(loginType, data)
                    }
                )
            }) {
                Text(
                    text = stringResource(id = R.string.login_screen_done_button),
                    style = MaterialTheme.typography.titleMedium
                        .copy(color = Color.White)
                )
            }
        }
    }
}

@Composable
private fun ErrorPopup(loginState: MutableState<LoginState>) {
    if (loginState.value.errorMessage.value.isNotBlank()) {
        ErrorAlertDialog(
            errorMessage = loginState.value.errorMessage.value
        ) {
            loginState.value.errorMessage.value = ""
        }
    }
}

@Composable
private fun PinErrorPopup(
    loginState: MutableState<LoginState>,
    viewModel: LoginVM
) {
    if (loginState.value.showPinPage.value) {
        ShowPinAlertDialog(
            verifyPinCode = {
                viewModel.verifyPinCode(it)
            },
            dismiss = {
                loginState.value.showPinPage.value = false
            }
        )
    }
}

fun prepareData(inputText: String, passwordText: String, loginType: LoginInputType): Any =
    when (loginType) {
        LoginInputType.EMAIL -> {
            EmailLogin(inputText, passwordText)
        }
        LoginInputType.PHONE -> {
            inputText
        }
        else -> inputText
    }

fun controlInputValues(
    inputText:String,
    passwordText: String,
    loginType: LoginInputType,
    notValid:()->Unit,
    valid:()->Unit
){
    if(inputText.isEmpty() || (passwordText.isEmpty() && loginType.passwordRequired))
        notValid()
    else
        valid()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowPinAlertDialog(
    verifyPinCode:(String)->Unit,
    dismiss:()->Unit
){
    val text = mutableStateOf("")
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.login_screen_pincode))
        },
        text = {
            OutlinedTextField(
                value = text.value,
                onValueChange = {
                    text.value = it
                }
            )
        },
        confirmButton = {
            TextButton(onClick = {
                verifyPinCode(text.value)
            }) {
                Text(text = "Okay")
            }
        },
        onDismissRequest = { dismiss() }
    )
}

@Composable
fun ErrorAlertDialog(
    titleMessage:String = stringResource(id = R.string.login_screen_empty_field_title),
    errorMessage:String,
    dismiss:()->Unit
){
    AlertDialog(
        title = {
            Text(text = titleMessage)
        },
        text = {
            Text(text = errorMessage)
        },
        confirmButton = {
            TextButton(onClick = {
                dismiss()
            }) {
                Text(text = "Okay")
            }
        },
        onDismissRequest = { dismiss() }
    )
}

@Composable
fun TabLayout(
    modifier: Modifier,
    tabIndex: MutableState<Int>
) {
    val tabData = LoginInputType.values().toList()
        .filter { it.name != LoginInputType.PASSWORD.name }
        .map { it.name }
    TabRow(
        modifier = modifier,
        selectedTabIndex = tabIndex.value
    ) {
        tabData.forEachIndexed { index, text ->
            Tab(
                modifier = Modifier
                    .background(Color.White),
                selected = tabIndex.value == index,
                onClick = {
                    tabIndex.value = index
                }, text = {
                    Text(text = text)
                })
        }
    }
}

@Composable
fun LoginInput(
    modifier: Modifier,
    loginType: LoginInputType,
    onValueChanged: (String) -> Unit
) {
    RoundedTextField(
        modifier = modifier,
        hint = loginType.name.replaceFirstChar { it.uppercase() },
        keyboardType = loginType.keyboardType,
        onValueChanged = {
            onValueChanged(it)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginActivityPreview() {
    MovieStoreTheme {
        LoginScreen()
    }
}
