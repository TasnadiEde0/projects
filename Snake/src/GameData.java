import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameData {//------------------------------------DATA
    private List<SnakeSegment> snake;
    private double time;
    private Food food;
    private Random random;
    private int currentScore;
    private int highestScore;
    private boolean inMenu;
    private boolean exited;

    public GameData(double speed) {
        time = speed;
        random = new Random();
        inMenu = true;
        exited = false;

        restart();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("gamedata.dat"));
            String string = reader.readLine();
            highestScore = Integer.parseInt(string);
        }
        catch (FileNotFoundException e) {
            highestScore = 0;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void restart() {
        snake = new ArrayList<>();
        food = new Food(25 * random.nextInt(1,30), 25 * random.nextInt(1,30));

        snake.add(0, new SnakeSegment(400, 400, 0, -25));
        snake.add(1, new SnakeSegment(400, 425, 0, 0));
        snake.add(2, new SnakeSegment(400, 450, 0, 0));

        currentScore = 0;


    }

    public List<SnakeSegment> getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public void nextFood() {
        food = new Food(25 * random.nextInt(1,30), 25 * random.nextInt(1,30));
    }

    public double getTime() {
        return time;
    }

    public void checkNewHighestScore() {
        highestScore = Integer.max(currentScore, highestScore);
    }

    public void incCurrentScore() {
        currentScore++;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public boolean menuState(){
        return inMenu;
    }

    public void turnMenuOn() {
        inMenu = true;
    }

    public void turnMenuOff() {
        inMenu = false;
    }

    public void finishGame() {
        exited = true;

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("gamedata.dat"));
            writer.write(Integer.toString(highestScore));
            writer.close();
        }
        catch (IOException f) {
            System.out.println(f.getMessage());
        }

    }

    public boolean endOfGame() {
        return exited;
    }
}
