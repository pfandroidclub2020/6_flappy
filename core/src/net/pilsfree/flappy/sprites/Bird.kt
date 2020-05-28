package net.pilsfree.flappy.sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class Bird(x: Float,y:Float) {
    companion object {
        const val GRAVITY = -20f
        const val SPEED = 100f
        const val FRAME_LENGTH = .2f
    }

    val texture by lazy { Texture("bird.png") }
    val animation by lazy { Texture("birdanimation.png") }

    val frames = listOf(
            TextureRegion(animation,0,0,animation.width/3,animation.height),
            TextureRegion(animation,animation.width/3,0,animation.width/3,animation.height),
            TextureRegion(animation,animation.width/3*2,0,animation.width/3,animation.height)
    )

    val sfxWing = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"))

    val position = Vector2(x,y)
    val velocity = Vector2(SPEED,0f)

    var currentFrame = 0
    var currentFrameTime = 0f

    fun update(dt:Float) {
        currentFrameTime += dt
        if (currentFrameTime > FRAME_LENGTH) {
            currentFrame++
            currentFrameTime -= FRAME_LENGTH
        }
        if (currentFrame>=frames.size) {
            currentFrame = 0
        }

        velocity.add(0f, GRAVITY)
        velocity.scl(dt)
        position.add(velocity)
        velocity.scl(1f/dt)
    }

    fun jump() {
        velocity.y = 450f
        sfxWing.play()
    }

    fun dispose() {
        texture.dispose()
        sfxWing.dispose()
    }

    fun rectangle() : Rectangle {
        return Rectangle(position.x,position.y,texture.width.toFloat(),texture.height.toFloat())
    }

    fun render(spriteBatch: SpriteBatch) {
        spriteBatch.draw(frames[currentFrame],position.x,position.y)
    }
}