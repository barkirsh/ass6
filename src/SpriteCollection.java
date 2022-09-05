// 327721544 Bar Kirshenboim

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * sprites class.
 */
public class SpriteCollection {
    private final List<Sprite> spriteList = new ArrayList<>();

    /**
     * adding sprite.
     *
     * @param s sprite
     */
    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    /**
     * @return sprite list
     */
    public List<Sprite> getSpriteList() {
        return spriteList;
    }

    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        try {


            for (Sprite sprite : this.spriteList) {
                sprite.timePassed();
            }
        } catch (Exception exp) {
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d drawer
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.spriteList) {
            sprite.drawOn(d);
        }
    }
}