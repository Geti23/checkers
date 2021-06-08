import java.util.ArrayList;

class TeDhenat
{
    static final int
                BOSH = 0,
                KUQ = 1,
                KUQ_KING = 2,
                ZI = 3,
                ZI_KING = 4;

        int[][] board;

        TeDhenat()
        {
            board = new int[8][8];
            organizojeLojen();
        }

        void organizojeLojen()
        {
            for (int rreshti = 0; rreshti < 8; rreshti++)
            {
                for (int kolona = 0; kolona < 8; kolona++)
                {
                    if ( rreshti % 2 == kolona % 2 )
                    {
                        if (rreshti < 3)
                            board[rreshti][kolona] = ZI;
                        else if (rreshti > 4)
                            board[rreshti][kolona] = KUQ;
                        else
                            board[rreshti][kolona] = BOSH;
                    }
                    else {
                        board[rreshti][kolona] = BOSH;
                    }
                }
            }
        }

        int kuEshteCopa(int rreshti, int kolona)
        {
            return board[rreshti][kolona];
        }

        void leviz(Levizjet leviz)
        {
            bejeLevizjen(leviz.ngaRreshti, leviz.ngaKolona, leviz.teRreshti, leviz.teKolona);
        }

        void bejeLevizjen(int ngaRreshti, int ngaKolona, int teRreshti, int teKolona)
        {
            board[teRreshti][teKolona] = board[ngaRreshti][ngaKolona];
            board[ngaRreshti][ngaKolona] = BOSH;
            if (ngaKolona - teRreshti == 2 || ngaRreshti - teRreshti == -2)
            {
                int kaloRreshtin = (ngaRreshti + teRreshti) / 2;
                int kaloKolonen = (ngaKolona + teKolona) / 2;
                board[kaloRreshtin][kaloKolonen] = BOSH;
            }
            if (teRreshti == 0 && board[teRreshti][teKolona] == KUQ)
                board[teRreshti][teKolona] = KUQ_KING;
            if (teRreshti == 7 && board[teRreshti][teKolona] == ZI)
                board[teRreshti][teKolona] = ZI_KING;
        }

        Levizjet[] merrLevizjet(int lojtari)
        {
            if (lojtari != KUQ && lojtari != ZI)
                return null;

            int lojtariKing;
            if (lojtari == KUQ)
                lojtariKing = KUQ_KING;
            else
                lojtariKing = ZI_KING;

            ArrayList<Levizjet> levizjet = new ArrayList<Levizjet>();

            for (int rreshti = 0; rreshti < 8; rreshti++)
            {
                for (int kolona = 0; kolona < 8; kolona++)
                {
                    if (board[rreshti][kolona] == lojtari || board[rreshti][kolona] == lojtariKing)
                    {
                        if (mundTeGjuhet(lojtari, rreshti, kolona, rreshti+1, kolona+1, rreshti+2, kolona+2))
                            levizjet.add(new Levizjet(rreshti, kolona, rreshti+2, kolona+2));
                        if (mundTeGjuhet(lojtari, rreshti, kolona, rreshti-1, kolona+1, rreshti-2, kolona+2))
                            levizjet.add(new Levizjet(rreshti, kolona, rreshti-2, kolona+2));
                        if (mundTeGjuhet(lojtari, rreshti, kolona, rreshti+1, kolona-1, rreshti+2, kolona-2))
                            levizjet.add(new Levizjet(rreshti, kolona, rreshti+2, kolona-2));
                        if (mundTeGjuhet(lojtari, rreshti, kolona, rreshti-1, kolona-1, rreshti-2, kolona-2))
                            levizjet.add(new Levizjet(rreshti, kolona, rreshti-2, kolona-2));
                    }
                }
            }

            if (levizjet.size() == 0)
            {
                for (int reshti = 0; reshti < 8; reshti++)
                {
                    for (int kolona = 0; kolona < 8; kolona++)
                    {
                        if (board[reshti][kolona] == lojtari || board[reshti][kolona] == lojtariKing)
                        {
                            if (mundTeLeviz(lojtari,reshti,kolona,reshti+1,kolona+1))
                                levizjet.add(new Levizjet(reshti,kolona,reshti+1,kolona+1));
                            if (mundTeLeviz(lojtari,reshti,kolona,reshti-1,kolona+1))
                                levizjet.add(new Levizjet(reshti,kolona,reshti-1,kolona+1));
                            if (mundTeLeviz(lojtari,reshti,kolona,reshti+1,kolona-1))
                                levizjet.add(new Levizjet(reshti,kolona,reshti+1,kolona-1));
                            if (mundTeLeviz(lojtari,reshti,kolona,reshti-1,kolona-1))
                                levizjet.add(new Levizjet(reshti,kolona,reshti-1,kolona-1));
                        }
                    }
                }
            }
            
            if (levizjet.size() == 0)
                return null;
            else
            {
                Levizjet[] levizArray = new Levizjet[levizjet.size()];
                for (int i = 0; i < levizjet.size(); i++)
                    levizArray[i] = levizjet.get(i);
                return levizArray;
            }

        }
         
        Levizjet[] merrKalimet(int lojtari, int rreshti, int kolona)
        {
            if (lojtari != KUQ && lojtari != ZI)
                return null;
            int lojtariKing;
            if (lojtari == KUQ)
                lojtariKing = KUQ_KING;
            else
                lojtariKing = ZI_KING;
            ArrayList<Levizjet> levizjet = new ArrayList<Levizjet>();
            if (board[rreshti][kolona] == lojtari || board[rreshti][kolona] == lojtariKing)
            {
                if (mundTeGjuhet(lojtari, rreshti, kolona, rreshti+1, kolona+1, rreshti+2, kolona+2))
                    levizjet.add(new Levizjet(rreshti, kolona, rreshti+2, kolona+2));
                if (mundTeGjuhet(lojtari, rreshti, kolona, rreshti-1, kolona+1, rreshti-2, kolona+2))
                    levizjet.add(new Levizjet(rreshti, kolona, rreshti-2, kolona+2));
                if (mundTeGjuhet(lojtari, rreshti, kolona, rreshti+1, kolona-1, rreshti+2, kolona-2))
                    levizjet.add(new Levizjet(rreshti, kolona, rreshti+2, kolona-2));
                if (mundTeGjuhet(lojtari, rreshti, kolona, rreshti-1, kolona-1, rreshti-2, kolona-2))
                    levizjet.add(new Levizjet(rreshti, kolona, rreshti-2, kolona-2));
            }
            if (levizjet.size() == 0)
                return null;
            else {
                Levizjet[] levizArray = new Levizjet[levizjet.size()];
                for (int i = 0; i < levizjet.size(); i++)
                    levizArray[i] = levizjet.get(i);
                return levizArray;
            }
        }

        private boolean mundTeGjuhet(int lojtari, int r1, int k1, int r2, int k2, int r3, int k3)
        {
            if (r3 < 0 || r3 >= 8 || k3 < 0 || k3 >= 8)
                return false;

            if (board[r3][k3] != BOSH)
                return false;

            if (lojtari == KUQ)
            {
                if (board[r1][k1] == KUQ && r3 > r1)
                    return false;
                if (board[r2][k2] != ZI && board[r2][k2] != ZI_KING)
                    return false;
                return true;
            }
            else
            {
                if (board[r1][k1] == ZI && r3 < r1)
                    return false;
                if (board[r2][k2] != KUQ && board[r2][k2] != KUQ_KING)
                    return false;
                return true;
            }
        }

        private boolean mundTeLeviz(int lojtari, int r1, int k1, int r2, int k2)
        {
            if (r2 < 0 || r2 >= 8 || k2 < 0 || k2 >= 8)
                return false;

            if (board[r2][k2] != BOSH)
                return false;

            if (lojtari == KUQ)
            {
                if (board[r1][k1] == KUQ && r2 > r1)
                    return false;
                return true;
            }
            else
            {
                if (board[r1][k1] == ZI && r2 < r1)
                    return false;
                return true;
            }
        }
}