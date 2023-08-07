package me.evlon.project.config

import com.highcapable.yukihookapi.hook.xposed.prefs.data.PrefsData
import me.evlon.project.bean.RecordLocationBean

object DataConst {
    val om_disableReportLocation = PrefsData("om_disableReportLocation",true);
    val om_recordLocationSet = PrefsData("om_recordLocationSet", HashSet<RecordLocationBean>())
    val om_currentLocation = PrefsData("om_currentLocation", "")
}