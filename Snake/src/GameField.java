import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameField extends JPanel {//------------------------------------VIEW
    private GameData gameData;
    private JPanel menu;
    private JButton startButton;
    private JButton closeButton;
    private JLabel currentScoreLabel;
    private JLabel highScoreLabel;
    private JLabel message;

    public GameField(GameData gameData) {
        this.gameData = gameData;

        setLayout(new FlowLayout());

        menu = new JPanel();
        menu.setLayout(new GridLayout(5, 1));
        message = new JLabel("");
        startButton = new JButton("Start");
        closeButton = new JButton("Close");
        currentScoreLabel = new JLabel("");
        highScoreLabel = new JLabel("High Score: " + gameData.getHighestScore());

        menu.add(message);
        menu.add(startButton);
        menu.add(highScoreLabel);
        menu.add(currentScoreLabel);
        menu.add(closeButton);
        add(menu);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(!gameData.menuState()) {

            g.setColor(Color.BLACK);
            for(int i = 0; i<=31; i++) {
                g.fillRect(i * 25,0,25,25);
                g.fillRect(i * 25,750,25,25);
            }
            for(int i = 1; i<=30; i++) {
                g.fillRect(0, i * 25, 25, 25);
                g.fillRect(775, i * 25, 25, 25);
            }

            List<SnakeSegment> snake = gameData.getSnake();

            g.setColor(Color.BLUE);
            for (SnakeSegment segment : snake) {
                g.fillRect(segment.getPosition_x(), segment.getPosition_y(), 25, 25);
            }

            g.setColor(Color.RED);
            g.fillRect(gameData.getFood().getPosition_x(), gameData.getFood().getPosition_y(), 25, 25);



        }

    }

    public void turnMenuOn() {
        add(menu);
        gameData.turnMenuOn();
        message.setText("Game Over!");
        gameData.checkNewHighestScore();
        highScoreLabel.setText("High Score: " + gameData.getHighestScore());
        currentScoreLabel.setText("Your Score: " + gameData.getCurrentScore());
        startButton.setText("Retry");
        revalidate();
        repaint();
    }

    public void turnMenuOff() {
        remove(menu);
        gameData.turnMenuOff();
        revalidate();
        repaint();
        gameData.restart();
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }
}
