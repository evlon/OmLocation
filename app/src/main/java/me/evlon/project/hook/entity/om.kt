package me.evlon.project.hook.entity

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.highcapable.yukihookapi.hook.factory.*
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.ContextClass
import de.robv.android.xposed.XposedHelpers
import me.evlon.project.hook.utils.Log
import com.highcapable.yukihookapi.hook.type.android.IntentClass
import com.highcapable.yukihookapi.hook.type.android.ViewClass
import com.highcapable.yukihookapi.hook.type.defined.VagueClass
import com.highcapable.yukihookapi.hook.type.defined.VagueType
import com.highcapable.yukihookapi.hook.type.java.DoubleClass
import com.highcapable.yukihookapi.hook.type.java.IntClass
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.xposed.prefs.data.PrefsData
import me.evlon.project.bean.RecordLocationBean
import me.evlon.project.config.DataConst
import java.nio.channels.DatagramChannel
import java.util.Objects
import kotlin.collections.HashSet
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

public fun PackageParam.hookMainActivity(classLoader: ClassLoader) {
    Log.d("fun start hookMainActivity")
    findClass("com.cmict.oa.activity.home.MainActivity", classLoader).hook {
        injectMember {
            method {
                name= "clickChat"
                param(ViewClass)
                beforeHook {
                    Log.d("clickChat ok")
                }
            }

            constructor {
                emptyParam()
                afterHook {
                    Log.d("Constructor MainActivity objThis is ${this.instance}")

                    val broadcastReceiver = this.instance.javaClass.field {
                        name="broadcastReceiver"

                    }.get(this.instance).any();
                    if(broadcastReceiver != null) {
                        Log.d("Constructor MainActivity broadcastReceiver is $broadcastReceiver")
                        hookBroadcastReceiver(classLoader, broadcastReceiver)
                    }
                    else{
                        Log.d("Constructor MainActivity broadcastReceiver is null");
                    }
                }
            }
        }
    }
}

public fun PackageParam.hookBroadcastReceiver(classLoader: ClassLoader, broadcastReceiver: Any){
    Log.d("fun start hookBroadcastReceiver")
    broadcastReceiver.javaClass.hook {
        injectMember {
            method {
                name = "onReceive"
                param(ContextClass, IntentClass)
                beforeHook {
                    val context = this.args(0).any() as Context;
                    val intent = this.args(1).any() as Intent;

                    Log.d("hook before broadcastReceiver onReceive:  ${intent.action}" );

                    if(intent.action == "location"){
                        val locationBeanString = intent.getStringExtra("location");
                        val locationType = intent.getIntExtra("type", -1);

                        if (locationType == 1) {
                            if (prefs.get(DataConst.om_disableReportLocation )) {
                                //过滤
                                intent.putExtra("location", "")
                                Log.d("skip locationBeanString: $locationBeanString");
                            }
                            else {
                                //启用，不处理
                                Log.d("locationBeanString: $locationBeanString");
                            }
                        }
                    }
                }
            }
        }
    }
}

public fun PackageParam.hookMainPresenter(classLoader: ClassLoader) {
    Log.d("fun start hookMainPresenter")

    findClass("com.cmict.oa.activity.home.presenter.MainPresenter", classLoader).hook {
        injectMember {
            method {
                name ="addShowRecord"
                param(StringClass)
                beforeHook {
                    val locationBeanString = this.args(0).string()
                    Log.d("MainPresenter.addShowRecord: $locationBeanString")
                }

            }
        }
    }
}
public fun PackageParam.hookCustomWebviewActivity(classLoader: ClassLoader) {
    Log.d("fun start hookCustomWebviewActivity")

    findClass("com.cmict.oa.activity.work.CustomWebviewActivity", classLoader).hook {
        injectMember {
            method {
                name = "getLocationCallBack"
//                param(DoubleClass, DoubleClass, "com.cmict.oa.bean.PlaceBean".toClass(classLoader))
                paramCount(3)
                beforeHook {
                    Log.d("CustomWebviewActivity.getLocationCallBack befor hook")
                    val lon = this.args(0).double()
                    val lat = this.args(1).double()
                    val place = this.args(2).any()
                    if(place != null) {
                        Log.d("place class is ${place.javaClass}")

                        val recordLocation = RecordLocationBean.from(place).setLonLat(lon,lat);

                        tryAddCurrentLocation(recordLocation);
                        Toast.makeText(this.instance as Context, recordLocation.POIName,Toast.LENGTH_SHORT).show();
                    }
                }
            }


        }

        injectMember {
            method {
                name = "loadUrl"
                param(StringClass)
                beforeHook {
                    val url = this.args(0).string();

                    Log.d("CustomWebviewActivity loadUrl $url");


//                    val fab = FloatingActionButton(this.instance as Context)
//                    fab.show();

                }
            }
        }
    }
}

public fun PackageParam.tryAddCurrentLocation(recordLocation: RecordLocationBean) {

    val recordLocationSet = prefs.get(DataConst.om_recordLocationSet)

    recordLocationSet.add(recordLocation)
     prefs.edit {

        put(DataConst.om_recordLocationSet, recordLocationSet)
     }

    val poiNames = recordLocationSet.map { it.POIName }.toList()
    dataChannel.put(key = "recordLocationChange", value = poiNames)
    Log.d("通知更新UI $poiNames")

    // 发送给模块
//    val placeBean = recordLocation.toThatBean("com.cmict.oa.bean.PlaceBean".toClass(classLoader));
//    val reportBean = recordLocation.toThatBean("com.cmict.oa.bean.MsgRecordLocationBean".toClass(classLoader));

////
////
//
//    Log.d("是否禁用 ${prefs.get(DataConst.om_disableReportLocation )}")
//    Log.d("当前位置 ${prefs.get(DataConst.om_currentLocation )}")
//    Log.d("更新位置 ${gson.toJson(recordLocationSet)}")
//
//    val locationSet = prefs.get(DataConst.om_recordLocationSet)
//    Log.d("读取位置${locationSet}")
////    val gson = Gson()
////    Log.d("recordLocation: ${gson.toJson(recordLocation)}")
////    Log.d("placeBean: ${gson.toJson(placeBean)}")
////    Log.d("reportBean: ${gson.toJson(reportBean)}")
//    dataChannel.put(key = "recordLocationChange", value = true)
//    Log.d("通知更新UI")
}


