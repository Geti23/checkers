class Levizjet
{   int ngaRreshti, ngaKolona;
    int teRreshti, teKolona;
    
        Levizjet(int r1, int k1, int r2, int k2)
        {
            ngaRreshti = r1;
            ngaKolona = k1;
            teRreshti = r2;
            teKolona = k2;
        }
        boolean gjuajtja()
        {
            return (ngaRreshti - teRreshti == 2 || ngaRreshti - teRreshti == -2);
        }
}