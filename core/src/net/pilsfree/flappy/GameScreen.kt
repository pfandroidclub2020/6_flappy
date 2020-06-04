package net.pilsfree.flappy

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import ktx.app.KtxScreen
import ktx.graphics.use
import net.pilsfree.flappy.sprites.BG
import net.pilsfree.flappy.sprites.Bird
import net.pilsfree.flappy.sprites.Ground
import net.pilsfree.flappy.sprites.Tubes

class GameScreen(val game: FlappyGame) : KtxScreen {

    val bird = Bird(30f,200f)
    val camera = OrthographicCamera().also {
        it.setToOrtho(false,Consts.WIDTH.toFloat()/2f,Consts.HEIGHT.toFloat()/2f)
    }
    val tubes = Tubes()
    val ground = Ground()
    var lastScore = 0
    val sfxScore = Gdx.audio.newSound(Gdx.files.internal("score.mp3"))
    val bg = BG(game.bg)



    override fun show() {
        game.music.volume = 0.05f
    }

    override fun render(delta: Float) {
        bird.update(delta)
        camera.position.x = bird.position.x + 80
        camera.update()
        tubes.update(camera)
        ground.update(camera)
        bg.update(camera)

        val newScore = tubes.score(bird.position.x+bird.texture.width/2)
        if (newScore > lastScore) {
            lastScore = newScore
            sfxScore.play()
        }
        val scoreSize = GlyphLayout().also {
            it.setText(game.font,newScore.toString())
        }


        game.batch.projectionMatrix = camera.combined
        game.batch.use {
            bg.render(it)
            tubes.render(it)
            ground.render(it)
            bird.render(it)
            game.font.draw(it,newScore.toString(),camera.position.x-scoreSize.width/2,
                    camera.viewportHeight - 30 - scoreSize.height)

        }

        if (bird.position.y <= Ground.BOTTOM || bird.position.y > camera.viewportHeight || tubes.collides(bird.rectangle())) {
            if (!bird.isDead)  {
                die()
                val savedScore = game.preferences.getInteger(Consts.HIGHSCORE_KEY,Consts.HIGHSCORE_DEFAULT)
                if (savedScore < newScore) {
                    game.preferences.putInteger(Consts.HIGHSCORE_KEY,newScore)
                    game.preferences.flush()
                }
            }
        }

        if (Gdx.input.justTouched()) {
            if (bird.isDead) {
                backToMenu()
            } else {
                bird.jump()
            }
        }
    }

    private fun die() {
        game.sfxDead.play()
        bird.velocity.x = 0f
        bird.velocity.y = 0f
        bird.isDead = true
    }

    private fun backToMenu() {
        game.addScreen(MenuScreen(game))
        game.setScreen<MenuScreen>()
        game.removeScreen<GameScreen>()
        dispose()
    }

    override fun dispose() {
        super.dispose()
        bird.dispose()
        tubes.dispose()
        sfxScore.dispose()
        ground.dispose()
    }
}