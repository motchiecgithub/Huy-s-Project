import bagel.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;


/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2022
 *
 * Please enter your name below
 * @author Duc Huy Le
 */

public class ShadowDimension extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final Image BACKGROUND0_IMAGE = new Image("res/background0.png");
    private final Image BACKGROUND1_IMAGE = new Image("res/background1.png");

    /**
     * Store all wall objects in level 0
     */
    private final ArrayList<Wall> walls = new ArrayList<>();
    /**
     * store all tree object in level 1
     */
    private final ArrayList<Tree> trees = new ArrayList<>();
    /**
     * store all sinkhole objects in level 0
     */
    private final ArrayList<SinkHole> sinkHoles = new ArrayList<>();
    /**
     * store all sinkhole object in level 1
     */
    private final ArrayList<SinkHole> sinkHoles1 = new ArrayList<>();
    /**
     * store all demon object in level 0
     */
    private final ArrayList<Demon> demons = new ArrayList<>();
    /**
     * player object
     */
    private Player fae;
    /**
     * navec object
     */
    private Navec navec;
    /**
     * used to start the game when user press SPACE
     */
    private int gameStart = 0;
    /**
     * used to mark level 0 is completed
     */
    private int level0Completed = 0;
    /**
     * used to mark the start of level 1
     */
    private int startLevel1 = 0;
    /**
     * store font name
     */
    private final String FONT_NAME = "res/frostbite.ttf";
    /**
     * object load screen
     */
    private final LoadScreen loadScreen = new LoadScreen(FONT_NAME);
    /**
     * used to count the frame when complete level 0
     */
    private int winSecondCheck = 0;
    /**
     * used to load the start screen of level 1
     */
    private int loadLevel1 = 0;
    /**
     * used to mark player in attack state
     */
    private int isAttack = 0;
    /**
     * used to count the frame player in attack state
     */
    private int attackFrame = 0;
    /**
     * used to count the cool down of player attack
     */
    private int coolDown = 0;
    /**
     * used to count the frame player in attack state
     */
    private int attackAnimation = 0;
    /**
     * used to count the frame player in invincible state
     */
    private int invincibleState = 0;
    /**
     * player speed
     */
    private final int PLAYER_SPEED = 2;
    /**
     * used to store level 1 player coordinate
     */
    private Point level1Coordinate, topLeft1, bottomRight1;
    /**
     * used to store difficulty of level 1
     */
    private int timeScale = 0;

    public ShadowDimension(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        String LEVEL0 = "res/level0.csv";
        ArrayList<String[]> dataLevel0 = readCSV(LEVEL0);
        for (String[] strings: dataLevel0){
            switch(strings[0]){
                case "Fae":
                    fae = new Player(strings[1], strings[2]);
                    break;
                case "Wall":
                    walls.add(new Wall(strings[1], strings[2]));
                    break;
                case "Sinkhole":
                    sinkHoles.add(new SinkHole(strings[1], strings[2]));
                    break;
                case "TopLeft":
                    fae.setTopLeft(strings[1], strings[2]);
                    break;
                case "BottomRight":
                    fae.setBottomRight(strings[1], strings[2]);
                    break;
            }
        }
        String LEVEL1 = "res/level1.csv";
        ArrayList<String[]> dataLevel1 = readCSV(LEVEL1);
        for (String[] strings: dataLevel1){
            switch(strings[0]){
                case "Fae":
                    level1Coordinate = new Point(strings[1], strings[2]);
                    break;
                case "Tree":
                    trees.add(new Tree(strings[1], strings[2]));
                    break;
                case "Demon":
                    demons.add(new Demon(strings[1], strings[2]));
                    break;
                case "Navec":
                    navec = new Navec(strings[1], strings[2]);
                    break;
                case "Sinkhole":
                    sinkHoles1.add(new SinkHole(strings[1], strings[2]));
                    break;
                case "TopLeft":
                    topLeft1 = new Point(strings[1], strings[2]);
                    break;
                case "BottomRight":
                    bottomRight1 = new Point(strings[1], strings[2]);
                    break;
            }
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }

    /**
     * Method used to read file into an arraylist
     */
    private ArrayList<String[]> readCSV(String fileName){
        ArrayList<String[]> result = new ArrayList<>();
        try (BufferedReader br =
                     new BufferedReader(new FileReader(fileName))){
            String data;
            int count = 0;
            while ((data = br.readLine()) != null){
                result.add(data.split(","));
                count++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Performs horizontal movement of player
     * Set facing of player left or right
     * @return horizontal movement value
     */
    public double horizonMove(Input input){
        double move = 0;
        if (input.isDown(Keys.LEFT)) {
            fae.setIsFacingLeft(1);
            move -= PLAYER_SPEED;
        }
        if (input.isDown(Keys.RIGHT)) {
            fae.setIsFacingLeft(0);
            move += PLAYER_SPEED;
        }
        return move;
    }

    /**
     * Performs vertical movement of player
     * @return vertical movement value
     */
    public double verticalMove(Input input){
        double move = 0;
        if (input.isDown(Keys.UP)) {
            move -= PLAYER_SPEED;
        }
        if (input.isDown(Keys.DOWN)) {
            move += PLAYER_SPEED;
        }
        return move;
    }

    /**
     * Performs implementation of player
     * Render player in attack state and idle state
     */
    public void renderPlayer(){
        if (isAttack == 1){
            if (attackFrame < 60){
                fae.renderAttack();
                attackFrame++;
            } else {
                attackFrame = 0;
                isAttack = 0;
            }
        } else {
            fae.render();
        }
        fae.drawPlayerHealth();
    }

    /**
     * Performs sinkhole implementation
     * @param sinkHoleInput an Arraylist of sinkhole
     */
    public void renderSinkhole(ArrayList<SinkHole> sinkHoleInput){
        for (SinkHole sinkHole: sinkHoleInput){
            if (sinkHole.collide(fae.createRectangle()) == 1){
                sinkHole.setIsStepOn(1);
                fae.getDamaged(sinkHole.getDamage());
                fae.printLog();
            }
            if (sinkHole.getIsStepOn() == 0){
                sinkHole.render();
            }
        }
    }

    /**
     * Performs wall implementation
     * @return if wall collide with player or not
     */
    public int renderWall(){
        int result = 0;
        for (Wall wall: walls){
            if (wall.collide(fae.createRectangle()) == 1){
                result = 1;
            }
            wall.render();
        }
        return result;
    }

    /**
     * Performs tree implementation
     * @return if tree collide with player or not
     */
    public int renderTree(){
        int result = 0;
        for (Tree tree: trees){
            if (tree.collide(fae.createRectangle()) == 1){
                result = 1;
            }
            tree.render();
        }
        return result;
    }

    /**
     * Performs all demon implementation
     * @param demon a demon or a navec
     * @param timeScaleChange change in timescale
     */
    public void demonImplementation(Demon demon, int timeScaleChange){
        if (demon.getHeathPercentage() > 0){
            demon.changeTimeScale(timeScaleChange);
            demon.drawHealth(demon.getHeathPercentage());
            if (demon.borderCheck(fae.getTop(), fae.getBottom(),
                    fae.getLeft(), fae.getRight())){
                demon.changeDirection();
            }
            for (Tree tree: trees){
                if (tree.collide(demon.createRectangle()) == 1){
                    demon.changeDirection();
                }
            }
            for (SinkHole sinkHole: sinkHoles1){
                if (sinkHole.collideDemon(demon.createRectangle()) == 1){
                    demon.changeDirection();
                }
            }
            if (demon.inAttackRange(fae.getPlayerCenterX(), fae.getPlayerCenterY()) == 1){
                if (demon.renderFire(fae.getPlayerCenterX(),
                        fae.getPlayerCenterY(), fae.createRectangle()) == 1 &&
                        invincibleState == 0){
                    fae.getDamaged(demon.getDamage());
                    demon.printLogDamagePlayer(fae);
                    invincibleState = 179;
                }
                if (invincibleState != 0){
                    invincibleState--;
                }
            }
        }
        if (isAttack == 1 && attackAnimation == 0) {
            demon.takeDamaged(fae.createRectangle(), fae.getDAMAGE());
            demon.printLog();
        }
        demon.render();
    }

    /**
     * Performs timescale controls.
     * @param input input from keyboards if L increase timescale by 1
     *                                   if K decrease timescale by 1
     * @return change in timescale
     */
    protected int timeScaleControl(Input input){
        if (input.wasPressed(Keys.L) && timeScale < 3){
            timeScale++;
            return 1;
        } else if (input.wasPressed(Keys.K) && timeScale > -3){
            timeScale--;
            return -1;
        }
        System.out.println(timeScale);
        return 0;
    }


    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.SPACE)){
            gameStart = 1;
        }
        if (input.wasPressed(Keys.I)){
            startLevel1 = 1;
        }
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        /* player movement */
        double moveHorizontal = horizonMove(input);
        double moveVertical = verticalMove(input);
        if (coolDown != 0){
            coolDown--;
            attackAnimation = 1;
        }
        if (input.isDown(Keys.A) && coolDown == 0){
            isAttack = 1;
            coolDown = 179;
            attackAnimation = 0;
        }
        int isCollided = 0;
        if (gameStart == 1 && level0Completed == 0 && (!fae.loseCheck()) && startLevel1 == 0){
            /* level 0 */
            BACKGROUND0_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            fae.updateMove(moveHorizontal, moveVertical);
            isCollided = renderWall();
            renderSinkhole(sinkHoles);
            if (isCollided == 1 || fae.borderCheck()){
                fae.updateMove(moveHorizontal * - 1, moveVertical * - 1);
            }
            if (fae.level0WinCheck()){
                level0Completed = 1;
            }
            renderPlayer();
        }
        else if (level0Completed == 1 && startLevel1 == 0){
            loadScreen.completeLevel0(WINDOW_WIDTH ,WINDOW_HEIGHT);
            int FRAME = 180;
            if (winSecondCheck >= FRAME){
                startLevel1 = 1;
            }
            winSecondCheck++;
        } else if (fae.loseCheck()){
            loadScreen.lose(WINDOW_WIDTH, WINDOW_HEIGHT);
        } else if (navec.level1Complete()){
            loadScreen.completeLevel1(WINDOW_WIDTH ,WINDOW_HEIGHT);
        } else if (startLevel1 == 0){
            loadScreen.startLevel0();
        } else if (startLevel1 == 1 && loadLevel1 == 0){
            loadLevel1 =  loadScreen.startLevel1(input);
            fae.transition(level1Coordinate, topLeft1, bottomRight1);
        } else if (startLevel1 == 1 && loadLevel1 == 1){
            BACKGROUND1_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            int timeScaleChange = timeScaleControl(input);
            fae.updateMove(moveHorizontal, moveVertical);
            isCollided = renderTree();
            renderSinkhole(sinkHoles1);

            for (Demon demon: demons){
                demonImplementation(demon, timeScaleChange);
            }
            demonImplementation(navec, timeScaleChange);

            if (isCollided == 1 || fae.borderCheck()){
                fae.updateMove(moveHorizontal * - 1, moveVertical * - 1);
            }
            renderPlayer();
        }
    }
}
