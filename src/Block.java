// 327721544 Bar Kirshenboim

import GeometryElements.Point;
import GeometryElements.Rectangle;
import GeometryElements.Velocity;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * this Block class implements the Collidable interface.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private static final double ERROR = 0.001;

    private final Rectangle rectangle;
    private Color color = Color.BLACK;
    private List<HitListener> hitListeners = new ArrayList<>();


    /**
     * this is a constructor of class.
     *
     * @param rectangle - rectangle, block shape
     */
    public Block(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * setter to color of block.
     *
     * @param color color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * getter to size of block.
     *
     * @return size of block
     */
    public double getSize() {
        return this.rectangle.getWidth() * this.rectangle.getHeight();
    }

    /**
     * @return rectangle, shape of block
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * check if hit is in an edge block.
     *
     * @param collisionPoint collision point
     * @return true if hit with edge, false otherwise
     */
    public boolean edgeHit(Point collisionPoint) {
        Point upLeft, upRight, lowLeft, lowRight;
        upLeft = this.rectangle.getUpperLeft();
        upRight = new Point(this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth(), upLeft.getY());
        lowLeft = new Point(upLeft.getX(), this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight());
        lowRight = new Point(upRight.getX(), lowLeft.getY());

        return upLeft.distance(collisionPoint) <= ERROR || upRight.distance(collisionPoint) <= ERROR
                || lowRight.distance(collisionPoint) <= ERROR || lowLeft.distance(collisionPoint) <= ERROR;
    }

    /**
     * this function returns the hit velocity after a collision.
     *
     * @param collisionPoint  - collision point
     * @param currentVelocity - pre hit velocity
     * @param hitter          ball that hits this block
     * @return post hit velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
// defining the block edges
        Point upLeft = this.rectangle.getUpperLeft();
        Point lowLeft = new Point(upLeft.getX(), upLeft.getY() + this.rectangle.getHeight());
        Point upRight = new Point(upLeft.getX() + this.rectangle.getWidth(), upLeft.getY());
        Point lowRight = new Point(upRight.getX(), lowLeft.getY());

        Velocity velocity = null;

        if (this.edgeHit(collisionPoint)) { //the ball hit an edge of block
            velocity = new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());

        } else if (Math.abs(collisionPoint.getY() - upLeft.getY()) <= ERROR
                || Math.abs(lowRight.getY() - collisionPoint.getY()) <= ERROR) {
            //vertical collision
            velocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (Math.abs(upLeft.getX() - collisionPoint.getX()) <= ERROR
                || Math.abs(collisionPoint.getX() - lowRight.getX()) <= ERROR) {
            //horizontal collision
            velocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        // re factoring
        this.notifyHit(hitter);
        return velocity;
    }


    /**
     * this function draw this block on the given DrawSurface.
     *
     * @param surface - given DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        if (surface == null) {
            return;
        }
        surface.setColor(this.color);
        Point upperLeft = this.rectangle.getUpperLeft();
        double width = this.rectangle.getWidth();
        double height = this.rectangle.getHeight();
        surface.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);
        surface.setColor(Color.black);
        surface.drawRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);

    }

    @Override
    public void timePassed() {
        return;
    }

    /**
     * add block to game.
     *
     * @param g game
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * removing block from game.
     *
     * @param game game
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>();
        listeners.addAll(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }


    /**
     * Add hl as a listener to hit events.
     *
     * @param hl hit listener
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl hit listener
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * removing.
     */
    public void removeAllListeners() {
        for (int i = 0; i < this.hitListeners.size(); i++) {
            this.removeHitListener(this.hitListeners.get(i));
        }
    }

    /**
     * @return hit listeners.
     */
    public List<HitListener> getHitListeners() {
        return this.hitListeners;
    }
}
