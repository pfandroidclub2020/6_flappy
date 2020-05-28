package net.pilsfree.flappy

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import ktx.app.KtxScreen
import ktx.graphics.use
import net.pilsfree.flappy.sprites.Bird
import net.pilsfree.flappy.sprites.Tubes

class GameScreen(val game: FlappyGame) : KtxScreen {

    val bird = Bird(30f,200f)
    val camera = OrthographicCamera().also {
        it.setToOrtho(false,Consts.WIDTH.toFloat()/2f,Consts.HEIGHT.toFloat()/2f)
    }
    val tubes = Tubes()

    override fun show() {
        game.music.volume = 0.05f
    }

    override fun render(delta: Float) {
        bird.update(delta)
        camera.position.x = bird.position.x + 80
        camera.update()
        tubes.update(camera)

        game.batch.projectionMatrix = camera.combined
        game.batch.use {
            it.draw(game.bg,camera.position.x-camera.viewportWidth/2,0f,Consts.WIDTH.toFloat(),Consts.HEIGHT.toFloat())
            tubes.render(it)
            bird.render(it)
        }

        if (bird.position.y < 0f || bird.position.y > camera.viewportHeight || tubes.collides(bird.rectangle())) {
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
        tubes.dispose()
    }
}