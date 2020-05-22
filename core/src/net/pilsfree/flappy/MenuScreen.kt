package net.pilsfree.flappy

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector3
import ktx.app.KtxScreen
import ktx.graphics.use

class MenuScreen(val game: FlappyGame) : KtxScreen {
    val button by lazy { Texture("playbtn.png") }
    val camera = OrthographicCamera().also {
        it.setToOrtho(false,Consts.WIDTH.toFloat(),Consts.HEIGHT.toFloat())
    }


    override fun show() {
        game.music.volume = 0.5f
    }

    override fun render(delta: Float) {
        camera.update()
        game.batch.projectionMatrix = camera.combined
        game.batch.use {
            it.draw(game.bg,0f,0f,Consts.WIDTH.toFloat(),Consts.HEIGHT.toFloat())
            it.draw(button,Consts.WIDTH/2f-button.width/2f,Consts.HEIGHT/2f-button.height/2f)
        }

        if (Gdx.input.justTouched()) {
            val touchCoords = Vector3(Gdx.input.x.toFloat(),Gdx.input.y.toFloat(),0f)
            camera.unproject(touchCoords)
            if (touchCoords.x in Consts.WIDTH/2f-button.width/2f .. Consts.WIDTH/2f+button.width/2f
                    && touchCoords.y in Consts.HEIGHT/2f-button.height/2f .. Consts.HEIGHT/2f+button.height/2f) {
                startGame()
            }
        }
    }

    fun startGame() {
        game.addScreen(GameScreen(game))
        game.setScreen<GameScreen>()
        game.removeScreen<MenuScreen>()
        dispose()
    }

    override fun dispose() {
        super.dispose()
        button.dispose()
    }
}