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
    private var configuration = YamlConfiguration.loadConfiguration(file)

    fun get(player: OfflinePlayer): BirthdayData {
        val uuid = player.uniqueId.toString()

        return try {
            val data = configuration.get(uuid)

            if (data is MemorySection) {
                val calendar = Util.getTaipeiCalendar()
                val timestamp = data.getLong("calendar")
                
                if (timestamp == 0L) return setDefault(player)

                calendar.timeInMillis = timestamp

                BirthdayData(
                    data.getString("player_uuid", uuid)!!,
                    calendar,
                    Date(data.getLong("last_receive_gift")),
                    data.getBoolean("greetings", true),
                    data.getBoolean("announcement", true)
                )
            } else {
                setDefault(player)
            }
        } catch (e: Exception) {
            BirthdayPlugin.LOGGER.warning("Failed to get birthday data for player:$uuid (${e.message})")
            setDefault(player)
        }
    }

    fun set(player: OfflinePlayer, data: BirthdayData) {
        val uuid = player.uniqueId.toString()

        configuration.set(uuid, data.serialize())
        save()
        reload()
    }

    fun reload() {
        configuration = YamlConfiguration.loadConfiguration(file)
    }

    fun save() {
        configuration.save(file)
    }

    private fun setDefault(player: OfflinePlayer): BirthdayData {
        val default = BirthdayData(player.uniqueId.toString(), null, null, greetings = true, announcement = true)
        set(player, default)
        return default
    }
}