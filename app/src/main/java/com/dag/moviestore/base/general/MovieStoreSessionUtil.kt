package com.dag.moviestore.base.general

import android.os.CountDownTimer
import java.util.*

class MovieStoreSessionUtil {
    companion object{
        val clSession = ClSession()
        var isSessionEndingProcessStarted = false

        fun startSession(sessionTimeOutInSeconds: Long){
            clSession.startSession(sessionTimeOutInSeconds)
        }
        fun restartSessionTime(){
            clSession.restartSessionTime()
        }
        fun endSession(){
            isSessionEndingProcessStarted = false
            clSession.endSession()
            // TODO: Buraya deviceUtil gelmeli
        }
        fun addObserver(observer: Observer){
            clSession.addObserver(observer)
        }
        fun deleteObserver(observer: Observer){
            clSession.deleteObserver(observer)
        }
    }

    class ClSession:Observable(){

        var sessionStarted:Boolean = false
            private set
        private var timer:CountDownTimer? = null

        fun startSession(sessionTimeOutInSeconds:Long){
            if (timer == null){
                timer = object :CountDownTimer(sessionTimeOutInSeconds*1000,1000L){
                    override fun onTick(p0: Long) {}

                    override fun onFinish() {
                        sessionStarted = true
                        setChanged()
                        notifyObservers()
                    }
                }
            }
            sessionStarted = true
            timer?.cancel()
            timer?.start()
        }
        fun restartSessionTime(){
            if (sessionStarted){
                timer?.cancel()
                timer?.start()
            }
        }
        fun endSession(){
            timer?.cancel()
            sessionStarted = false
        }
    }
}