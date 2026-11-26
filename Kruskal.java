<<<<<<< HEAD
import java.util.*;

public class Kruskal {

    public static class Resultado {
        public final double pesoTotal;
        public final List<double[]> arestas; // {u, v, peso}

        Resultado(double pesoTotal, List<double[]> arestas) {
            this.pesoTotal = pesoTotal;
            this.arestas = arestas;
        }
    }

    /** Union-Find (Disjoint Set) com compressao de caminho + uniao por rank */
    static class DSU {
        int[] pai, rank;

        DSU(int n) {
            pai = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) pai[i] = i;
        }

        int find(int x) {
            if (pai[x] != x) pai[x] = find(pai[x]);
            return pai[x];
        }

        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;

            if (rank[ra] < rank[rb]) { int t = ra; ra = rb; rb = t; }
            pai[rb] = ra;
            if (rank[ra] == rank[rb]) rank[ra]++;
=======

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

>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c
            return true;
        }
    }

<<<<<<< HEAD
    /**
     * Executa Kruskal tratando o grafo como NAO-dirigido.
     * Evita duplicatas: usa apenas (u,v) com u<v, mantendo o menor peso
     * entre u->v e v->u.
     */
    public static Resultado executar(Grafo grafo) {

        int n = grafo.getNumVertices();

        // Mapa (min,max) -> menor peso
        Map<Long, Double> arestasUnicas = new HashMap<>();

        for (int u = 0; u < n; u++) {
            for (Aresta a : grafo.getAdjacentes(u)) {
                int v = a.getDestino();
                if (u == v) continue;

                int lo = Math.min(u, v);
                int hi = Math.max(u, v);
                long chave = ((long) lo << 32) | (hi & 0xffffffffL);

                double peso = a.getPeso();
                Double atual = arestasUnicas.get(chave);
                if (atual == null || peso < atual) {
                    arestasUnicas.put(chave, peso);
=======
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
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c
                }
            }
        }

<<<<<<< HEAD
        // Converte para lista ordenavel
        List<double[]> lista = new ArrayList<>();
        for (Map.Entry<Long, Double> e : arestasUnicas.entrySet()) {
            long k = e.getKey();
            int lo = (int) (k >> 32);
            int hi = (int) (k & 0xffffffffL);
            lista.add(new double[]{lo, hi, e.getValue()});
        }

        lista.sort((a, b) -> Double.compare(a[2], b[2]));

        DSU dsu = new DSU(n);
        double pesoTotal = 0;
        List<double[]> mst = new ArrayList<>();

        for (double[] aresta : lista) {
            int u = (int) aresta[0];
            int v = (int) aresta[1];

            if (dsu.union(u, v)) {
                pesoTotal += aresta[2];
                mst.add(aresta);

                if (mst.size() == n - 1) break;
            }
        }

        if (mst.size() != n - 1) {
            throw new IllegalStateException(
                    "Grafo desconexo: nao existe arvore geradora minima"
            );
        }

        return new Resultado(pesoTotal, mst);
    }
}
=======
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
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c
