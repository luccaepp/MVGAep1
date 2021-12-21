//Lucca Eppinger
// nUSP: 11381406

class Matriz {

    public static final double SMALL = 0.000001;
    private int lin, col;
    private double [][] m;

    public static Matriz identidade(int n){
        Matriz mat = new Matriz(n, n);
        for(int i = 0; i < mat.lin; i++) mat.m[i][i] = 1;
        return mat;
    }

    public Matriz(int n, int m){
        this.lin = n;
        this.col = m;
        this.m = new double[lin][col];
    }

    public void set(int i, int j, double valor){
        valor = roundAvoid(valor, 6);
        m[i][j] = valor;
    }

    public double get(int i, int j){
        return m[i][j];
    }
    public double[][] getM(){
        return m;
    }

    public void imprime(){
        for(int i = 0; i < lin; i++){
            for(int j = 0; j < col; j++){
                System.out.printf("%7.2f ", m[i][j]);
            }
            System.out.println();
        }
    }

    public void imprime(Matriz agregada){
        for(int i = 0; i < lin; i++){
            for(int j = 0; j < col; j++){
                System.out.printf("%7.2f ", m[i][j]);
            }
            System.out.print(" |");
            for(int j = 0; j < agregada.col; j++){
                System.out.printf("%7.2f ", agregada.m[i][j]);
            }
            System.out.println();
        }
    }

    private void trocaLinha(int i1, int i2){
        double[] tmp = getM()[i1];
        getM()[i1] = getM()[i2];
        getM()[i2] = tmp;
    }

    private void multiplicaLinha(int i, double k){
        for (int j = 0; j < col; j++) {
            Double novoValor = (get(i, j) / k);
            set(i, j, novoValor);
        }
    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    private void combinaLinhas(int i1, int i2, double k){
        for (int j = 0; j < col; j++) {
            set(i1, j, get(i1, j) + (get(i2, j) * k));
        }
    }

    private int [] encontraLinhaPivo(int ini){
        int pivo_col, pivo_lin;
        pivo_lin = lin;
        pivo_col = col;
        for(int i = ini; i < lin; i++){
            int j;
            for(j = 0; j < col; j++) if(Math.abs(m[i][j]) > 0) break;
            if(j < pivo_col) {

                pivo_lin = i;
                pivo_col = j;
            }
        }
        return new int [] { pivo_lin, pivo_col };
    }

    public double formaEscalonada(Matriz agregada){

        int n = lin;
        double det = 1.0;
        for (int i = 0; i < n - 1; i++) {
            int k = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(get(j, i)) > Math.abs(get(k, i))) {
                    k = j;
                }
            }
            if (k != i) {
                det = -det;
                trocaLinha(i, k);
                if(agregada != null) {
                    agregada.trocaLinha(i, k);
                }
            }
            for (int j = i + 1; j < n; j++) {
                double s = get(j, i) / get(i, i);
                    combinaLinhas(j, i, -s);


                if(agregada != null) {
                    agregada.combinaLinhas(j, i, -s);
                }
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            double s = get(i, i);
            det *= s;
            }
        det = verificarDeterminante(det);
        return det;
    }

    private double verificarDeterminante(double det) {
        for (int linha = 0; linha < lin; linha++) {
            boolean isNull = true;
            for (int coluna = 0; coluna < col; coluna++) {
                if(get(linha, coluna) != 0){
                    isNull = false;
                }
            }
            if(isNull) {
                det = 0;
            }
        }
        return det;
    }

    public void formaEscalonadaReduzida(Matriz agregada){
        for (int linha = 0, pivo = 0; linha < lin && pivo < col; ++linha, ++pivo) {
            int i = linha;
            while (get(i, pivo) == 0) {
                if (++i == lin) {
                    i = linha;
                    if (++pivo == col)
                        return;
                }
            }
            if(i != linha) {
                trocaLinha(i, linha);
                if(agregada != null) {
                    agregada.trocaLinha(i, linha);
                }
            }
            if (get(linha, pivo) != 0) {
                double f = get(linha, pivo);
                multiplicaLinha(linha, f);

                if(agregada != null) {
                    agregada.multiplicaLinha(linha, f);
                }
            }
            for (int j = 0; j < lin; ++j) {
                if (j == linha)
                    continue;
                double f = get(j, pivo);
                combinaLinhas(j, linha, -f);

                if(agregada != null) {
                    agregada.combinaLinhas(j, linha, -f);
                }
            }
        }
    }

    public void imprimeResultado() {
        boolean semRespota = false;
        boolean multiplasSolucoes = false;
        for (int linha = 0; linha < lin; linha++) {
            boolean isNull = true;

            for (int coluna = 0; coluna < lin; coluna++) {
                if(get(linha, coluna) != 0){
                    isNull = false;
                }
            }
            if(isNull && get(linha, col-1) != 0) {
                semRespota = true;
            }else if(isNull && get(linha, col-1) == 0){
                multiplasSolucoes = true;
            }
        }
        if(semRespota){
            System.out.println("sistema sem solução");
        }else if(multiplasSolucoes){
            System.out.println("sistema possui diversas soluções");
        }else{
            for(int j = 0; j < lin; j++){

                System.out.printf("%7.2f ", m[j][col-1]);
                System.out.println();
            }
        }
    }
}