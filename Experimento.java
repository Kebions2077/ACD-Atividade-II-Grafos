import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Experimento {

    private static final int REPETICOES = 10;

    private static volatile double resultadoVerificacao;

    // ========================================================
    //  Experimento para algoritmos de caminho minimo
    //  (Dijkstra / Bellman-Ford)
    // ========================================================
    public static void executarConfiguracao(
            String arquivo,
            Grafo.Representacao representacao,
            String algoritmo,
            int origem,
            String arquivoCSV) throws IOException {

        Grafo grafo = GrafoLoader.carregar(arquivo, representacao);

        int vertices = grafo.getNumVertices();

        int arestas = 0;
        for (int i = 0; i < vertices; i++) {
            arestas += grafo.getAdjacentes(i).size();
        }

        double densidade = vertices <= 1
                ? 0
                : (double) arestas / (vertices * (vertices - 1));

        // Aquecimento (warm-up para JIT)
        if (algoritmo.equalsIgnoreCase("Dijkstra")) {
            Dijkstra.executar(grafo, origem);
        } else if (algoritmo.equalsIgnoreCase("BellmanFord")) {
            BellmanFord.executar(grafo, origem);
        } else {
            throw new IllegalArgumentException("Algoritmo desconhecido");
        }

        double[] tempos = new double[REPETICOES];
        double soma = 0;

        for (int i = 0; i < REPETICOES; i++) {

            long inicio = System.nanoTime();

            double[] resultado;

            if (algoritmo.equalsIgnoreCase("Dijkstra")) {
                resultado = Dijkstra.executar(grafo, origem);
            } else {
                resultado = BellmanFord.executar(grafo, origem);
            }

            long fim = System.nanoTime();

            tempos[i] = (fim - inicio) / 1_000_000.0;
            soma += tempos[i];

            for (double distancia : resultado) {
                resultadoVerificacao += distancia == Double.POSITIVE_INFINITY
                        ? 0
                        : distancia;
            }

            System.out.printf("Execucao %d: %.6f ms%n", i + 1, tempos[i]);
        }

        double media = soma / REPETICOES;

        double menor = tempos[0];
        double maior = tempos[0];

        for (double tempo : tempos) {
            if (tempo < menor) menor = tempo;
            if (tempo > maior) maior = tempo;
        }

        salvarCSV(
                arquivoCSV,
                algoritmo,
                representacao.toString(),
                vertices,
                arestas,
                densidade,
                tempos
        );

        System.out.println("\nRESULTADO DO EXPERIMENTO");
        System.out.println("Algoritmo: " + algoritmo);
        System.out.println("Representacao: " + representacao);
        System.out.println("Vertices: " + vertices);
        System.out.println("Arestas: " + arestas);
        System.out.printf("Densidade: %.2f%n", densidade);
        System.out.printf("Media: %.6f ms%n", media);
        System.out.printf("Menor tempo: %.6f ms%n", menor);
        System.out.printf("Maior tempo: %.6f ms%n", maior);
        System.out.println();
    }

    // ========================================================
    //  Experimento para algoritmos de MST
    //  (Prim / Kruskal)
    // ========================================================
    public static void executarConfiguracaoMST(
            String arquivo,
            Grafo.Representacao representacao,
            String algoritmo,
            String arquivoCSV) throws IOException {

        Grafo grafo = GrafoLoader.carregar(arquivo, representacao);

        int vertices = grafo.getNumVertices();

        int arestas = 0;
        for (int i = 0; i < vertices; i++) {
            arestas += grafo.getAdjacentes(i).size();
        }

        double densidade = vertices <= 1
                ? 0
                : (double) arestas / (vertices * (vertices - 1));

        // Aquecimento (warm-up para JIT)
        if (algoritmo.equalsIgnoreCase("Prim")) {
            Prim.executar(grafo, 0);
        } else if (algoritmo.equalsIgnoreCase("Kruskal")) {
            Kruskal.executar(grafo);
        } else {
            throw new IllegalArgumentException("Algoritmo MST desconhecido");
        }

        double[] tempos = new double[REPETICOES];
        double soma = 0;

        for (int i = 0; i < REPETICOES; i++) {

            long inicio = System.nanoTime();

            double pesoTotal;

            if (algoritmo.equalsIgnoreCase("Prim")) {
                pesoTotal = Prim.executar(grafo, 0).pesoTotal;
            } else {
                pesoTotal = Kruskal.executar(grafo).pesoTotal;
            }

            long fim = System.nanoTime();

            tempos[i] = (fim - inicio) / 1_000_000.0;
            soma += tempos[i];

            // Verificacao leve para o JIT nao descartar a chamada
            resultadoVerificacao += pesoTotal;

            System.out.printf("Execucao %d: %.6f ms%n", i + 1, tempos[i]);
        }

        double media = soma / REPETICOES;

        double menor = tempos[0];
        double maior = tempos[0];

        for (double tempo : tempos) {
            if (tempo < menor) menor = tempo;
            if (tempo > maior) maior = tempo;
        }

        salvarCSV(
                arquivoCSV,
                algoritmo,
                representacao.toString(),
                vertices,
                arestas,
                densidade,
                tempos
        );

        System.out.println("\nRESULTADO DO EXPERIMENTO (MST)");
        System.out.println("Algoritmo: " + algoritmo);
        System.out.println("Representacao: " + representacao);
        System.out.println("Vertices: " + vertices);
        System.out.println("Arestas: " + arestas);
        System.out.printf("Densidade: %.2f%n", densidade);
        System.out.printf("Media: %.6f ms%n", media);
        System.out.printf("Menor tempo: %.6f ms%n", menor);
        System.out.printf("Maior tempo: %.6f ms%n", maior);
        System.out.println();
    }

    // ========================================================
    //  Experimento para buscas e ordenacao topologica
    //  (BFS / DFS / Ordenacao Topologica)
    // ========================================================
    public static void executarConfiguracaoBusca(
            String arquivo,
            Grafo.Representacao representacao,
            String algoritmo,
            int origem,
            String arquivoCSV) throws IOException {

        Grafo grafo = GrafoLoader.carregar(arquivo, representacao);

        int vertices = grafo.getNumVertices();

        int arestas = 0;
        for (int i = 0; i < vertices; i++) {
            arestas += grafo.getAdjacentes(i).size();
        }

        double densidade = vertices <= 1
                ? 0
                : (double) arestas / (vertices * (vertices - 1));

        // Aquecimento
        executarBusca(grafo, algoritmo, origem);

        double[] tempos = new double[REPETICOES];
        double soma = 0;

        for (int i = 0; i < REPETICOES; i++) {

            long inicio = System.nanoTime();

            executarBusca(grafo, algoritmo, origem);

            long fim = System.nanoTime();

            tempos[i] = (fim - inicio) / 1_000_000.0;
            soma += tempos[i];

            System.out.printf("Execucao %d: %.6f ms%n", i + 1, tempos[i]);
        }

        double media = soma / REPETICOES;

        double menor = tempos[0];
        double maior = tempos[0];

        for (double tempo : tempos) {
            if (tempo < menor) menor = tempo;
            if (tempo > maior) maior = tempo;
        }

        salvarCSV(
                arquivoCSV,
                algoritmo,
                representacao.toString(),
                vertices,
                arestas,
                densidade,
                tempos
        );

        System.out.println("\nRESULTADO DO EXPERIMENTO (BUSCA)");
        System.out.println("Algoritmo: " + algoritmo);
        System.out.println("Representacao: " + representacao);
        System.out.println("Vertices: " + vertices);
        System.out.println("Arestas: " + arestas);
        System.out.printf("Densidade: %.2f%n", densidade);
        System.out.printf("Media: %.6f ms%n", media);
        System.out.printf("Menor tempo: %.6f ms%n", menor);
        System.out.printf("Maior tempo: %.6f ms%n", maior);
        System.out.println();
    }

    private static void executarBusca(Grafo grafo, String algoritmo, int origem) {
        switch (algoritmo) {
            case "BFS" -> BuscaLargura.executar(grafo, origem);
            case "DFS" -> BuscaProfundidade.executar(grafo, origem);
            case "Topologica" -> OrdenacaoTopologica.executar(grafo);
            default -> throw new IllegalArgumentException(
                    "Algoritmo de busca desconhecido: " + algoritmo
            );
        }
    }

    // ========================================================
    //  Gravacao do CSV
    // ========================================================
    private static void salvarCSV(
            String arquivoCSV,
            String algoritmo,
            String representacao,
            int vertices,
            int arestas,
            double densidade,
            double[] tempos) throws IOException {

        File arquivo = new File(arquivoCSV);

        File diretorio = arquivo.getParentFile();

        if (diretorio != null) {
            diretorio.mkdirs();
        }

        boolean escreverCabecalho =
                !arquivo.exists() || arquivo.length() == 0;

        try (BufferedWriter escritor = new BufferedWriter(
                new FileWriter(arquivo, true))) {

            if (escreverCabecalho) {
                escritor.write(
                        "Algoritmo,Representacao,Vertices,Arestas,Densidade,Execucao,Tempo_ms"
                );
                escritor.newLine();
            }

            for (int i = 0; i < tempos.length; i++) {
                escritor.write(
                        algoritmo + "," +
                                representacao + "," +
                                vertices + "," +
                                arestas + "," +
                                densidade + "," +
                                (i + 1) + "," +
                                tempos[i]
                );
                escritor.newLine();
            }
        }
    }
}