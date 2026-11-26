<<<<<<< HEAD
import java.util.*;

public class Prim {

    public static class Resultado {
        public final double pesoTotal;
        public final List<int[]> arestas; // {u, v, pesoArredondado?} -> usamos double separado
        public final List<double[]> arestasComPeso; // {u, v, peso}

        Resultado(double pesoTotal, List<double[]> arestasComPeso) {
            this.pesoTotal = pesoTotal;
            this.arestasComPeso = arestasComPeso;
            this.arestas = new ArrayList<>();
            for (double[] a : arestasComPeso) {
                arestas.add(new int[]{(int) a[0], (int) a[1]});
            }
        }
    }

    /**
     * Executa Prim a partir de uma origem.
     * Trata o grafo como NAO-dirigido: para cada par (u,v) usa o menor peso
     * entre as arestas u->v e v->u.
     */
    public static Resultado executar(Grafo grafo, int origem) {

        if (origem < 0 || origem >= grafo.getNumVertices()) {
            throw new IllegalArgumentException("Origem invalida");
=======

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {

    private static class Candidata implements Comparable<Candidata> {

        int origem;
        int destino;
        double peso;

        public Candidata(int origem, int destino, double peso) {
            this.origem = origem;
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public int compareTo(Candidata outra) {
            return Double.compare(this.peso, outra.peso);
        }
    }

    public static ResultadoAGM executar(Grafo grafo) {

        if (grafo.isDirigido() || !grafo.isPonderado()) {
            throw new IllegalArgumentException(
                    "Prim exige grafo nao dirigido e ponderado."
            );
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c
        }

        int n = grafo.getNumVertices();

<<<<<<< HEAD
        // Constroi lista de adjacencia nao-dirigida (menor peso entre as duas direcoes)
        List<List<double[]>> adj = construirAdjacenciaNaoDirecionada(grafo);

        boolean[] naArvore = new boolean[n];
        double[] chave = new double[n];
        int[] pai = new int[n];

        Arrays.fill(chave, Double.POSITIVE_INFINITY);
        Arrays.fill(pai, -1);

        // Fila de prioridade: {vertice, chave}
        PriorityQueue<double[]> fila = new PriorityQueue<>(
                (a, b) -> Double.compare(a[1], b[1])
        );

        chave[origem] = 0;
        fila.add(new double[]{origem, 0});

        double pesoTotal = 0;
        List<double[]> arestasMST = new ArrayList<>();

        while (!fila.isEmpty()) {
            double[] topo = fila.poll();
            int u = (int) topo[0];

            if (naArvore[u]) continue;
            naArvore[u] = true;

            if (pai[u] != -1) {
                pesoTotal += chave[u];
                arestasMST.add(new double[]{pai[u], u, chave[u]});
            }

            for (double[] viz : adj.get(u)) {
                int v = (int) viz[0];
                double peso = viz[1];

                if (!naArvore[v] && peso < chave[v]) {
                    chave[v] = peso;
                    pai[v] = u;
                    fila.add(new double[]{v, peso});
=======
        boolean[] visitado = new boolean[n];

        List<ArestaAGM> resultado = new ArrayList<>();

        PriorityQueue<Candidata> fila = new PriorityQueue<>();

        for (int inicio = 0; inicio < n; inicio++) {

            if (visitado[inicio]) {
                continue;
            }

            visitado[inicio] = true;

            for (Aresta aresta : grafo.getAdjacentes(inicio)) {
                fila.add(new Candidata(
                        inicio,
                        aresta.getDestino(),
                        aresta.getPeso()
                ));
            }

            while (!fila.isEmpty()) {

                Candidata atual = fila.poll();

                if (visitado[atual.destino]) {
                    continue;
                }

                visitado[atual.destino] = true;

                resultado.add(new ArestaAGM(
                        atual.origem,
                        atual.destino,
                        atual.peso
                ));

                for (Aresta aresta :
                        grafo.getAdjacentes(atual.destino)) {

                    int destino = aresta.getDestino();

                    if (!visitado[destino]) {
                        fila.add(new Candidata(
                                atual.destino,
                                destino,
                                aresta.getPeso()
                        ));
                    }
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c
                }
            }
        }

<<<<<<< HEAD
        // Verifica conectividade
        for (int i = 0; i < n; i++) {
            if (!naArvore[i]) {
                throw new IllegalStateException(
                        "Grafo desconexo: nao existe arvore geradora minima"
                );
            }
        }

        return new Resultado(pesoTotal, arestasMST);
    }

    static List<List<double[]>> construirAdjacenciaNaoDirecionada(Grafo grafo) {
        int n = grafo.getNumVertices();
        List<List<double[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int u = 0; u < n; u++) {
            for (Aresta a : grafo.getAdjacentes(u)) {
                int v = a.getDestino();
                if (u == v) continue;
                adj.get(u).add(new double[]{v, a.getPeso()});
            }
        }
        return adj;
    }
}
=======
        return new ResultadoAGM(resultado);
    }
}
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c
