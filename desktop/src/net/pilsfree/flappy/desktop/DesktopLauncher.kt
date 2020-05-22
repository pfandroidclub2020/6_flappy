package net.pilsfree.flappy.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import net.pilsfree.flappy.Consts
import net.pilsfree.flappy.FlappyGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = Consts.WIDTH
        config.height = Consts.HEIGHT
        LwjglApplication(FlappyGame(), config)
    }
}