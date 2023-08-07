package me.evlon.project.hook.utils

import com.highcapable.yukihookapi.hook.log.loggerD
import com.highcapable.yukihookapi.hook.log.loggerE
import com.highcapable.yukihookapi.hook.log.loggerI
import com.highcapable.yukihookapi.hook.log.loggerW
import me.evlon.project.BuildConfig.TAG

object Log {
//    const val TAG = "OmLocation"
    fun d(msg: String) {
        loggerD(TAG, "------$msg------")
    }

    fun e(msg: String) {
        loggerE(TAG, "------$msg------")
    }

    fun i(msg: String) {
        loggerI(TAG, "------$msg------")
    }

    fun w(msg: String) {
        loggerW(TAG, "------$msg------")
    }

    fun d(TAG: String, msg: String) {
        loggerD(TAG, "------$msg------")
    }

    fun e(TAG: String, msg: String) {
        loggerE(TAG, "------$msg------")
    }

    fun i(TAG: String, msg: String) {
        loggerI(TAG, "------$msg------")
    }

    fun w(TAG: String, msg: String) {
        loggerW(TAG, "------$msg------")
    }
}