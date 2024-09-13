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
        get() = configuration.getInt("join_server_gift_delay", 10)

    val giveGiftMessage: String
        get() = configuration.getString("give_gift_message", "&7｜&6系統&7｜&f飯娘：&7輝煌伺服器祝您生日快樂！已將生日小禮品放到您的背包囉！")!!

    val giveGiftInventoryFullMessage: String
        get() = configuration.getString(
            "give_gift_inventory_full_message",
            "&7｜&6系統&7｜&f飯娘：&7輝煌伺服器祝您生日快樂！但是您的背包滿了，請清理後輸入&e[/btd gift]&7再領取生日小禮品！"
        )!!

    val giveGiftRepeatMessage: String
        get() = configuration.getString(
            "give_gift_repeat_message",
            "&7｜&6系統&7｜&f飯娘：&7您已經領取過生日禮物了呢！"
        )!!

    val personalGreetingsMessage: String
        get() = configuration.getString(
            "server_greetings_message",
            "&7｜&6系統&7｜&f飯娘：&7輝煌伺服器祝您生日快樂！感謝您遊玩輝煌！&8（可使用&e[/btd gs off]&8關閉生日通知）"
        )!!

    val serverAnnouncementMessage: String
        get() = configuration.getString(
            "server_announcement_message",
            "&7｜&6系統&7｜&f飯娘：&7今天是 %tab_tabsuffix% 的生日，祝他生日快樂吧！&8（可使用&e[/btd at off]&8關閉生日通知）"
        )!!

    val serverAnnouncementMessageDiscord: String
        get() = configuration.getString(
            "server_announcement_message_discord",
            "> -# 今天是 [%playerTitle_use%] %tab_tabsuffix% 的生日，祝他生日快樂吧！ <@&1283751877389058109>"
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
