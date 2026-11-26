import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GrafoLoader {

    /**
     * Carrega um grafo. Por padrao cria GrafoNaoDirecionado, pois os
     * algoritmos de AGM (Prim/Kruskal) exigem grafo nao dirigido.
     * Se o arquivo for claramente um digrafo, use carregarDigrafo().
     */
    public static Grafo carregar(
            String caminho,
            Grafo.Representacao representacao) throws IOException {

        return carregar(caminho, representacao, false);
    }

    public static Grafo carregarDigrafo(
            String caminho,
            Grafo.Representacao representacao) throws IOException {

        return carregar(caminho, representacao, true);
    }

    private static Grafo carregar(
            String caminho,
            Grafo.Representacao representacao,
            boolean dirigido) throws IOException {

        int numVertices = 0;
        Grafo grafo = null;

        try (BufferedReader leitor = new BufferedReader(
                new FileReader(caminho))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                linha = linha.trim();

                if (linha.isEmpty() || linha.startsWith("c")) {
                    continue;
                }

                String[] partes = linha.split("\\s+");

                if (partes[0].equals("p")) {

                    numVertices = Integer.parseInt(partes[2]);

                    if (dirigido) {
                        grafo = new Digrafo(numVertices, true, representacao);
                    } else {
                        grafo = new GrafoNaoDirecionado(numVertices, true, representacao);
                    }

                } else if (partes[0].equals("a") || partes[0].equals("e")) {

                    if (grafo == null) {
                        throw new IOException(
                                "Cabecalho do grafo nao encontrado"
                        );
                    }

                    int origem = Integer.parseInt(partes[1]) - 1;
                    int destino = Integer.parseInt(partes[2]) - 1;
                    double peso = partes.length > 3
                            ? Double.parseDouble(partes[3])
                            : 1.0;

                    grafo.adicionarAresta(origem, destino, peso);
                }
            }
        }

        if (grafo == null) {
            throw new IOException("Arquivo de grafo invalido");
        }

        return grafo;
    }
}