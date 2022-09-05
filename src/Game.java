// 327721544 Bar Kirshenboim

import GeometryElements.Point;
import GeometryElements.Rectangle;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;
import java.util.Random;

/**
 * defined the game.
 */
public class Game {
    private final GUI gui = new GUI("My Game :)", 800, 600);
    private final Sleeper sleeper = new Sleeper();


    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter remainBlocks = new Counter();
    private Counter remainBalls = new Counter();
    private Counter score = new Counter();


    /**
     * add to this environment a collidable.
     *
     * @param c collidable
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add sprite to sprites colection.
     *
     * @param s sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Removing s collidable from collection.
     *
     * @param c collidable to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.getEnvironment().remove(c);
    }

    /**
     * Removing s sprite from collection.
     *
     * @param s sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSpriteList().remove(s);
    }

    /**
     * Initialize a new game: create the Blocks and Ball(and Paddle) and add them to the game.
     */
    public void initialize() {
        // counters intylaizing
        this.remainBlocks.increase(45);
        this.remainBalls.increase(3);
        HitListener removeBall = new BallRemover(this, this.remainBalls);
        HitListener removeBlock = new BlockRemover(this, this.remainBlocks);
        HitListener scoreTracker = new ScoreTrackingListener(this.score);

        //border points intilaizing...
        Point paddleUpperLeft = new Point(350, 585);
        Point upBorder = new Point(0, 15);
        Point leftBorder = new Point(0, 0);
        Point lowBorder = new Point(0, 600);
        Point rightBorder = new Point(775, 0);
        Block paddle = new Block(new Rectangle(paddleUpperLeft, 85, 15));

        Block upWall = new Block(new Rectangle(upBorder, 800, 25));
        Block rightWall = new Block(new Rectangle(rightBorder, 25, 600));
        Block deathBlock = new Block(new Rectangle(lowBorder, 800, 1)); // death wall
        Block leftWall = new Block(new Rectangle(leftBorder, 25, 600));


        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
        this.environment.addCollidable(paddle);
        this.environment.addCollidable(upWall);
        this.environment.addCollidable(rightWall);
        this.environment.addCollidable(deathBlock);
        this.environment.addCollidable(leftWall);

        upWall.setColor(Color.lightGray);
        rightWall.setColor(Color.lightGray);
        leftWall.setColor(Color.lightGray);

        upWall.addToGame(this);
        rightWall.addToGame(this);
        leftWall.addToGame(this);
        deathBlock.addHitListener(removeBall);

        biuoop.KeyboardSensor keyboard = this.gui.getKeyboardSensor();
        Paddle player = new Paddle(paddle, keyboard);

        for (int i = 0; i < 3; i++) {
            Ball ball = new Ball(300 + i, 300 + i, 5, Color.RED);
            ball.setGameEnvironment(this.environment);
            ball.setVelocity(5 + i, -5 + i);
            ball.addToGame(this);
        }
        paddle.setColor(Color.GRAY);
        player.addToGame(this);

        Random rand = new Random();

        Point p;
        for (int j = 1; j < 6; j++) {
            Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            for (int i = 0; i < 12 - j; i++) {
                p = new Point(715 - i * 60, 50 + j * 30);
                Block block = new Block(new Rectangle(p, 60, 30));
                //block.addHitListener(printingHitListener);
                block.addHitListener(removeBlock);
                block.addHitListener(scoreTracker);
                block.setColor(color);
                block.addToGame(this);
            }
        }

    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {

        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            DrawSurface d = this.gui.getDrawSurface();
            d.drawText(394, 11, "Score: " + this.score.getValue(), 12);
            // stop conditions
            if (this.remainBlocks.getValue() <= 0) {
                // succeeded level
                this.score.increase(100);
                return;
            } else if (this.remainBalls.getValue() <= 0) {
                return;
            }
            //timing
            long startTime = System.currentTimeMillis();

            //draw sprites
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}