//Lucca Eppinger
// nUSP: 11381406

import java.util.Scanner;

public class EP1 {

    static Scanner in;
    // metodo principal.

    public static void main(String [] args){

        in = new Scanner(System.in);	// Scanner para facilitar a leitura de dados a partir da entrada padrao.
        String operacao = in.next();		// le, usando o scanner, a string que determina qual operacao deve ser realizada.
        int n = in.nextInt();			// le a dimensão da matriz a ser manipulada pela operacao escolhida.
        Matriz matriz;
        // TODO: completar este metodo.

        if("resolve".equals(operacao)){
            matriz = lerMatriz(n, n+1);
            matriz.formaEscalonada(null);
            matriz.formaEscalonadaReduzida(null);
            matriz.imprimeResultado();
        }
        else if("inverte".equals(operacao)){
            matriz = lerMatriz(n, n);
            Matriz matrizId = Matriz.identidade(n);
            double det = matriz.formaEscalonada(matrizId);
            if(det == 0){
                System.out.println("Matriz singular");
            }else{
                matriz.formaEscalonadaReduzida(matrizId);
                matrizId.imprime();

            }

        }
        else if("determinante".equals(operacao)){

            matriz = lerMatriz(n, n);
            double det = matriz.formaEscalonada(null);
            System.out.printf("%7.2f ",det);
        }
        else {
            System.out.println("Operação desconhecida!");
            System.exit(1);
        }
    }

    public static Matriz lerMatriz(int n, int m){
        Matriz matriz = new Matriz(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matriz.set(i, j, in.nextInt());
            }
        }

        return matriz;
    }

}