package BrickBreaker;
import javax.swing.JFrame;

public class LayOut {
    public static void main (String [] args) {
        JFrame obj = new JFrame();
        GamePlay gamePlay = new GamePlay();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Bang Bricks");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
    }
}
