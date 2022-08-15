package engineer.skyouo.plugins.birthdayplugin.model

enum class BirthdayCommand(val command: String, val admin: Boolean = false) {
    Set("set"),
    Greetings("gs"),
    At("at"),
    Gift("gift"),
    Help("help"),
    GiftSet("giftset", true),
}