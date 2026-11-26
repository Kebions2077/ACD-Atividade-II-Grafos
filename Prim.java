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

    /** Versao sem origem explicita (escolhe vertice 0). */
    public static ResultadoAGM executar(Grafo grafo) {
        return executar(grafo, 0);
    }

    /** Versao com origem explicita. */
    public static ResultadoAGM executar(Grafo grafo, int origem) {

        if (grafo.isDirigido() || !grafo.isPonderado()) {
            throw new IllegalArgumentException(
                    "Prim exige grafo nao dirigido e ponderado."
            );
        }

        int n = grafo.getNumVertices();

        if (n == 0) {
            return new ResultadoAGM(new ArrayList<>());
        }

        if (origem < 0 || origem >= n) {
            throw new IllegalArgumentException("Origem invalida");
        }

        boolean[] visitado = new boolean[n];

        List<ArestaAGM> resultado = new ArrayList<>();

        PriorityQueue<Candidata> fila = new PriorityQueue<>();

        visitado[origem] = true;
        for (Aresta aresta : grafo.getAdjacentes(origem)) {
            fila.add(new Candidata(
                    origem,
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

            for (Aresta aresta : grafo.getAdjacentes(atual.destino)) {
                int destino = aresta.getDestino();

                if (!visitado[destino]) {
                    fila.add(new Candidata(
                            atual.destino,
                            destino,
                            aresta.getPeso()
                    ));
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (!visitado[i]) {
                throw new IllegalStateException(
                        "Grafo desconexo: nao existe arvore geradora minima"
                );
            }
        }

        return new ResultadoAGM(resultado);
    }
}