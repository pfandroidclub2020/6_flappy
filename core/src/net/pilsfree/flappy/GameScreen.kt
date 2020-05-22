package net.pilsfree.flappy

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import ktx.app.KtxScreen
import ktx.graphics.use
import net.pilsfree.flappy.sprites.Bird

class GameScreen(val game: FlappyGame) : KtxScreen {

    val bird = Bird(30f,200f)
    val camera = OrthographicCamera().also {
        it.setToOrtho(false,Consts.WIDTH.toFloat()/2f,Consts.HEIGHT.toFloat()/2f)
    }

    override fun show() {
        game.music.volume = 0.05f
    }

    override fun render(delta: Float) {
        camera.update()
        bird.update(delta)
        game.batch.projectionMatrix = camera.combined
        game.batch.use {
            it.draw(game.bg,0f,0f,Consts.WIDTH.toFloat(),Consts.HEIGHT.toFloat())
            it.draw(bird.texture,bird.position.x,bird.position.y)
        }

        if (bird.position.y < 0f) {
            die()
        }

        if (Gdx.input.justTouched()) {
            bird.jump()
        }
    }

    private fun die() {
        game.sfxDead.play()
        game.addScreen(MenuScreen(game))
        game.setScreen<MenuScreen>()
        game.removeScreen<GameScreen>()
        dispose()
    }

    override fun dispose() {
        super.dispose()
        bird.dispose()
    }
}