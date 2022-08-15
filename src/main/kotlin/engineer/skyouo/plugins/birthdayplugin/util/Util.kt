package engineer.skyouo.plugins.birthdayplugin.util

import java.io.File

object Util {
    private fun getBaseDirectory(): File {
        return File("plugins/BirthdayPlugin")
    }

    fun getFileLocation(fileName: String): File {
        val baseDirectory: File = getBaseDirectory()
        if (!baseDirectory.exists()) {
            baseDirectory.mkdirs()
        }

        return baseDirectory.resolve(fileName)
    }
}