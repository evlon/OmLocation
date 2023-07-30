package me.evlon.project.hook

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit






@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        // Your code here.
//        debugLog {
//            tag = "OmLocation"
//            isEnable = true
//            isRecord = true
//            elements(TAG, PRIORITY, PACKAGE_NAME, USER_ID)
//        }
//
//        isDebug = false
        //"com.amap.api.location.AMapLocation".
    }

    override fun onHook() = encase {
        // Your code here.
    }
}