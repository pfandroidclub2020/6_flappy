package net.pilsfree.flappy.sprites

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.pilsfree.flappy.Consts

class BG(val texture: Texture) {
    var leftSide = 0f
    val PARALAX = 4f

    fun update(camera: Camera) {
         leftSide = camera.position.x - camera.viewportWidth/2
    }

    fun render(spriteBatch: SpriteBatch) {
        val times = (leftSide / (texture.width*PARALAX)).toInt()
        val offset = leftSide - (texture.width*PARALAX*times)

        spriteBatch.draw(texture,leftSide-offset/PARALAX,0f,Consts.WIDTH.toFloat(),Consts.HEIGHT.toFloat())
        spriteBatch.draw(texture,leftSide-offset/PARALAX+texture.width,0f,Consts.WIDTH.toFloat(),Consts.HEIGHT.toFloat())
    }
}