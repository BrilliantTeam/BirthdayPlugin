package tw.brilliant.server.plugins.birthdayplugin.model

import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayConfig
import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayStorage
import tw.brilliant.server.plugins.birthdayplugin.util.Util
import org.bukkit.Bukkit
import org.bukkit.configuration.MemorySection
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.entity.Player
import java.util.*

@SerializableAs("BirthdayData")
data class BirthdayData(
    private val playerUUID: String,
    val calendar: Calendar?,
    val lastReceiveGift: Date?,
    val lastReceiveIp: String?,
    val greetings: Boolean,
    val announcement: Boolean
) : ConfigurationSerializable {
    companion object {
        fun deserialize(section: MemorySection): BirthdayData? {
            val calendar = Util.getTaipeiCalendar()
            val timestamp = section.getLong("calendar")

            if (timestamp == 0L) return null
            calendar.timeInMillis = timestamp

            return BirthdayData(
                section.getString("player_uuid")!!,
                calendar,
                Date(section.getLong("last_receive_gift")),
                section.getString("last_receive_ip"),
                section.getBoolean("greetings", true),
                section.getBoolean("announcement", true)
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        map["player_uuid"] = playerUUID

        if (calendar != null) {
            map["calendar"] = calendar.timeInMillis
        }
        if (lastReceiveGift != null) {
            map["last_receive_gift"] = lastReceiveGift.time
        }
        if (lastReceiveIp != null) {
            map["last_receive_ip"] = lastReceiveIp
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

    fun giveGift(player: Player, autoGift: Boolean) {
        val giftCommands = BirthdayConfig.giftCommands

        if (receivedGift(player)) {
            if (!autoGift) {
                Util.sendSystemMessage(player, BirthdayConfig.giveGiftRepeatMessage)
            }
            return
        }

        if (!Util.hasAvailableSlot(player, giftCommands.filter { it.contains("minecraft:give") }.size)) {
            Util.sendSystemMessage(
                player, BirthdayConfig.giveGiftInventoryFullMessage
            )
            return
        }

        for (giftCommand in giftCommands) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), giftCommand.replace("%player%", player.name))
        }
        BirthdayStorage.set(
            player, copy(lastReceiveGift = Date(), lastReceiveIp = player.address?.address?.hostAddress)
        )

        Util.sendSystemMessage(player, BirthdayConfig.giveGiftMessage)
    }

    private fun receivedGift(player: Player): Boolean {
        val data = player.address?.address?.hostAddress?.let { BirthdayStorage.getByIp(it) }
        val lastReceiveGift = data?.lastReceiveGift

        return if (lastReceiveGift != null) {
            val lastYear = Util.getTaipeiCalendar().apply {
                add(Calendar.YEAR, -1)
            }

            lastReceiveGift.after(lastYear.time)
        } else {
            false
        }
    }
}
