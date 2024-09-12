package tw.brilliant.server.plugins.birthdayplugin

import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayConfig
import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayStorage
import tw.brilliant.server.plugins.birthdayplugin.event.EventListener
import tw.brilliant.server.plugins.birthdayplugin.model.BirthdayData
import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

@Suppress("unused")
class BirthdayPlugin : JavaPlugin() {
    companion object {
        val LOGGER: Logger = Logger.getLogger("BirthdayPlugin")
        lateinit var INSTANCE: Plugin
    }

    override fun onEnable() {
        INSTANCE = this
        BirthdayConfig.init()

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            LOGGER.severe("This plugin requires PlaceholderAPI to work!")
        }

        val birthdayCommand = this.getCommand("btd")
        if (birthdayCommand == null) {
            LOGGER.severe("Command btd is not found")
        } else {
            birthdayCommand.setExecutor(BirthdayCommandExecutor())
            birthdayCommand.description = "輝煌伺服器生日系統指令（/btd help）"
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