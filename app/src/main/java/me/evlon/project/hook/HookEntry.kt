package me.evlon.project.hook

import com.highcapable.yukihookapi.YukiHookAPI.configs
import com.highcapable.yukihookapi.YukiHookAPI.encase
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed

import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit


import me.evlon.project.hook.entity.hookJiagu360
import me.evlon.project.hook.entity.startChannel
import me.evlon.project.hook.utils.Log

@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        // Your code here.

        debugLog {
            tag = "OmLocation"
            isEnable = true
            isRecord = true
            elements(TAG, PRIORITY, PACKAGE_NAME, USER_ID)
        }

        isDebug = true

    }

    override fun onHook() {


        val objThis = this;
        encase {
            // Your code here.
            Log.d("om location onhook ${this.packageName}")

//            loadApp(packageName) {
//                hookJiagu360()
//            }

            if("com.cmict.oa" == this.packageName) {
                loadApp(name = "com.cmict.oa") {
                    hookJiagu360()
                    startChannel()
                }
            }
        }
    }
}

