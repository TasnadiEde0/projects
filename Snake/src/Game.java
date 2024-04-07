import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Game extends JFrame implements KeyListener, ActionListener {
    GameData gameData;
    GameField gameField;
    GameFrame gameFrame;

    public Game() {

        gameData = new GameData(0.1);
        gameField = new GameField(gameData);
        gameFrame = new GameFrame(gameData, gameField);
        Thread physicsFrameThread = new Thread(gameFrame);
        physicsFrameThread.start();

        addKeyListener(this);
        gameField.getStartButton().addActionListener(this);
        gameField.getCloseButton().addActionListener(this);
        setBounds(50, 50, 817, 815);
        setTitle("Snake");
        setLayout(new BorderLayout());
        add(gameField,BorderLayout.CENTER);

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("background.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(100);
            clip.start();
        }
        catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }



        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        requestFocusInWindow();


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode());
        if((e.getKeyCode() == 39 || e.getKeyCode() == 68) && gameData.getSnake().get(0).getVelocity_x() != -25) {
            List<SnakeSegment> snake = gameData.getSnake();
            SnakeSegment first = snake.get(0);

            snake.add(0, new SnakeSegment(first.getPosition_x(), first.getPosition_y(), 25, 0));
            snake.remove(1);
        }

        if((e.getKeyCode() == 38 || e.getKeyCode() == 87) && gameData.getSnake().get(0).getVelocity_y() != 25) {
            List<SnakeSegment> snake = gameData.getSnake();
            SnakeSegment first = snake.get(0);

            snake.add(0, new SnakeSegment(first.getPosition_x(), first.getPosition_y(), 0, -25));
            snake.remove(1);
        }

        if((e.getKeyCode() == 37 || e.getKeyCode() == 65) && gameData.getSnake().get(0).getVelocity_x() != 25) {
            List<SnakeSegment> snake = gameData.getSnake();
            SnakeSegment first = snake.get(0);

            snake.add(0, new SnakeSegment(first.getPosition_x(), first.getPosition_y(), -25, 0));
            snake.remove(1);
        }

        if((e.getKeyCode() == 40 || e.getKeyCode() == 83) && gameData.getSnake().get(0).getVelocity_y() != -25) {
            List<SnakeSegment> snake = gameData.getSnake();
            SnakeSegment first = snake.get(0);

            snake.add(0, new SnakeSegment(first.getPosition_x(), first.getPosition_y(), 0, 25));
            snake.remove(1);
        }

        if(e.getKeyCode() == 82) {
            gameData.restart();
        }

        if(e.getKeyCode() == 81) {
            gameData.finishGame();
            dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == gameField.getStartButton()) {
            gameField.turnMenuOff();
            requestFocusInWindow();
        }
        else if(e.getSource() == gameField.getCloseButton()) {
            gameData.finishGame();
            dispose();
        }
    }

    public static void main(String[] args) {
        new Game();
    }

}

