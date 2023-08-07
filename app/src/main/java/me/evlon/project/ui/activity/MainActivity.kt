@file:Suppress("SetTextI18n")

package me.evlon.project.ui.activity

import android.content.ComponentName
import android.content.pm.PackageManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.hook.factory.dataChannel
import com.highcapable.yukihookapi.hook.factory.prefs
import me.evlon.project.BuildConfig
import me.evlon.project.R
import me.evlon.project.bean.ChannelCmd
import me.evlon.project.bean.RecordLocationBean
import me.evlon.project.config.DataConst
import me.evlon.project.databinding.ActivityMainBinding
import me.evlon.project.hook.entity.CmdLocationNameArray
import me.evlon.project.hook.utils.Log
import me.evlon.project.ui.activity.base.BaseActivity
import kotlin.reflect.typeOf

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate() {
        refreshModuleStatus()
        binding.mainTextVersion.text = getString(R.string.module_version, BuildConfig.VERSION_NAME)
        binding.hideIconInLauncherSwitch.isChecked = isLauncherIconShowing.not()
        binding.hideIconInLauncherSwitch.setOnCheckedChangeListener { button, isChecked ->
            if (button.isPressed) hideOrShowLauncherIcon(isChecked)
        }
        // Your code here.

        binding.switchReportLocation.isChecked = prefs().getBoolean("om_disableReportLocation",true);
        binding.switchReportLocation.setOnCheckedChangeListener {
            button, isChecked ->
            if(button.isPressed){
                prefs().edit {
                    putBoolean("om_disableReportLocation", isChecked)
                }
            }
        }


//        val moduleDataChannel = dataChannel(packageName = "com.cmict.oa");
//        moduleDataChannel.wait<ChannelCmd>("listener_from_host") { value ->
//            // Your code here.
//            if(value.cmd == "LocationNameArray"){
//                val gson = Gson()
//                val arg = gson.fromJson(value.jsonBody, CmdLocationNameArray::class.java)
//                Log.d("PoiNames is ${arg.poiNames}, checked is ${arg.checkedValue}")
//                bindRecordLocationToView(arg.poiNames, arg.checkedValue, true)
//            }
//        }
//
//        moduleDataChannel.put("listener_from_module", ChannelCmd("LocationNameArray" , ""))
    }

    private fun bindRecordLocationToView(poiNames: Iterable<String?>, checkedValue: String?, rebinding: Boolean) {
        if(rebinding){
            binding.mainRecentLocation.removeAllViewsInLayout()
        }

        var rbDefault = RadioButton(this)
        rbDefault.text = "当前位置"
        rbDefault.isChecked  = checkedValue == ""

        binding.mainRecentLocation.addView(rbDefault)

        poiNames.forEach {

            val rb = RadioButton(this)
            rb.text = it
            rb.isChecked = checkedValue == rb.text
            binding.mainRecentLocation.addView(rb)
        }
    }

    private  fun bindRecordLocationToView(rebind : Boolean){
        if(rebind){
            binding.mainRecentLocation.removeAllViewsInLayout()
//            binding.mainRecentLocation.children.forEach {
//                binding.mainRecentLocation.removeAllViews()
//            }
        }

//        val gson = Gson()
//        var recordLocationSetString = ""
//        try{
//            recordLocationSetString =prefs().get(DataConst.om_recordLocationSet)
//        }
//        catch (e: Error){
//            recordLocationSetString = gson.toJson(HashSet<RecordLocationBean>())
//            prefs().edit {
//                put(DataConst.om_recordLocationSet,recordLocationSetString)
//            }
//        }

        Log.d("当前位置 ${prefs().get(DataConst.om_currentLocation )}")
        Toast.makeText(this,"读取位置${prefs().get(DataConst.om_currentLocation )}",Toast.LENGTH_LONG).show()

//        val recordLocationSetString =prefs().get(DataConst.om_recordLocationSet)
//        Log.d("读取位置${recordLocationSetString}")
//        Toast.makeText(this,"读取位置${recordLocationSetString}",Toast.LENGTH_LONG).show()
//
//        prefs().edit{
//            put(DataConst.om_currentLocation,"河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄河北石家庄")
//        }
//
//        val locationSet = gson.fromJson<HashSet<RecordLocationBean>>(recordLocationSetString, typeOf<HashSet<RecordLocationBean>>().javaClass)
//
//        val checkedValue = prefs().get(DataConst.om_currentLocation)
//
//        Log.d("读取位置${gson.toJson(locationSet)}")
//        Toast.makeText(this,"读取位置${gson.toJson(locationSet)}",Toast.LENGTH_LONG).show()
//
//        var rbDefault = RadioButton(this)
//        rbDefault.text = "当前位置"
//        rbDefault.isChecked  = checkedValue == ""
//
//        binding.mainRecentLocation.addView(rbDefault)
//
//        locationSet.forEach {
//
//            val rb = RadioButton(this)
//            rb.text = it.POIName
//            rb.isChecked = checkedValue == rb.text
//            binding.mainRecentLocation.addView(rb)
//        }

    }

    /**
     * Hide or show launcher icons
     *
     * - You may need the latest version of LSPosed to enable the function of hiding launcher
     *   icons in higher version systems
     *
     * 隐藏或显示启动器图标
     *
     * - 你可能需要 LSPosed 的最新版本以开启高版本系统中隐藏 APP 桌面图标功能
     * @param isShow Whether to display / 是否显示
     */
    private fun hideOrShowLauncherIcon(isShow: Boolean) {
        packageManager?.setComponentEnabledSetting(
            ComponentName(packageName, "${BuildConfig.APPLICATION_ID}.Home"),
            if (isShow) PackageManager.COMPONENT_ENABLED_STATE_DISABLED else PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    /**
     * Get launcher icon state
     *
     * 获取启动器图标状态
     * @return [Boolean] Whether to display / 是否显示
     */
    private val isLauncherIconShowing
        get() = packageManager?.getComponentEnabledSetting(
            ComponentName(packageName, "${BuildConfig.APPLICATION_ID}.Home")
        ) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED

    /**
     * Refresh module status
     *
     * 刷新模块状态
     */
    private fun refreshModuleStatus() {
        binding.mainLinStatus.setBackgroundResource(
            when {
                YukiHookAPI.Status.isXposedModuleActive -> R.drawable.bg_green_round
                else -> R.drawable.bg_dark_round
            }
        )
        binding.mainImgStatus.setImageResource(
            when {
                YukiHookAPI.Status.isXposedModuleActive -> R.mipmap.ic_success
                else -> R.mipmap.ic_warn
            }
        )
        binding.mainTextStatus.text = getString(
            when {
                YukiHookAPI.Status.isXposedModuleActive -> R.string.module_is_activated
                else -> R.string.module_not_activated
            }
        )
        binding.mainTextApiWay.isVisible = YukiHookAPI.Status.isXposedModuleActive
        binding.mainTextApiWay.text = if (YukiHookAPI.Status.Executor.apiLevel > 0)
            "Activated by ${YukiHookAPI.Status.Executor.name} API ${YukiHookAPI.Status.Executor.apiLevel}"
        else "Activated by ${YukiHookAPI.Status.Executor.name}"
    }
}