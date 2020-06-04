package net.pilsfree.flappy.sprites

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Ground {
    companion object {
        const val BOTTOM = 50
    }

    val texture by lazy { Texture("ground.png") }
    val positions = arrayOf(
            0,
            texture.width
    )

    fun update(camera: Camera) {
        val leftSide = camera.position.x - camera.viewportWidth / 2
        for (i in positions.indices) {
            if (positions[i] + texture.width < leftSide) {
                positions[i] += 2 * texture.width
            }
        }
    }

    fun render(spriteBatch: SpriteBatch) {
        spriteBatch.draw(texture, positions[0].toFloat(), (BOTTOM-texture.height).toFloat())
        spriteBatch.draw(texture, positions[1].toFloat(), (BOTTOM-texture.height).toFloat())
    }

    fun dispose() {
        texture.dispose()
    }
}