package engineer.skyouo.plugins.birthdayplugin

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

@Suppress("unused")
class BirthdayPlugin : JavaPlugin() {
    companion object {
        val LOGGER: Logger = Logger.getLogger("BirthdayPlugin")
    }

    override fun onEnable() {
        LOGGER.info("BirthdayPlugin is enabled")

        val birthdayCommand = this.getCommand("btd")
        if (birthdayCommand == null) {
            LOGGER.severe("Command btd is not found")
        } else {
            birthdayCommand.setExecutor(BirthdayCommandExecutor())
            birthdayCommand.description = "輝煌伺服器生日系統指令 (btd /help)"
        }
    }

    override fun onDisable() {
        LOGGER.info("BirthdayPlugin is disabled")
    }
}