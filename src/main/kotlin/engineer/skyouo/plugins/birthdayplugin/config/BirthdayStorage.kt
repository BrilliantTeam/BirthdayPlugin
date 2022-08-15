package engineer.skyouo.plugins.birthdayplugin.config

import engineer.skyouo.plugins.birthdayplugin.BirthdayPlugin
import engineer.skyouo.plugins.birthdayplugin.model.BirthdayData
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.MemorySection
import org.bukkit.configuration.file.YamlConfiguration
import java.util.*

object BirthdayStorage {
    private val file = Util.getFileLocation("data.yml")
    private val configuration = YamlConfiguration.loadConfiguration(file)

    fun get(uuid: String): BirthdayData? {
        return try {
            val data = configuration.get(uuid)

            if (data is MemorySection) {
                val calendar = Util.getTaipeiCalendar()
                calendar.timeInMillis = data.getLong("calendar")

                BirthdayData(
                    data.getString("player_uuid", uuid)!!,
                    calendar,
                    data.getBoolean("receive_gift")
                )
            } else {
                null
            }
        } catch (e: Exception) {
            BirthdayPlugin.LOGGER.warning("Failed to get birthday data for player:$uuid (${e.message})")
            null
        }
    }

    fun set(player: OfflinePlayer, calendar: Calendar, receiveGift: Boolean) {
        val uuid = player.uniqueId.toString()

        configuration.set(uuid, BirthdayData(uuid, calendar, receiveGift).serialize())
        save()
        reload()
    }

    fun reload() {
        configuration.load(file)
    }

    fun save() {
        configuration.save(file)
    }
}