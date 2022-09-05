// 327721544 Bar Kirshenboim

/**
 * test class for hitting listener.
 */
public class PrintingHitListener implements HitListener {
    /**
     * blah.
     *
     * @param beingHit object being it
     * @param hitter   The hitter parameter is the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block was hit.");
    }
}