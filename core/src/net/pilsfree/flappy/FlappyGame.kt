package net.pilsfree.flappy

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxGame
import ktx.app.KtxScreen

class FlappyGame : KtxGame<KtxScreen>() {
    val batch by lazy { SpriteBatch() }
    val font by lazy {
        BitmapFont(Gdx.files.internal("flappy.fnt"),Gdx.files.internal("flappy.png"),false).also {
            it.setUseIntegerPositions(false)
        }
    }
    val preferences by lazy { Gdx.app.getPreferences("data") }
    val bg by lazy { Texture("bg.png") }
    lateinit var sfxDead : Sound
    lateinit var music : Music

    override fun create() {
        Consts.HEIGHT = ((Gdx.graphics.height.toFloat() / Gdx.graphics.width.toFloat()) * Consts.WIDTH.toFloat()).toInt()
        sfxDead = Gdx.audio.newSound(Gdx.files.internal("dead.ogg"))
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"))
        music.isLooping = true
        //music.play()
        addScreen(MenuScreen(this))
        setScreen<MenuScreen>()
        super.create()
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()
        bg.dispose()
        sfxDead.dispose()
        music.stop()
        music.dispose()
        font.dispose()
    }
}