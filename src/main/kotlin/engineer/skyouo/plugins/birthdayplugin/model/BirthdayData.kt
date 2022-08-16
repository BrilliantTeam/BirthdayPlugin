package engineer.skyouo.plugins.birthdayplugin.model

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayConfig
import engineer.skyouo.plugins.birthdayplugin.config.BirthdayStorage
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.entity.Player
import java.util.*

@SerializableAs("BirthdayData")
data class BirthdayData(
    private val playerUUID: String,
    val calendar: Calendar?,
    val lastReceiveGift: Date?,
    val greetings: Boolean,
    val announcement: Boolean
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        map["player_uuid"] = playerUUID

        if (calendar != null) {
            map["calendar"] = calendar.timeInMillis
        }
        if (lastReceiveGift != null) {
            map["last_receive_gift"] = lastReceiveGift.time
        }

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

    fun canGiveGift(isOp: Boolean): Boolean {
        return if (todayIsBirthday()) {
            if (isOp) return true
            val yesterday = Date()
            yesterday.time -= 24 * 60 * 60 * 1000

            !(lastReceiveGift != null && lastReceiveGift.after(yesterday))
        } else {
            false
        }
    }

    fun giveGift(player: Player) {
        val giftCommands = BirthdayConfig.giftCommands

        if (!Util.hasAvailableSlot(player, giftCommands.filter { it.contains("minecraft:give") }.size)) {
            Util.sendSystemMessage(
                player,
                "&c您的背包滿了，我不能給您生日禮物 ._.，整理出空間後請使用 [/btd gift] 領取~"
            )
            return
        }

        for (giftCommand in giftCommands) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), giftCommand.replace("%player%", player.name))
        }
        BirthdayStorage.set(player, copy(lastReceiveGift = Date()))

        Util.sendSystemMessage(player, "&a生日快樂！這是您的生日禮物 :D")
    }
}
