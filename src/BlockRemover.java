
/**
 * a BlockRemover is in charge of removing blocks from the game.
 * as well as keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * constructor of block remover.
     *
     * @param game            the game
     * @param remainingBlocks the remaining blocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Blocks that are hit should be removed from the game.
     * Remember to remove this listener from the block that is being removed from the game.
     *
     * @param beingHit object being it
     * @param hitter   The hitter parameter is the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeAllListeners();
        beingHit.removeFromGame(this.game);
        this.remainingBlocks.decrease(1);

    }
}