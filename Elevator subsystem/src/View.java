import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View {
    private JFrame frame;
    private JPanel panel;
    private JButton[][] buttons;

    public View() {
        frame = new JFrame();
        panel = new JPanel(new GridLayout(3, 4));
        buttons = new JButton[3][4];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(100, 100));
                panel.add(buttons[i][j]);
            }
        }


        buttons[0][0].setText("Elevator 1");
        buttons[0][1].setText("Elevator 2");
        buttons[0][2].setText("Elevator 3");
        buttons[0][3].setText("Elevator 4");

        buttons[1][0].setText("Floor: 1");
        buttons[1][1].setText("Floor: 1");
        buttons[1][2].setText("Floor: 1");
        buttons[1][3].setText("Floor: 1");

        buttons[2][0].setText("FAULTS: None");
        buttons[2][1].setText("FAULTS: None");
        buttons[2][2].setText("FAULTS: None");
        buttons[2][3].setText("FAULTS: None");

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }

    public void update(ArrayList<Motor> cars) {
        buttons[1][0].setText(String.format("Floor: %d", cars.get(0).getCurrentFloor()));
        buttons[1][1].setText(String.format("Floor: %d", cars.get(1).getCurrentFloor()));
        buttons[1][2].setText(String.format("Floor: %d", cars.get(2).getCurrentFloor()));
        buttons[1][3].setText(String.format("Floor: %d", cars.get(3).getCurrentFloor()));

        if(!cars.get(0).isWorking()) {
            buttons[2][0].setText("FAULTS: Hard FAULTS!");
            buttons[0][0].setBackground(Color.RED);
            buttons[1][0].setBackground(Color.RED);
            buttons[2][0].setBackground(Color.RED);
            buttons[2][0].setBackground(Color.RED);
            buttons[2][0].repaint();
        }
        if(!cars.get(1).isWorking()) {
            buttons[2][1].setText("FAULTS: Hard FAULTS!");
            buttons[0][1].setBackground(Color.RED);
            buttons[1][1].setBackground(Color.RED);
            buttons[2][1].setBackground(Color.RED);
            buttons[2][1].repaint();
        }
        if(!cars.get(2).isWorking()) {
            buttons[2][2].setText("FAULTS: Hard FAULTS!");
            buttons[0][2].setBackground(Color.RED);
            buttons[1][2].setBackground(Color.RED);
            buttons[2][2].setBackground(Color.RED);
            buttons[2][2].repaint();
        }
        if(!cars.get(3).isWorking()) {
            buttons[2][3].setText("FAULTS: Hard FAULTS!");
            buttons[0][3].setBackground(Color.RED);
            buttons[1][3].setBackground(Color.RED);
            buttons[2][3].setBackground(Color.RED);
            buttons[2][3].repaint();
        }
    }

    public void singleUpdate(int i, boolean transFault, int floor) {
        if(transFault) {
            buttons[2][i-1].setText("FAULTS: Transient Fault. CLOSING DOOR");
            buttons[0][i-1].setBackground(Color.YELLOW);
            buttons[1][i-1].setBackground(Color.YELLOW);
            buttons[2][i-1].setBackground(Color.YELLOW);
            buttons[2][i-1].repaint();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}

            buttons[2][i-1].setText("FAULTS: None");
            buttons[0][i-1].setBackground(null);
            buttons[1][i-1].setBackground(null);
            buttons[2][i-1].setBackground(null);
            buttons[2][i-1].repaint();
        } else {
            buttons[1][i-1].setText(String.format("Floor: %d", floor));
        }
    }

}