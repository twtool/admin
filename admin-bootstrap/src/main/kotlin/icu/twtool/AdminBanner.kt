package icu.twtool

import icu.twtool.common.TwtoolAdminVersion
import org.springframework.boot.Banner
import org.springframework.boot.SpringBootVersion
import org.springframework.boot.ansi.AnsiColor
import org.springframework.boot.ansi.AnsiOutput
import org.springframework.boot.ansi.AnsiStyle
import org.springframework.core.env.Environment
import java.io.PrintStream

/**
 * Twtool Admin Banner 实现
 *
 * @author wen-flower
 * @since 2024-06-04
 */
class AdminBanner : Banner {

    companion object {

        const val BANNER = """            
    __ __ _______         _                 _                _             _        __ __
   / // /|__   __|       | |               | |     /\       | |           (_)       \ \\ \
  / // /    | |__      __| |_  ___    ___  | |    /  \    __| | _ __ ___   _  _ __   \ \\ \
 < << <     | |\ \ /\ / /| __|/ _ \  / _ \ | |   / /\ \  / _` || '_ ` _ \ | || '_ \   > >> >
  \ \\ \    | | \ V  V / | |_| (_) || (_) || |  / ____ \| (_| || | | | | || || | | | / // /
   \_\\_\   |_|  \_/\_/   \__|\___/  \___/ |_| /_/    \_\\__,_||_| |_| |_||_||_| |_|/_//_/
        """

        const val TWTOOL_ADMIN = " :: Twtool Admin :: "
        const val SPRING_BOOT = " :: Spring Boot :: "

        const val STRAP_LINE_SIZE = 91
    }

    override fun printBanner(environment: Environment, sourceClass: Class<*>, out: PrintStream) {
        out.println(BANNER)
        printVersion(out, SPRING_BOOT, SpringBootVersion.getVersion())
        printVersion(out, TWTOOL_ADMIN, TwtoolAdminVersion.VERSION)
    }

    private fun printVersion(out: PrintStream, name: String, ver: String) {
        val version = String.format(" (v%s)", ver)

        out.println(
            AnsiOutput.toString(
                AnsiColor.GREEN, name,
                AnsiColor.DEFAULT, buildString {
                    repeat(STRAP_LINE_SIZE - (version.length + name.length)) { append(' ') }
                },
                AnsiStyle.FAINT, version
            )
        )
    }
}