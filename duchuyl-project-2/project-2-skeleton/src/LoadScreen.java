import bagel.*;

/**
 * Used to load every load screen in the game.
 */
public class LoadScreen {
    /**
     * Used to write the log in every screen.
     */
    private final Font TITLE;
    private final Font INSTRUCTION;

    public LoadScreen(String font){
        TITLE = new Font(font, 75);
        INSTRUCTION = new Font(font, 40);
    }

    /**
     * Load screen when complete level 0.
     */
    public void completeLevel0(int width, int height){
        TITLE.drawString("LEVEL COMPLETE!", (width -
                        TITLE.getWidth("LEVEL COMPLETE!")) / 2,
                (height + 33) / 2.0);
    }

    /**
     * Load screen when complete level 1.
     */
    public void completeLevel1(int width, int height){
        TITLE.drawString("CONGRATULATIONS!", (width -
                        TITLE.getWidth("CONGRATULATIONS!")) / 2,
                (height + 33) / 2.0);
    }

    /**
     * Load screen when start level 0.
     */
    public void startLevel0(){
        TITLE.drawString("SHADOW DIMENSION", 260, 250);
        INSTRUCTION.drawString("PRESS SPACE TO START", 350, 440);
        INSTRUCTION.drawString("USE ARROW KEYS TO FIND GATE", 350, 500);
    }

    /**
     * Load screen when start level 1.
     */
    public int startLevel1(Input input){
        INSTRUCTION.drawString("PRESS SPACE TO START", 350, 350);
        INSTRUCTION.drawString("PRESS A TO ATTACK", 350, 410);
        INSTRUCTION.drawString("DEFEAT NAVEC TO WIN", 350, 470);
        if (input.wasPressed(Keys.SPACE)){
            return 1;
        }
        return 0;
    }

    /**
     * Load losing screen.
     */
    public void lose(int width, int height){
        TITLE.drawString("GAME OVER!", (width -
                        TITLE.getWidth("GAME OVER!")) / 2,
                (height + 33) / 2.0);
    }

}
