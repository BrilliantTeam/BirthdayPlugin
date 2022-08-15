package engineer.skyouo.plugins.birthdayplugin.model

import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*

@SerializableAs("BirthdayData")
data class BirthdayData(
    private val playerUUID: String,
    val calendar: Calendar?,
    val lastReceiveGift: Boolean,
    val greetings: Boolean,
    val announcement: Boolean
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        map["player_uuid"] = playerUUID
        if (calendar != null) {
            map["calendar"] = calendar.timeInMillis
        }
        map["last_receive_gift"] = lastReceiveGift
        map["greetings"] = greetings
        map["announcement"] = announcement

        return map
    }

    fun todayIsBirthday(): Boolean {
        if (calendar == null) return false

        val now = Util.getTaipeiCalendar()

        val isSameMonth = calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH)
        val isSameDay = calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)

        return isSameMonth && isSameDay
    }
}