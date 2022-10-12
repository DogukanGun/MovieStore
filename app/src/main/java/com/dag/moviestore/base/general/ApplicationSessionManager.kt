package com.dag.moviestore.base.general

import android.os.CountDownTimer
import java.util.*

class ApplicationSessionManager {

    companion object {
        val movieStoreSession = MovieStoreSession()

        var isSessionEndingProcessStarted = false

        fun startSession(sessionTimeOutInSeconds: Long) {
            movieStoreSession.startSession(sessionTimeOutInSeconds)
        }

        fun restartSessionTime() {
            movieStoreSession.restartSessionTime()
        }

        fun endSession() {
            isSessionEndingProcessStarted = false
            movieStoreSession.endSession()
        }

        fun addObserver(observer: Observer) {
            movieStoreSession.addObserver(observer)
        }

        fun deleteObserver(observer: Observer) {
            movieStoreSession.deleteObserver(observer)
        }

        fun deleteObservers() {
            movieStoreSession.deleteObservers()
        }
    }

    class MovieStoreSession : Observable() {

        var sessionStarted: Boolean = false
            private set
        private var timer: CountDownTimer? = null

        fun startSession(sessionTimeOutInSeconds: Long) {
            if (timer == null) {
                timer = object : CountDownTimer(sessionTimeOutInSeconds * 1000, 1000L) {
                    override fun onFinish() {
                        sessionStarted = false
                        setChanged()
                        notifyObservers() // session timeout
                    }

                    override fun onTick(millisUntilFinished: Long) {
                    }
                }
            }

            sessionStarted = true
            timer?.cancel()
            timer?.start()
        }

        fun restartSessionTime() {
            if (sessionStarted) {
                timer?.cancel()
                timer?.start()
            }
        }

        fun endSession() {
            timer?.cancel()
            sessionStarted = false
        }
    }
}