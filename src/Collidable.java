// 327721544 Bar Kirshenboim

import GeometryElements.Point;
import GeometryElements.Rectangle;
import GeometryElements.Velocity;

/**
 * this is an interface of  all collidable objects, in our game those are the blocks and paddle.
 */
public interface Collidable {
    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     *
     * @param collisionPoint  collision point
     * @param currentVelocity the hit velocity
     * @param hitter          hitter ball
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}