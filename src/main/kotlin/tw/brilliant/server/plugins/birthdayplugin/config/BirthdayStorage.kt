package tw.brilliant.server.plugins.birthdayplugin.config

import tw.brilliant.server.plugins.birthdayplugin.BirthdayPlugin
import tw.brilliant.server.plugins.birthdayplugin.model.BirthdayData
import tw.brilliant.server.plugins.birthdayplugin.util.Util
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
                BirthdayData.deserialize(data) ?: setDefault(player)
            } else {
                setDefault(player)
            }
        } catch (e: Exception) {
            BirthdayPlugin.LOGGER.warning("Failed to get birthday data for player:$uuid (${e.message})")
            setDefault(player)
        }
    }

    fun getByIp(ip: String): BirthdayData? {
        val dataList = configuration.getValues(false)
        val result = dataList.filter { it.value is MemorySection }.map { it.value as MemorySection }
            .firstOrNull { it.getString("last_receive_ip") == ip }

        return result?.let { BirthdayData.deserialize(it) }
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
        val default = BirthdayData(player.uniqueId.toString(), null, null, null, greetings = true, announcement = true)
        set(player, default)
        return default
    }
}