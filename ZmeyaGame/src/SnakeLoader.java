import javax.swing.*;
import java.awt.*;

public class SnakeLoader {
    
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        FrameStuff();
    }
    
    private static void FrameStuff() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame();
        frame.setBounds(10,10,905,700);
        //frame.setBackground(Color.GRAY);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Ирочка, солнышко, с праздником, я тебя очень люблю");
        Gameplay gp = new Gameplay();
        frame.add(gp);
        frame.setVisible(true);
    
    }
}
