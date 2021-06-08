import java.awt.*;
import javax.swing.*;

public class HajdeLujmCheckers
{
    public static void main(String[] args)
    {
        JFrame dritarja = new JFrame("Hajde lujm Checkers");
        Checkers permbajtja = new Checkers();
        dritarja.setContentPane(permbajtja);
        dritarja.pack();
        Dimension permasa = Toolkit.getDefaultToolkit().getScreenSize();
        dritarja.setLocation( (permasa.width - dritarja.getWidth())/2,
                (permasa.height - dritarja.getHeight())/2 );
        dritarja.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        dritarja.setResizable(false);  
        dritarja.setVisible(true);
    }   
}