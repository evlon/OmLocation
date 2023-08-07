package me.evlon.project.hook.entity

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSerializer
import com.highcapable.yukihookapi.hook.param.PackageParam
import me.evlon.project.bean.ChannelCmd
import me.evlon.project.config.DataConst


class CmdLocationNameArray(val poiNames: List<String?>, val checkedValue : String?)
public fun PackageParam.startChannel(){
    // 从模块获取
    dataChannel.wait<ChannelCmd>(key = "listener_from_module") { value ->
        // Your code here.
        val gson = Gson()

        if(value.cmd == "LocationNameArray"){
            val poiNames = prefs.get(DataConst.om_recordLocationSet).map { it.POIName }.toList()
            val  checkedValue = prefs.get(DataConst.om_currentLocation)

            val jsonBody = CmdLocationNameArray(poiNames, checkedValue)

            dataChannel.put(key = "listener_from_host", gson.toJson(jsonBody))
        }

    }
// 发送给模块

}