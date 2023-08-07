package me.evlon.project.hook.entity

import android.content.Context
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.ContextClass
import me.evlon.project.hook.utils.Log

public fun PackageParam.hookJiagu360() {
    Log.d("fun start hookJiagu360")
    findClass(name = "com.stub.StubApp").hook {
        injectMember {
            method {
                name = "attachBaseContext"
                param(ContextClass)
                beforeHook {
                    // Your code here.
                }
                afterHook {
                    // Your code here.
                    val context = this.args(0).any() as Context
                    val classLoader = context.classLoader;
                    Log.d("360 hook 20230805")
                    hookMainActivity(classLoader);
                    hookMainPresenter(classLoader);
                    hookCustomWebviewActivity(classLoader);

                }
            }
        }
    }
}
