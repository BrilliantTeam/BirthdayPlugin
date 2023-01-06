package tw.brilliant.server.plugins.birthdayplugin.config

import tw.brilliant.server.plugins.birthdayplugin.util.Util
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object BirthdayConfig {
    private val file: File = Util.getFileLocation("config.yml")
    private val configuration = YamlConfiguration.loadConfiguration(file)

    val giftCommands: MutableList<String>
        get() = configuration.getStringList("gift_commands")

    val discordSrv: Boolean
        get() = configuration.getBoolean("discord_srv", true)

    val joinServerGiftDelay: Int
        get() = configuration.getInt("join_server_gift_delay", 0)

    val giveGiftMessage: String
        get() = configuration.getString("give_gift_message", "&aHappy birthday! Here is your gift!")!!

    val giveGiftInventoryFullMessage: String
        get() = configuration.getString(
            "give_gift_inventory_full_message",
            "&cYour inventory is full, I can't give you a birthday gift. Please use [/btd gift] to get after sorting out the inventory."
        )!!

    val giveGiftRepeatMessage: String
        get() = configuration.getString(
            "give_gift_repeat_message",
            "&cYou have already received your birthday gift today. Don't try to use some tricks to get it again!"
        )!!

    val personalGreetingsMessage: String
        get() = configuration.getString(
            "server_greetings_message",
            "&aWe wish you a happy birthday! &7(You can use [/btd gs off] to turn off personal greetings)"
        )!!

    val serverAnnouncementMessage: String
        get() = configuration.getString(
            "server_announcement_message",
            "&6Today is %playerTitle_use% &a%essentials_nickname% &6's birthday, wish him/her a happy birthday! &7(You can use [/btd at off] to turn off server announcements)"
        )!!

    val serverAnnouncementMessageDiscord: String
        get() = configuration.getString(
            "server_announcement_message_discord",
            "Today is %playerTitle_use% %essentials_nickname% 's birthday, wish him/her a happy birthday!`"
        )!!

    fun init() {
        if (!configuration.isSet("gift_commands")) {
            configuration.set("gift_commands", listOf("minecraft:give %player% minecraft:diamond 1"))
            configuration.setInlineComments(
                "gift_commands", arrayListOf("The gift given to the player on his birthday (command)")
            )
        }

        if (!configuration.isSet("discord_srv")) {
            configuration.set("discord_srv", discordSrv)
            configuration.setInlineComments(
                "discord_srv", arrayListOf("Whether to send the birthday message to the Discord server (discord srv)")
            )
        }

        if (!configuration.isSet("join_server_gift_delay")) {
            configuration.set("join_server_gift_delay", joinServerGiftDelay)
            configuration.setInlineComments(
                "join_server_gift_delay",
                arrayListOf("The delay in seconds before the join server birthday gift is given")
            )
        }

        if (!configuration.isSet("give_gift_message")) {
            configuration.set("give_gift_message", giveGiftMessage)
            configuration.setInlineComments(
                "give_gift_message",
                arrayListOf("The message sent to the player when the birthday gift is given")
            )
        }

        if (!configuration.isSet("give_gift_inventory_full_message")) {
            configuration.set("give_gift_inventory_full_message", giveGiftInventoryFullMessage)
            configuration.setInlineComments(
                "give_gift_inventory_full_message",
                arrayListOf("The message sent to the player when the birthday gift is not given because the inventory is full")
            )
        }

        if (!configuration.isSet("give_gift_repeat_message")) {
            configuration.set("give_gift_repeat_message", giveGiftRepeatMessage)
            configuration.setInlineComments(
                "give_gift_repeat_message",
                arrayListOf("The message sent to the player when the birthday gift is not given because the player has already received it")
            )
        }

        if (!configuration.isSet("personal_greetings_message")) {
            configuration.set("personal_greetings_message", personalGreetingsMessage)
            configuration.setInlineComments(
                "personal_greetings_message",
                arrayListOf("The message sent to the player when he/she joins the server on his/her birthday")
            )
        }

        if (!configuration.isSet("server_announcement_message")) {
            configuration.set("server_announcement_message", serverAnnouncementMessage)
            configuration.setInlineComments(
                "server_announcement_message",
                arrayListOf("The message sent to the server when a player joins the server on his/her birthday")
            )
        }

        if (!configuration.isSet("server_announcement_message_discord")) {
            configuration.set("server_announcement_message_discord", serverAnnouncementMessageDiscord)
            configuration.setInlineComments(
                "server_announcement_message_discord",
                arrayListOf("The message sent to the Discord server when a player joins the server on his/her birthday")
            )
        }

        save()
    }

    fun reload() {
        configuration.load(file)
    }

    fun save() {
        configuration.save(file)
    }
}