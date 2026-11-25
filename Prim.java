
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
        }

        int n = grafo.getNumVertices();

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
                }
            }
        }

        return new ResultadoAGM(resultado);
    }
}
