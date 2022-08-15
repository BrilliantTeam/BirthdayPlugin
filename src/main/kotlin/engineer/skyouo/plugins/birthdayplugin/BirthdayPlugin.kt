package engineer.skyouo.plugins.birthdayplugin

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayConfig
import engineer.skyouo.plugins.birthdayplugin.config.BirthdayStorage
import engineer.skyouo.plugins.birthdayplugin.event.EventListener
import engineer.skyouo.plugins.birthdayplugin.model.BirthdayData
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

@Suppress("unused")
class BirthdayPlugin : JavaPlugin() {
    companion object {
        val LOGGER: Logger = Logger.getLogger("BirthdayPlugin")
    }

    override fun onEnable() {
        BirthdayConfig.init()

        val birthdayCommand = this.getCommand("btd")
        if (birthdayCommand == null) {
            LOGGER.severe("Command btd is not found")
        } else {
            birthdayCommand.setExecutor(BirthdayCommandExecutor())
            birthdayCommand.description = "輝煌伺服器生日系統指令 (btd /help)"
            LOGGER.info("Register btd command successful")
        }

        server.pluginManager.registerEvents(EventListener(), this)
        ConfigurationSerialization.registerClass(BirthdayData::class.java, "BirthdayData")

        LOGGER.info("BirthdayPlugin is enabled")
    }

    override fun onDisable() {
        BirthdayConfig.save()
        BirthdayStorage.save()

        LOGGER.info("BirthdayPlugin is disabled")
    }
}