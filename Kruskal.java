
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

    private static class UnionFind {

        private int[] pai;
        private int[] rank;

        public UnionFind(int n) {
            pai = new int[n];
            rank = new int[n];

            for (int i = 0; i < n; i++) {
                pai[i] = i;
            }
        }

        public int encontrar(int x) {
            if (pai[x] != x) {
                pai[x] = encontrar(pai[x]);
            }

            return pai[x];
        }

        public boolean unir(int a, int b) {
            int raizA = encontrar(a);
            int raizB = encontrar(b);

            if (raizA == raizB) {
                return false;
            }

            if (rank[raizA] < rank[raizB]) {
                pai[raizA] = raizB;
            } else if (rank[raizA] > rank[raizB]) {
                pai[raizB] = raizA;
            } else {
                pai[raizB] = raizA;
                rank[raizA]++;
            }

            return true;
        }
    }

    public static ResultadoAGM executar(Grafo grafo) {

        if (grafo.isDirigido() || !grafo.isPonderado()) {
            throw new IllegalArgumentException(
                    "Kruskal exige grafo nao dirigido e ponderado."
            );
        }

        int n = grafo.getNumVertices();

        List<ArestaAGM> arestas = new ArrayList<>();

        for (int origem = 0; origem < n; origem++) {
            for (Aresta aresta : grafo.getAdjacentes(origem)) {

                int destino = aresta.getDestino();

                if (origem < destino) {
                    arestas.add(new ArestaAGM(
                            origem,
                            destino,
                            aresta.getPeso()
                    ));
                }
            }
        }

        arestas.sort(
                Comparator.comparingDouble(ArestaAGM::getPeso)
        );

        UnionFind uf = new UnionFind(n);

        List<ArestaAGM> resultado = new ArrayList<>();

        for (ArestaAGM aresta : arestas) {

            if (uf.unir(
                    aresta.getOrigem(),
                    aresta.getDestino()
            )) {

                resultado.add(aresta);

                if (resultado.size() == n - 1) {
                    break;
                }
            }
        }

        return new ResultadoAGM(resultado);
    }
}
