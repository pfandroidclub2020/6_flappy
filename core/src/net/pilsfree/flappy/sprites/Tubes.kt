package net.pilsfree.flappy.sprites

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import kotlin.random.Random

class Tubes {
    companion object {
        const val GAP = 150
        const val WIDTH = 52
        const val SPACING = 120
        const val COUNT = 4
        const val DEVIATION = 200
        const val MIN_BOTTOM = 100
        const val FIRST_TUBE = 150
    }

    val textureBottom by lazy { Texture("bottomtube.png") }
    val textureTop by lazy { Texture("toptube.png") }

    fun generatePos(x:Float) : Vector2 {
        return Vector2(x, MIN_BOTTOM.toFloat() + Random.nextInt(DEVIATION))
    }

    val positions = arrayOf(
            generatePos(FIRST_TUBE+0 * (WIDTH + SPACING).toFloat()),
            generatePos(FIRST_TUBE+1 * (WIDTH + SPACING).toFloat()),
            generatePos(FIRST_TUBE+2 * (WIDTH + SPACING).toFloat()),
            generatePos(FIRST_TUBE+3 * (WIDTH + SPACING).toFloat())
    )

    fun render(spriteBatch: SpriteBatch) {
        positions.forEach { position ->
            spriteBatch.draw(textureBottom, position.x, position.y - textureBottom.height)
            spriteBatch.draw(textureTop, position.x, position.y + GAP)
        }
    }

    fun update(camera: Camera) {
        val leftSide = camera.position.x - camera.viewportWidth / 2
        for (i in positions.indices) {
            if (positions[i].x + WIDTH < leftSide) {
                positions[i] = generatePos(positions[i].x + (SPACING + WIDTH) * COUNT)
            }
        }
    }

    fun collides(r:Rectangle) : Boolean {
        positions.forEach { position ->
            val rectt = Rectangle(position.x, position.y + GAP,
                    textureTop.width.toFloat(), textureTop.height.toFloat())
            val rectb = Rectangle(position.x, position.y - textureBottom.height,
                    textureBottom.width.toFloat(), textureBottom.height.toFloat())
            if (rectt.overlaps(r) || rectb.overlaps(r)) {
                return true
            }
        }
        return false
    }

    fun dispose() {
        textureBottom.dispose()
        textureTop.dispose()
    }

}