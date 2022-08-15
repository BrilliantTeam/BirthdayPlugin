package engineer.skyouo.plugins.birthdayplugin.model

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayConfig
import engineer.skyouo.plugins.birthdayplugin.config.BirthdayStorage
import engineer.skyouo.plugins.birthdayplugin.util.Util
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

    fun canGiveGift(): Boolean {
        return if (todayIsBirthday()) {
            val yesterday = Date()
            yesterday.time -= 24 * 60 * 60 * 1000

            !(lastReceiveGift != null && lastReceiveGift.after(yesterday))
        } else {
            false
        }
    }

    fun giveGift(player: Player) {
        val giftCommands = BirthdayConfig.giftCommands

        val emptySpace = player.inventory.maxStackSize - player.inventory.size

        if (emptySpace < giftCommands.size) {
            Util.sendSystemMessage(
                player,
                "&c您的背包滿了，我不能給您生日禮物 ._.，請空出 ${giftCommands.size - emptySpace} 格空間後請使用 [/btd gift] 領取~"
            )
            return
        }

        for (giftCommand in giftCommands) {
            player.server.dispatchCommand(player.server.consoleSender, giftCommand.replace("%player%", player.name))
        }
        BirthdayStorage.set(player, copy(lastReceiveGift = Date()))

        Util.sendSystemMessage(player, "&a生日快樂！這是您的生日禮物 ._.")
    }
}