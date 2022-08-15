package engineer.skyouo.plugins.birthdayplugin.model

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*


@SerializableAs("BirthdayData")
data class BirthdayData(private val playerUUID: String, val birthday: Date, val receiveGift: Boolean) :
    ConfigurationSerializable {
    val offlinePlayer: OfflinePlayer
        get() = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID))

    override fun serialize(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        map["player_uuid"] = playerUUID
        map["birthday"] = birthday.time
        map["receive_gift"] = receiveGift
        return map
    }
}