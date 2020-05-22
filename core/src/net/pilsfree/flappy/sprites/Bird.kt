package net.pilsfree.flappy.sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

class Bird(x: Float,y:Float) {
    companion object {
        const val GRAVITY = -20f
    }

    val texture by lazy { Texture("bird.png") }
    val sfxWing = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"))

    val position = Vector2(x,y)
    val velocity = Vector2(0f,0f)

    fun update(dt:Float) {
        velocity.add(0f, GRAVITY)
        velocity.scl(dt)
        position.add(0f,velocity.y)
        velocity.scl(1f/dt)
    }

    fun jump() {
        velocity.y = 500f
        sfxWing.play()
    }

    fun dispose() {
        texture.dispose()
        sfxWing.dispose()
    }
}