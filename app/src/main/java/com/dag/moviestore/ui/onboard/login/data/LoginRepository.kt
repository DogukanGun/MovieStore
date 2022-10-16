package com.dag.moviestore.ui.onboard.login.data

import android.se.omapi.Session
import com.dag.moviestore.R
import com.dag.moviestore.base.general.MarketType
import com.dag.moviestore.base.general.MovieStoreActivityListener
import com.dag.moviestore.base.general.MovieStoreApplication
import com.dag.moviestore.base.general.ResourceManager
import com.dag.moviestore.network.BaseRepository
import com.dag.moviestore.network.BaseResult
import com.dag.moviestore.session.MovieStoreSessionManager
import com.dag.moviestore.session.SessionKey
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject
import kotlin.math.log

class LoginRepository @Inject constructor(): BaseRepository(){

    private var mAuth: FirebaseAuth? = null
    private lateinit var googleSignInClient: GoogleSignInClient

    init {
        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resourceManager.getString(R.string.web_client_id))
            .requestEmail()
            .build()
        activityListener.currentActivity?.let {
            googleSignInClient = GoogleSignIn.getClient(it, gso)
        }
    }

    @Inject
    lateinit var activityListener: MovieStoreActivityListener

    @Inject
    lateinit var sessionManager: MovieStoreSessionManager

    @Inject
    lateinit var resourceManager: ResourceManager

    fun registerWithMail(emailLogin: EmailLogin){

    }


}

/*
var loginResult=true
        mAuth
            ?.createUserWithEmailAndPassword(emailLogin.email,emailLogin.password)
            ?.addOnFailureListener {
                if (it.message.equals("auth/email-already-in-use"))
                    loginResult = false
                return@addOnFailureListener
            }
            ?.addOnSuccessListener {

            }
        return loginResult
 */