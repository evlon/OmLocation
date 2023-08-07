package me.evlon.project.bean

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.highcapable.yukihookapi.hook.factory.toClass

class ChannelCmd (val cmd : String, val jsonBody : String)

class RecordLocationBean (
    var longitude : Double,
    var latitude : Double,
    var POIName: String? = "",
    var adcode: String?= "",
    var city: String?= "",
    var citycode: String?= "",
    var country: String? = "",
    var district: String? = "",
    var formattedAddress: String? = "",
    var number: String? = "",
    var province: String?= "",
    var street: String? = ""
    )
{
    public fun toMsgRecordLocationBean(){
    }

    fun setLonLat(lon: Double, lat: Double): RecordLocationBean {
        longitude = lon
        latitude = lat
        return this;
    }

    fun toThatBean(clazz :  Class<*>) : Any? {
        val gson = Gson()
        val gsonString = gson.toJson(this)
        val ret = gson.fromJson(gsonString, clazz)
        return ret
    }

    override fun hashCode(): Int {
        if(this.POIName == null){
            return super.hashCode();
        }

        return this.POIName.hashCode()
    }

    companion object {
        fun from(gsonLikeObject: Any): RecordLocationBean {
            val gson = Gson()
            val gsonString = gson.toJson(gsonLikeObject)
            val ret = gson.fromJson(gsonString, RecordLocationBean::class.java)

            return ret;
        }
    }
}



