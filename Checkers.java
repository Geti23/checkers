import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Checkers extends JPanel
{
    private JButton ButoniLojaERe;
    private JButton ButoniHeqDor; 
    private JLabel mesazhi;
    
    public Checkers()
    {
        setLayout(null);
        setPreferredSize( new Dimension(450,550) );
        setBackground(new Color(0,100,0));
        
        Board b = new Board();
        add(b);
        add(ButoniLojaERe);
        add(ButoniHeqDor);
        add(mesazhi);

        b.setBounds(20,20,403,403);
        ButoniLojaERe.setBounds(102, 500, 123, 30);
        ButoniHeqDor.setBounds(226, 500, 123, 30);
        mesazhi.setBounds(0, 400, 450, 123);
    }
    private class Board extends JPanel implements ActionListener, MouseListener
    {
        TeDhenat board;
        
        boolean dukeLuajtur;
        int lojtariAktual;
        int rreshtiAktual, kolonaAktuale;
        
        Levizjet[] levizje;
        
        Board() 
        {
            setBackground(Color.BLACK);
            addMouseListener(this);
            ButoniHeqDor = new JButton("Unë heqë dorë");
            ButoniHeqDor.addActionListener(this);
            ButoniLojaERe = new JButton("Fillo lojë t're");
            ButoniLojaERe.addActionListener(this);
            mesazhi = new JLabel("Udhëzim:",JLabel.CENTER);
            mesazhi.setFont(new  Font("Dialog",Font.PLAIN, 19));
            mesazhi.setForeground(Color.YELLOW);
            board = new TeDhenat();
            bejeLojenERe();
        }

        public void actionPerformed(ActionEvent evt)
        {
            Object prek = evt.getSource();
            if (prek == ButoniLojaERe)
                bejeLojenERe();
            else if (prek == ButoniHeqDor)
                bejeDoreHeqjen();
        }

        void bejeLojenERe()
        {
            if (dukeLuajtur == true)
            {
                mesazhi.setText("Perfundo lojen aktuale fillimisht!");
                return;
            }
            board.organizojeLojen();
            lojtariAktual = TeDhenat.KUQ;
            levizje = board.merrLevizjet(TeDhenat.KUQ);
            rreshtiAktual = -1;
            mesazhi.setText("KUQ: Beje levizjen tende.");
            dukeLuajtur = true;
            ButoniLojaERe.setEnabled(false);
            ButoniHeqDor.setEnabled(true);
            repaint();
        }

        void bejeDoreHeqjen()
        {
            if (dukeLuajtur == false)
            {
                mesazhi.setText("Nuk ka loje ne progres!");
                return;
            }
            if (lojtariAktual == TeDhenat.KUQ)
                lojaMbaroi("I KUQI  heqe dore. I ZIU  fiton.");
            else
                lojaMbaroi("I ZIU  heqe dore. I KUQI  fiton.");
        }

        void lojaMbaroi(String s)
        {
            mesazhi.setText(s);
            ButoniLojaERe.setEnabled(true);
            ButoniHeqDor.setEnabled(false);
            dukeLuajtur = false;
        }

        void klikoKatrorin(int rreshti, int kolona)
        {
            for (int i = 0; i < levizje.length; i++)
                if (levizje[i].ngaRreshti == rreshti && levizje[i].ngaKolona == kolona)
                {
                    rreshtiAktual = rreshti;
                    kolonaAktuale = kolona;
                    if (lojtariAktual == TeDhenat.KUQ)
                        mesazhi.setText("KUQ: Beje levizjen tende.");
                    else
                        mesazhi.setText("ZI: Beje levizjen tende.");
                    repaint();
                    return;
                }

            if (rreshtiAktual < 0)
            {
                mesazhi.setText("Zgjedhe copen qe deshiron t'a levizesh.");
                return;
            }

            for (int i = 0; i < levizje.length; i++)
                if (levizje[i].ngaRreshti == rreshtiAktual && levizje[i].ngaKolona == kolonaAktuale
                && levizje[i].teRreshti == rreshti && levizje[i].teKolona == kolona)
                {
                    bejeLevizjen(levizje[i]);
                    return;
                }

            mesazhi.setText("Kliko nje katror ku mund te levizesh.");

        }
        
        void bejeLevizjen(Levizjet leviz)
        {
            board.leviz(leviz);

            if (leviz.gjuajtja())
            {
                levizje = board.merrKalimet(lojtariAktual,leviz.teRreshti,leviz.teKolona);
                if (levizje != null)
                {
                    if (lojtariAktual == TeDhenat.KUQ)
                        mesazhi.setText("KUQ: Duhet te gjuhesh per te vazhduar.");
                    else
                        mesazhi.setText("ZI: Duhet te gjuhesh per te vazhduar.");
                    rreshtiAktual = leviz.teRreshti;
                    kolonaAktuale = leviz.teKolona;
                    repaint();
                    return;
                }
            }

            if (lojtariAktual == TeDhenat.KUQ)
            {
                lojtariAktual = TeDhenat.ZI;
                levizje = board.merrLevizjet(lojtariAktual);
                if (levizje == null)
                    lojaMbaroi("I ZIU  nuk ka levizje. I KUQI  fiton.");
                else if (levizje[0].gjuajtja())
                    mesazhi.setText("ZIU: Gjuajtja eshte levizja jote e vetme.");
                else
                    mesazhi.setText("ZIU: Beje levizjen tende.");
            }
            else
            {
                lojtariAktual = TeDhenat.KUQ;
                levizje = board.merrLevizjet(lojtariAktual);
                if (levizje == null)
                    lojaMbaroi("I KUQI  nuk ka me levizje. I ZIU  fiton.");
                else if (levizje[0].gjuajtja())
                    mesazhi.setText("KUQ: Beje levizjen tende, duhet te gjuhesh.");
                else
                    mesazhi.setText("KUQ: Beje levizjen tende.");
            }

            rreshtiAktual = -1;

            if (levizje != null)
            {
                boolean katroriFillimit = true;
                for (int i = 1; i < levizje.length; i++)
                    if (levizje[i].ngaRreshti != levizje[0].ngaRreshti
                    || levizje[i].ngaKolona != levizje[0].ngaKolona)
                    {
                        katroriFillimit = false;
                        break;
                    }
                if (katroriFillimit)
                {
                    rreshtiAktual = levizje[0].ngaRreshti;
                    kolonaAktuale = levizje[0].ngaKolona;
                }
            }

            repaint();

        }

        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color c = new Color(0,178,0);
            g.setColor(c);
            g.drawRect(0,0,getSize().width+12,getSize().height+12);
            g.drawRect(1,1,getSize().width+10,getSize().height+10);

            for (int rreshti = 0; rreshti < 8; rreshti++)
            {
                for (int kolona = 0; kolona < 8; kolona++)
                {
                    if ( rreshti % 2 == kolona % 2 )
                        g.setColor(Color.DARK_GRAY);
                    else
                        g.setColor(Color.GRAY);
                    g.fillRect(2 + kolona*50, 2 + rreshti*50, 100, 100);
                    switch (board.kuEshteCopa(rreshti,kolona))
                    {
                    case TeDhenat.KUQ:
                        g.setColor(Color.RED);
                        g.fillOval(4 + kolona*50, 4 + rreshti*50, 45, 45);
                        break;
                    case TeDhenat.ZI:
                        g.setColor(Color.BLACK);
                        g.fillOval(4 + kolona*50, 4 + rreshti*50, 45, 45);
                        break;
                    case TeDhenat.KUQ_KING:
                        g.setColor(Color.RED);
                        g.fillOval(4 + kolona*50, 4 + rreshti*50, 45, 45);
                        g.setColor(Color.DARK_GRAY);
                        g.drawString("KING", 10 + kolona*50, 30 + rreshti*50);
                        break;
                    case TeDhenat.ZI_KING:
                        g.setColor(Color.BLACK);
                        g.fillOval(4 + kolona*50, 4 + rreshti*50, 45, 45);
                        g.setColor(Color.YELLOW);
                        g.drawString("KING", 10 + kolona*50, 30 + rreshti*50);
                        break;
                    }
                }
            }    

            if (dukeLuajtur)
            {
                g.setColor(Color.WHITE);
                for (int i = 0; i < levizje.length; i++)
                {
                    g.drawOval(2 + levizje[i].ngaKolona*50, 2 + levizje[i].ngaRreshti*50, 49, 49);
                    g.drawOval(3 + levizje[i].ngaKolona*50, 3 + levizje[i].ngaRreshti*50, 47, 47);
                }
               
                if (rreshtiAktual >= 0)
                {
                    g.setColor(Color.YELLOW);
                    g.drawOval(2 + kolonaAktuale*50, 2 + rreshtiAktual*50, 49, 49);
                    g.drawOval(3 + kolonaAktuale*50, 3 + rreshtiAktual*50, 47, 47);
                    g.setColor(Color.ORANGE);
                    for (int i = 0; i < levizje.length; i++)
                    {
                        if (levizje[i].ngaKolona == kolonaAktuale && levizje[i].ngaRreshti == rreshtiAktual)
                        {
                            g.drawRect(2 + levizje[i].teKolona*50, 2 + levizje[i].teRreshti*50, 49, 49);
                            g.drawRect(3 + levizje[i].teKolona*50, 3 + levizje[i].teRreshti*50, 47, 47);
                        }
                    }
                }
            }

        }

        public void mousePressed(MouseEvent evt)
        {
            if (dukeLuajtur == false)
                mesazhi.setText("Kliko \"Fillo loje t're\" per te filluar nje loje tjeter.");
            else
            {
                int kolona = (evt.getX() - 2) / 50;
                int rreshti = (evt.getY() - 2) / 50;
                if (kolona >= 0 && kolona < 8 && rreshti >= 0 && rreshti < 8)
                    klikoKatrorin(rreshti,kolona);
            }
        }

        public void mouseReleased(MouseEvent evt) { }
        public void mouseClicked(MouseEvent evt) { }
        public void mouseEntered(MouseEvent evt) { }
        public void mouseExited(MouseEvent evt) { }
    }
}