import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

<<<<<<< HEAD
        // ===== Demonstracao inicial com grafo pequeno =====
        Grafo grafo = new GrafoNaoDirecionado(
                5,
                true,
                Grafo.Representacao.LISTA
        );

        grafo.adicionarAresta(0, 1, 10);
        grafo.adicionarAresta(0, 2, 5);
        grafo.adicionarAresta(1, 2, 3);
        grafo.adicionarAresta(1, 3, 7);
        grafo.adicionarAresta(2, 4, 8);
        grafo.adicionarAresta(3, 4, 2);

        System.out.println("GRAFO DE DEMONSTRACAO:");
        grafo.imprimir();

        double[] dijkstra = Dijkstra.executar(grafo, 0);
        double[] bellman = BellmanFord.executar(grafo, 0);

        System.out.println("\nDIJKSTRA (origem 0):");
        for (int i = 0; i < dijkstra.length; i++) {
            System.out.println("Vertice " + i + ": " + dijkstra[i]);
        }

        System.out.println("\nBELLMAN-FORD (origem 0):");
        for (int i = 0; i < bellman.length; i++) {
            System.out.println("Vertice " + i + ": " + bellman[i]);
        }

        BuscaLargura.executar(grafo, 0);
        BuscaProfundidade.executar(grafo, 0);

        Prim.Resultado prim = Prim.executar(grafo, 0);
        System.out.printf("%nPRIM (MST) - peso total: %.2f%n", prim.pesoTotal);
        for (double[] a : prim.arestasComPeso) {
            System.out.printf("  %d - %d (peso %.2f)%n",
                    (int) a[0], (int) a[1], a[2]);
        }

        Kruskal.Resultado kruskal = Kruskal.executar(grafo);
        System.out.printf("%nKRUSKAL (MST) - peso total: %.2f%n", kruskal.pesoTotal);
        for (double[] a : kruskal.arestas) {
            System.out.printf("  %d - %d (peso %.2f)%n",
                    (int) a[0], (int) a[1], a[2]);
        }

        // ===== Menu interativo =====
=======
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
<<<<<<< HEAD
            System.out.println("\n===== TP2 - GRAFOS =====");
            System.out.println("1  - Testar Dijkstra");
            System.out.println("2  - Testar Bellman-Ford");
            System.out.println("3  - Experimento Dijkstra");
            System.out.println("4  - Experimento Bellman-Ford");
            System.out.println("5  - Busca em Largura (BFS)");
            System.out.println("6  - Busca em Profundidade (DFS)");
            System.out.println("7  - Ordenacao Topologica");
            System.out.println("8  - Testar Prim (MST)");
            System.out.println("9  - Testar Kruskal (MST)");
            System.out.println("10 - Experimento Prim");
            System.out.println("11 - Experimento Kruskal");
            System.out.println("12 - Gerar grafico de linhas (CSV -> PNG)");
            System.out.println("13 - Gerar boxplot (CSV -> PNG)");
            System.out.println("14 - Gerar todos os graficos (linha + boxplot)");
            System.out.println("15 - Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            try {
                switch (opcao) {
                    case 1  -> testarAlgoritmo("Dijkstra", scanner);
                    case 2  -> testarAlgoritmo("BellmanFord", scanner);
                    case 3  -> executarExperimento("Dijkstra", scanner);
                    case 4  -> executarExperimento("BellmanFord", scanner);
                    case 5  -> testarBusca("BFS", scanner);
                    case 6  -> testarBusca("DFS", scanner);
                    case 7  -> testarTopologica(scanner);
                    case 8  -> testarPrim(scanner);
                    case 9  -> testarKruskal(scanner);
                    case 10 -> executarExperimentoMST("Prim", scanner);
                    case 11 -> executarExperimentoMST("Kruskal", scanner);
                    case 12 -> executarGraficoLinhas(scanner);
                    case 13 -> executarGraficoBoxplot(scanner);
                    case 14 -> executarGraficos(scanner);
                    case 15 -> System.out.println("Encerrando...");
                    default -> System.out.println("Opcao invalida");
=======

            System.out.println("\n===== TP2 - GRAFOS =====");

            System.out.println("1 - Testar Dijkstra");
            System.out.println("2 - Testar Bellman-Ford");
            System.out.println("3 - Experimento Dijkstra");
            System.out.println("4 - Experimento Bellman-Ford");
            System.out.println("5 - Testar Kruskal");
            System.out.println("6 - Testar Prim");
            System.out.println("7 - Testar Floyd-Warshall");
            System.out.println("8 - Testar Ford-Fulkerson");
            System.out.println("9 - Sair");

            System.out.print("Escolha: ");

            try {

                opcao = scanner.nextInt();

                switch (opcao) {

                    case 1:
                        testarAlgoritmo("Dijkstra", scanner);
                        break;

                    case 2:
                        testarAlgoritmo("BellmanFord", scanner);
                        break;

                    case 3:
                        executarExperimento("Dijkstra", scanner);
                        break;

                    case 4:
                        executarExperimento("BellmanFord", scanner);
                        break;

                    case 5:
                        testarAGM("Kruskal", scanner);
                        break;

                    case 6:
                        testarAGM("Prim", scanner);
                        break;

                    case 7:
                        testarFloyd(scanner);
                        break;

                    case 8:
                        testarFordFulkerson(scanner);
                        break;

                    case 9:
                        System.out.println("Encerrando...");
                        break;

                    default:
                        System.out.println("Opcao invalida");
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());

                scanner.nextLine();

                opcao = 0;
            }

<<<<<<< HEAD
        } while (opcao != 15);
=======
        } while (opcao != 9);
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c

        scanner.close();
    }

<<<<<<< HEAD
    // ========================================================
    //  Metodos auxiliares
    // ========================================================
=======

    private static void testarAlgoritmo(
            String algoritmo,
            Scanner scanner) throws Exception {
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c

    private static Grafo carregarGrafo(Scanner scanner) throws Exception {
        System.out.print("Arquivo .gr: ");
        String arquivo = scanner.nextLine().trim();

        System.out.print("Representacao (1-LISTA / 2-MATRIZ): ");
        int tipo = Integer.parseInt(scanner.nextLine().trim());

        Grafo.Representacao representacao =
                tipo == 1
                        ? Grafo.Representacao.LISTA
                        : Grafo.Representacao.MATRIZ;

<<<<<<< HEAD
        return GrafoLoader.carregar(arquivo, representacao);
    }
=======
        System.out.print("Vertice de origem (comecando em 0): ");
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c

    // ---------- Caminhos minimos ----------

<<<<<<< HEAD
    private static void testarAlgoritmo(
            String algoritmo,
            Scanner scanner) throws Exception {

        Grafo grafo = carregarGrafo(scanner);

        System.out.print("Vertice de origem (comecando em 0): ");
        int origem = Integer.parseInt(scanner.nextLine().trim());
=======
        Grafo grafo = GrafoLoader.carregar(
                arquivo,
                representacao
        );
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c

        System.out.println("\nExecutando " + algoritmo);

        double[] resultado;

        if (algoritmo.equals("Dijkstra")) {
            resultado = Dijkstra.executar(grafo, origem);
        } else {
            resultado = BellmanFord.executar(grafo, origem);
        }

        for (int i = 0; i < resultado.length; i++) {
            System.out.println("Vertice " + i + ": " + resultado[i]);
        }
    }

<<<<<<< HEAD
    // ---------- Buscas ----------

    private static void testarBusca(String tipo, Scanner scanner) throws Exception {
        Grafo grafo = carregarGrafo(scanner);

        System.out.print("Vertice de inicio: ");
        int inicio = Integer.parseInt(scanner.nextLine().trim());

        if (tipo.equals("BFS")) {
            BuscaLargura.executar(grafo, inicio);
        } else {
            BuscaProfundidade.executar(grafo, inicio);
        }
    }

    // ---------- Ordenacao topologica ----------

    private static void testarTopologica(Scanner scanner) throws Exception {
        Grafo grafo = carregarGrafo(scanner);
        OrdenacaoTopologica.executar(grafo);
    }

    // ---------- MST ----------

    private static void testarPrim(Scanner scanner) throws Exception {
        Grafo grafo = carregarGrafo(scanner);

        System.out.print("Vertice de origem (enter = 0): ");
        String entrada = scanner.nextLine().trim();
        int origem = entrada.isEmpty() ? 0 : Integer.parseInt(entrada);

        System.out.println("\nExecutando Prim (MST)");

        Prim.Resultado r = Prim.executar(grafo, origem);

        System.out.printf("Peso total MST: %.6f%n", r.pesoTotal);
        for (double[] a : r.arestasComPeso) {
            System.out.printf("  %d - %d (peso %.6f)%n",
                    (int) a[0], (int) a[1], a[2]);
        }
    }

    private static void testarKruskal(Scanner scanner) throws Exception {
        Grafo grafo = carregarGrafo(scanner);

        System.out.println("\nExecutando Kruskal (MST)");

        Kruskal.Resultado r = Kruskal.executar(grafo);

        System.out.printf("Peso total MST: %.6f%n", r.pesoTotal);
        for (double[] a : r.arestas) {
            System.out.printf("  %d - %d (peso %.6f)%n",
                    (int) a[0], (int) a[1], a[2]);
        }
    }

    // ---------- Experimentos ----------
=======
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c

    private static void executarExperimento(
            String algoritmo,
            Scanner scanner) throws Exception {

        System.out.print("Arquivo .gr: ");
        String arquivo = scanner.nextLine().trim();

<<<<<<< HEAD
        System.out.print("Vertice de origem (comecando em 0): ");
        int origem = Integer.parseInt(scanner.nextLine().trim());
=======
        String arquivo = scanner.next();

        System.out.print("Vertice de origem (comecando em 0): ");

        int origem = scanner.nextInt();
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c

        System.out.println("1 - Lista de adjacencia");
        System.out.println("2 - Matriz de adjacencia");
        System.out.print("Escolha: ");
        int tipo = Integer.parseInt(scanner.nextLine().trim());

        Grafo.Representacao representacao =
                tipo == 1
                        ? Grafo.Representacao.LISTA
                        : Grafo.Representacao.MATRIZ;

        Experimento.executarConfiguracao(
                arquivo,
                representacao,
                algoritmo,
                origem,
                "resultados/resultados.csv"
        );
    }

<<<<<<< HEAD
    private static void executarExperimentoMST(
=======

    private static void testarAGM(
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c
            String algoritmo,
            Scanner scanner) throws Exception {

        System.out.print("Arquivo .gr: ");
<<<<<<< HEAD
        String arquivo = scanner.nextLine().trim();

        System.out.println("1 - Lista de adjacencia");
        System.out.println("2 - Matriz de adjacencia");
        System.out.print("Escolha: ");
        int tipo = Integer.parseInt(scanner.nextLine().trim());
=======

        String arquivo = scanner.next();

        System.out.println("1 - Lista de adjacencia");
        System.out.println("2 - Matriz de adjacencia");

        int tipo = scanner.nextInt();
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c

        Grafo.Representacao representacao =
                tipo == 1
                        ? Grafo.Representacao.LISTA
                        : Grafo.Representacao.MATRIZ;

<<<<<<< HEAD
        Experimento.executarConfiguracaoMST(
                arquivo,
                representacao,
                algoritmo,
                "resultados/resultados.csv"
        );
    }

    // ---------- Graficos ----------

    private static String[] perguntarCsvSaida(Scanner scanner) {
        System.out.print("CSV de entrada (enter = resultados/resultados.csv): ");
        String csv = scanner.nextLine().trim();
        if (csv.isEmpty()) csv = "resultados/resultados.csv";

        System.out.print("Diretorio de saida (enter = graficos): ");
        String dir = scanner.nextLine().trim();
        if (dir.isEmpty()) dir = "graficos";

        return new String[]{csv, dir};
    }

    private static void executarGraficoLinhas(Scanner scanner) throws Exception {
        String[] cfg = perguntarCsvSaida(scanner);

        Graficos.gerarLinhas(
                Graficos.lerCSV(cfg[0]),
                cfg[1] + "/linha_tempo_medio.png"
        );
        System.out.println("Grafico de linhas gerado em: " + cfg[1]);
    }

    private static void executarGraficoBoxplot(Scanner scanner) throws Exception {
        String[] cfg = perguntarCsvSaida(scanner);

        Graficos.gerarBoxplots(
                Graficos.lerCSV(cfg[0]),
                cfg[1] + "/boxplot_tempo.png"
        );
        System.out.println("Boxplot gerado em: " + cfg[1]);
    }

    private static void executarGraficos(Scanner scanner) throws Exception {
        String[] cfg = perguntarCsvSaida(scanner);

        Graficos.gerarTodos(cfg[0], cfg[1]);
        System.out.println("Graficos gerados em: " + cfg[1]);
    }
}
=======
        Grafo grafo = GrafoLoader.carregar(
                arquivo,
                representacao
        );

        System.out.println("\nExecutando " + algoritmo);

        ResultadoAGM resultado;

        if (algoritmo.equals("Kruskal")) {
            resultado = Kruskal.executar(grafo);
        } else {
            resultado = Prim.executar(grafo);
        }

        resultado.imprimir();
    }


    private static void testarFloyd(
            Scanner scanner) throws Exception {

        System.out.print("Arquivo .gr: ");

        String arquivo = scanner.next();

        System.out.println("1 - Lista de adjacencia");
        System.out.println("2 - Matriz de adjacencia");

        int tipo = scanner.nextInt();

        Grafo.Representacao representacao =
                tipo == 1
                        ? Grafo.Representacao.LISTA
                        : Grafo.Representacao.MATRIZ;

        Grafo grafo = GrafoLoader.carregar(
                arquivo,
                representacao
        );

        System.out.println("\nExecutando Floyd-Warshall");

        double[][] distancias =
                FloydWarshall.executar(grafo);

        FloydWarshall.imprimir(distancias);
    }


    private static void testarFordFulkerson(
            Scanner scanner) throws Exception {

        System.out.print("Arquivo .gr: ");

        String arquivo = scanner.next();

        System.out.println("1 - Lista de adjacencia");
        System.out.println("2 - Matriz de adjacencia");

        int tipo = scanner.nextInt();

        Grafo.Representacao representacao =
                tipo == 1
                        ? Grafo.Representacao.LISTA
                        : Grafo.Representacao.MATRIZ;

        Grafo grafo = GrafoLoader.carregar(
                arquivo,
                representacao
        );

        System.out.print("Vertice de origem: ");

        int origem = scanner.nextInt();

        System.out.print("Vertice de destino: ");

        int destino = scanner.nextInt();

        System.out.println("\nExecutando Ford-Fulkerson");

        FordFulkerson.Resultado resultado =
                FordFulkerson.executar(
                        grafo,
                        origem,
                        destino
                );

        System.out.println(
                "Fluxo maximo: " +
                        resultado.getFluxoMaximo()
        );
    }
}
>>>>>>> 52a304edb27b71df196b5b2b9ed6befc2e92322c
