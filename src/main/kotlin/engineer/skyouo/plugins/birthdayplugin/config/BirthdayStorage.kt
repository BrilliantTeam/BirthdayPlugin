package engineer.skyouo.plugins.birthdayplugin.config

import engineer.skyouo.plugins.birthdayplugin.BirthdayPlugin
import engineer.skyouo.plugins.birthdayplugin.model.BirthdayData
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.MemorySection
import org.bukkit.configuration.file.YamlConfiguration
import java.util.Date

object BirthdayStorage {
    private val file = Util.getFileLocation("data.yml")
    private val configuration = YamlConfiguration.loadConfiguration(file)

    fun get(player: OfflinePlayer): BirthdayData {
        val uuid = player.uniqueId.toString()

        return try {
            val data = configuration.get(uuid)

            if (data is MemorySection) {
                val calendar = Util.getTaipeiCalendar()
                calendar.timeInMillis = data.getLong("calendar")

                BirthdayData(
                    data.getString("player_uuid", uuid)!!,
                    calendar,
                    Date(data.getLong("last_receive_gift")),
                    data.getBoolean("greetings", true),
                    data.getBoolean("announcement", true)
                )
            } else {
                val default = getDefault(uuid)
                set(player, default)
                default
            }
        } catch (e: Exception) {
            BirthdayPlugin.LOGGER.warning("Failed to get birthday data for player:$uuid (${e.message})")
            val default = getDefault(uuid)
            set(player, default)
            default
        }
    }

    fun set(player: OfflinePlayer, data: BirthdayData) {
        val uuid = player.uniqueId.toString()

        configuration.set(uuid, data.serialize())
        save()
        reload()
    }

    fun reload() {
        configuration.load(file)
    }

    fun save() {
        configuration.save(file)
    }

    private fun getDefault(uuid: String): BirthdayData {
        return BirthdayData(uuid, null, null, greetings = true, announcement = true)
    }
}