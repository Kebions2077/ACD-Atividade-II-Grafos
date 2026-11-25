
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class FordFulkerson {

    public static class Resultado {

        private double fluxoMaximo;
        private double[][] residual;

        public Resultado(double fluxoMaximo, double[][] residual) {
            this.fluxoMaximo = fluxoMaximo;
            this.residual = residual;
        }

        public double getFluxoMaximo() {
            return fluxoMaximo;
        }

        public double[][] getResidual() {
            return residual;
        }
    }

    private static boolean buscarCaminho(
            double[][] residual,
            int origem,
            int destino,
            int[] pai) {

        int n = residual.length;

        boolean[] visitado = new boolean[n];

        Queue<Integer> fila = new LinkedList<>();

        fila.add(origem);
        visitado[origem] = true;
        pai[origem] = -1;

        while (!fila.isEmpty()) {

            int atual = fila.poll();

            for (int v = 0; v < n; v++) {

                if (!visitado[v] && residual[atual][v] > 0) {

                    pai[v] = atual;
                    visitado[v] = true;

                    if (v == destino) {
                        return true;
                    }

                    fila.add(v);
                }
            }
        }

        return false;
    }

    public static Resultado executar(
            Grafo grafo,
            int origem,
            int destino) {

        if (!grafo.isDirigido() || !grafo.isPonderado()) {
            throw new IllegalArgumentException(
                    "Ford-Fulkerson exige digrafo ponderado."
            );
        }

        int n = grafo.getNumVertices();

        if (origem < 0 || origem >= n ||
                destino < 0 || destino >= n ||
                origem == destino) {

            throw new IllegalArgumentException(
                    "Vertices de origem ou destino invalidos."
            );
        }

        double[][] residual = new double[n][n];

        for (int u = 0; u < n; u++) {

            for (Aresta aresta : grafo.getAdjacentes(u)) {

                double capacidade = aresta.getPeso();

                if (!Double.isFinite(capacidade) || capacidade < 0) {
                    throw new IllegalArgumentException(
                            "Capacidades devem ser finitas e nao negativas."
                    );
                }

                residual[u][aresta.getDestino()] += capacidade;
            }
        }

        int[] pai = new int[n];

        double fluxoMaximo = 0;

        while (buscarCaminho(residual, origem, destino, pai)) {

            double fluxoCaminho = Double.POSITIVE_INFINITY;

            for (int v = destino; v != origem; v = pai[v]) {

                int u = pai[v];

                fluxoCaminho = Math.min(
                        fluxoCaminho,
                        residual[u][v]
                );
            }

            for (int v = destino; v != origem; v = pai[v]) {

                int u = pai[v];

                residual[u][v] -= fluxoCaminho;
                residual[v][u] += fluxoCaminho;
            }

            fluxoMaximo += fluxoCaminho;
        }

        return new Resultado(fluxoMaximo, residual);
    }
}
