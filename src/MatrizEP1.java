// classe que representa uma matriz de valores do tipo double.

class MatrizEP1 {

    // constante para ser usada na comparacao de valores double.
    // Se a diferenca absoluta entre dois valores double for menor
    // do que o valor definido por esta constante, eles devem ser
    // considerados iguais.
    public static final double SMALL = 0.000001;

    private int lin, col;
    private double[][] m;

    public static MatrizEP1 identidade(int n) {

        MatrizEP1 mat = new MatrizEP1(n, n);
        for (int i = 0; i < mat.lin; i++) mat.m[i][i] = 1;
        return mat;
    }

    public MatrizEP1(int n, int m) {

        this.lin = n;
        this.col = m;
        this.m = new double[lin][col];
    }

    public void set(int i, int j, double valor) {

        m[i][j] = valor;
    }

    public double get(int i, int j) {

        return m[i][j];
    }

    public double[][] getM(){
        return m;
    }

    public void imprime() {

        for (int i = 0; i < lin; i++) {

            for (int j = 0; j < col; j++) {

                System.out.printf("%7.2f ", m[i][j]);
            }

            System.out.println();
        }
    }

    public void imprime(MatrizEP1 agregada) {

        for (int i = 0; i < lin; i++) {

            for (int j = 0; j < col; j++) {

                System.out.printf("%7.2f ", m[i][j]);
            }

            System.out.print(" |");

            for (int j = 0; j < agregada.col; j++) {

                System.out.printf("%7.2f ", agregada.m[i][j]);
            }

            System.out.println();
        }
    }

    void trocaLinha(int i, int j) {
        double[] tmp = getM()[i];
        getM()[i] = getM()[j];
        getM()[j] = tmp;
    }

    void multiplicaLinha(int i, double k) {

        // TODO: implementar este metodo.

        for (int j = 0; j < col; j++) {
            Double novoValor = (get(i, j) / k);
            set(i, j, novoValor);
        }
    }

    void combinaLinhas(int i1, int i2, double k) {

        for (int j = 0; j < col; j++) {
            set(i1, j, get(i1, j) + (get(i2, j) * k));
        }
        // TODO: implementar este metodo.

    }

    private int[] encontraLinhaPivo(int ini) {

        int pivo_col, pivo_lin;
        pivo_lin = lin;
        pivo_col = col;

        for (int i = ini; i < lin; i++) {

            int j;
            for (j = 0; j < col; j++) if (Math.abs(m[i][j]) > 0) break;
            if (j < pivo_col) {
                pivo_lin = i;
                pivo_col = j;
            }
        }
        return new int[]{pivo_lin, pivo_col};
    }

    public double determinante(double[][] matriz) {
            //parte-se do pressuposto que a matriz seja válida
            double determinante = 0;//valor do determinante que vai ser retornado
            if (matriz.length == 1) {
                //calcula o determinante de matriz de ordem um
                determinante = matriz[0][0];
            } else if (matriz.length == 2) {
                determinante = matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
            } else if (matriz.length == 3) {
                determinante = matriz[0][0] * matriz[1][1] * matriz[2][2]
                        + matriz[0][1] * matriz[1][2] * matriz[2][0]
                        + matriz[0][2] * matriz[1][0] * matriz[2][1]
                        - matriz[0][2] * matriz[1][1] * matriz[2][0]
                        - matriz[0][0] * matriz[1][2] * matriz[2][1]
                        - matriz[0][1] * matriz[1][0] * matriz[2][2];
            } else {

                double[][] aux;
                int i_aux, j_aux, linha, coluna, i;

                for (i = 0; i < matriz.length; i++) {

                    if (matriz[0][i] != 0) {
                        aux = new double[matriz.length - 1][matriz.length - 1];
                        i_aux = 0;
                        j_aux = 0;

                        for (linha = 1; linha < matriz.length; linha++) {
                            for (coluna = 0; coluna < matriz.length; coluna++) {

                                if (coluna != i) {
                                    aux[i_aux][j_aux] = matriz[linha][coluna];
                                    j_aux++;
                                }
                            }
                            i_aux++;
                            j_aux = 0;
                        }
                        determinante += Math.pow(-1, i) * matriz[0][i] * determinante(aux);
                    }

                }
            }
            return determinante;
        }

    public MatrizEP1 inverse() {
        assert(lin == col);
        MatrizEP1 tmp = new MatrizEP1(lin, col * 2);
        for (int linha = 0; linha < lin; ++linha) {
            System.arraycopy(getM()[linha], 0, tmp.getM()[linha], 0, col);
            tmp.getM()[linha][linha + lin] = 1;
        }
        tmp.formaEscalonadaReduzida();
        MatrizEP1 inv = new MatrizEP1(lin, col);
        for (int linha = 0; linha < lin; ++linha)
            System.arraycopy(tmp.getM()[linha], col, inv.getM()[linha], 0, col);
        return inv;
    }

    public void formaEscalonadaReduzida() {
        for (int linha = 0, pivo = 0; linha < lin && pivo < col; ++linha, ++pivo) {
            int i = linha;
            int[] pivos = encontraLinhaPivo(i);
            pivo = pivos[1];

            while (get(i, pivo) == 0) {
                if (++i == lin) {
                    i = linha;
                    if (++pivo == col)
                        return;
                }
            }

            if(i != linha) {
                trocaLinha(i, linha);

            }
            if (get(linha, pivo) != 0) {
                double f = get(linha, pivo);
                //divide na verdade
                multiplicaLinha(linha, f);
            }
            for (int j = 0; j < lin; ++j) {
                if (j == linha)
                    continue;
                double f = get(j, pivo);
                combinaLinhas(j, linha, -f);
            }
        }
    }


}
