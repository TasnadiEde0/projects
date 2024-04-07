import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GameFrame implements Runnable {//------------------------------------CONTROLLER
    private GameData gameData;
    private GameField gameField;

    public GameFrame(GameData gameData, GameField gameField) {
        this.gameData = gameData;
        this.gameField = gameField;

    }

    @Override
    public void run() {
        while (!gameData.endOfGame()) {

            try {
                Thread.sleep((int)(gameData.getTime() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(!gameData.menuState()) {

                List<SnakeSegment> snake = gameData.getSnake();

                if(snake.get(0).getPosition_x() + snake.get(0).getVelocity_x() == gameData.getFood().getPosition_x() && snake.get(0).getPosition_y() + snake.get(0).getVelocity_y() == gameData.getFood().getPosition_y()) {
                    gameData.incCurrentScore();
                    gameData.nextFood();
                    SnakeSegment newSegment = new SnakeSegment(snake.get(0).getPosition_x() + snake.get(0).getVelocity_x(),
                            snake.get(0).getPosition_y() + snake.get(0).getVelocity_y(),
                            snake.get(0).getVelocity_x(), snake.get(0).getVelocity_y());
                    snake.add(0, newSegment);

                    try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("eating.wav"));
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    }
                    catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                        System.out.println("IO error");
                    }
                }
                else {
                    snake.remove(snake.size() - 1);
                    SnakeSegment newSegment = new SnakeSegment(snake.get(0).getPosition_x() + snake.get(0).getVelocity_x(),
                            snake.get(0).getPosition_y() + snake.get(0).getVelocity_y(),
                            snake.get(0).getVelocity_x(), snake.get(0).getVelocity_y());
                    snake.add(0, newSegment);

                }

                for(SnakeSegment segment1 : snake) {
                    for(SnakeSegment segment2 : snake) {
                        if(segment1.getPosition_x() == segment2.getPosition_x() &&
                                segment1.getPosition_y() == segment2.getPosition_y() &&
                                        segment1 != segment2) {
                            gameField.turnMenuOn();
                        }
                    }
                }

                if(snake.get(0).getPosition_x() < 25 || snake.get(0).getPosition_x() >  750 ||
                        snake.get(0).getPosition_y() < 25 || snake.get(0).getPosition_y() > 725) {
                    gameField.turnMenuOn();
                }

                gameField.repaint();

            }

        }

    }

}
