package engineer.skyouo.plugins.birthdayplugin.config

import engineer.skyouo.plugins.birthdayplugin.BirthdayPlugin
import engineer.skyouo.plugins.birthdayplugin.model.BirthdayData
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.file.YamlConfiguration
import java.util.Date

object BirthdayStorage {
    private val file = Util.getFileLocation("data.yml")
    private val configuration = YamlConfiguration.loadConfiguration(file)

    fun get(player: OfflinePlayer): BirthdayData? {
        return try {
            val data = configuration.get(player.uniqueId.toString())

            if (data is Map<*, *>) {
                BirthdayData(
                    data["player_uuid"] as String,
                    Date(data["birthday"] as Long),
                    data["receive_gift"] as Boolean
                )
            } else {
                null
            }
        } catch (e: Exception) {
            BirthdayPlugin.LOGGER.warning("Failed to get birthday data for ${player.name}")
            null
        }
    }

    fun set(player: OfflinePlayer, data: BirthdayData) {
        configuration.set(player.uniqueId.toString(), data.serialize())
        save()
    }

    fun save() {
        configuration.save(file)
    }
}