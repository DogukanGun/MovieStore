package com.dag.moviestore.ui.onboard.login.feature

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import com.dag.moviestore.base.general.*
import com.dag.moviestore.base.ui.MovieStoreViewModel
import com.dag.moviestore.dailogbox.*
import com.dag.moviestore.ext.safeLet
import com.dag.moviestore.session.MovieStoreSessionManager
import com.dag.moviestore.session.SessionKey
import com.dag.moviestore.ui.onboard.login.data.EmailLogin
import com.dag.moviestore.ui.onboard.login.data.LoginInputType
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val resourceManager: ResourceManager,
    private val activityListener: MovieStoreActivityListener,
    private val modelDialogHandler: ModelDialogHandler,
    private val sessionManager: MovieStoreSessionManager
): MovieStoreViewModel() {
    private var mAuth: FirebaseAuth? = null
    val loginState = mutableStateOf(LoginState())
    private var _verificationId: String = ""

    init {
        mAuth = FirebaseAuth.getInstance()

    }

    fun loginCommand(loginInputType: LoginInputType, data: Any) {
        when (loginInputType.name) {
            LoginInputType.EMAIL.name -> {
                registerByEmail(data as EmailLogin)
            }
            LoginInputType.PHONE.name -> {
                registerByPhoneNumber(data as String)
            }
        }
    }

    private fun registerByEmail(emailLogin: EmailLogin) {
        if (MovieStoreApplication.market == MarketType.GOOGLE) {
            registerByEmailGoogle(emailLogin)
        } else if (MovieStoreApplication.market == MarketType.HUAWEI) {

        }
    }

    private fun registerByEmailGoogle(emailLogin: EmailLogin) {
        mAuth
            ?.createUserWithEmailAndPassword(emailLogin.email, emailLogin.password)
            ?.addOnFailureListener {
                if (it.message.equals("ERROR_EMAIL_ALREADY_IN_USE"))
                    loginByEmailGoogle(emailLogin)
                else {
                    loginState.value.errorMessage.value = it.localizedMessage ?: "Unknown Error"
                }
                return@addOnFailureListener
            }
            ?.addOnSuccessListener {
                it.user?.let { user ->
                    sessionManager.addData(SessionKey.User.name, user)
                }
            }
    }

    private fun loginByEmailGoogle(emailLogin: EmailLogin) {
        mAuth
            ?.signInWithEmailAndPassword(emailLogin.email, emailLogin.password)
            ?.addOnFailureListener {

            }
            ?.addOnSuccessListener {
                it.user?.let { user ->
                    sessionManager.addData(SessionKey.User.name, user)
                }
            }
    }

    private fun registerByPhoneNumber(phoneNumber: String) {
        if (MovieStoreApplication.market == MarketType.GOOGLE) {
            registerByPhoneNumberGoogle(phoneNumber)
        } else if (MovieStoreApplication.market == MarketType.HUAWEI) {

        }
    }
    private fun registerByPhoneNumberGoogle(phoneNumber: String) {
        safeLet(activityListener.currentActivity,mAuth){ activity, firebaseAuth ->
            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

        }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }
        override fun onVerificationFailed(e: FirebaseException) {
            e.message?.let {
                loginState.value.errorMessage.value = it
            }
        }
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            _verificationId = verificationId
            loginState.value.showPinPage.value = true
        }
    }

    fun verifyPinCode(pinCode:String){
        val credential = PhoneAuthProvider.getCredential(_verificationId, pinCode)
        signInWithPhoneAuthCredential(credential)
    }

    private fun loginByPhoneNumberGoogle(phoneNumber: String) {


    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth
            ?.signInWithCredential(credential)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result.user?.let { user->
                        sessionManager.addData(SessionKey.User.name,user)
                        loginState.value.loginCompleted.value = true
                    }
                } else {
                    it.exception?.localizedMessage?.let { errorMessage->
                        loginState.value.errorMessage.value = errorMessage
                    }
                }
            }
    }
}