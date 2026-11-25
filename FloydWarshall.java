
import java.util.Arrays;

public class FloydWarshall {

    public static double[][] executar(Grafo grafo) {

        if (!grafo.isPonderado()) {
            throw new IllegalArgumentException(
                    "Floyd-Warshall exige grafo ponderado."
            );
        }

        int n = grafo.getNumVertices();

        double[][] distancia = new double[n][n];

        for (int i = 0; i < n; i++) {

            Arrays.fill(
                    distancia[i],
                    Double.POSITIVE_INFINITY
            );

            distancia[i][i] = 0;
        }

        for (int origem = 0; origem < n; origem++) {

            for (Aresta aresta : grafo.getAdjacentes(origem)) {

                int destino = aresta.getDestino();

                distancia[origem][destino] = Math.min(
                        distancia[origem][destino],
                        aresta.getPeso()
                );
            }
        }

        for (int k = 0; k < n; k++) {

            for (int i = 0; i < n; i++) {

                for (int j = 0; j < n; j++) {

                    if (distancia[i][k] != Double.POSITIVE_INFINITY
                            && distancia[k][j] != Double.POSITIVE_INFINITY) {

                        double novaDistancia =
                                distancia[i][k] + distancia[k][j];

                        if (novaDistancia < distancia[i][j]) {
                            distancia[i][j] = novaDistancia;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (distancia[i][i] < 0) {
                throw new IllegalStateException(
                        "O grafo possui ciclo de peso negativo."
                );
            }
        }

        return distancia;
    }

    public static void imprimir(double[][] distancia) {

        System.out.println("Matriz de menores distancias:");

        for (int i = 0; i < distancia.length; i++) {

            for (int j = 0; j < distancia.length; j++) {

                if (distancia[i][j] == Double.POSITIVE_INFINITY) {
                    System.out.printf("%12s", "INF");
                } else {
                    System.out.printf("%12.2f", distancia[i][j]);
                }
            }

            System.out.println();
        }
    }
}
