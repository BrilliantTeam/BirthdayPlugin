package engineer.skyouo.plugins.birthdayplugin.model

import engineer.skyouo.plugins.birthdayplugin.BirthdayPlugin
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*

@SerializableAs("BirthdayData")
data class BirthdayData(private val playerUUID: String, val calendar: Calendar, val receiveGift: Boolean) :
    ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        map["player_uuid"] = playerUUID
        map["calendar"] = calendar.timeInMillis
        map["receive_gift"] = receiveGift
        return map
    }

    fun todayIsBirthday(): Boolean {
        val now = Util.getTaipeiCalendar()

        val isSameMonth = calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH)
        val isSameDay = calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)

        BirthdayPlugin.LOGGER.info("isSameMonth: $isSameMonth, isSameDay: $isSameDay")

        return isSameMonth && isSameDay
    }
}