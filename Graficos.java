import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Graficos {

    static final Color FUNDO = Color.WHITE;
    static final Color EIXO  = Color.BLACK;
    static final Color GRADE = new Color(230, 230, 230);

    static final Color[] CORES = {
            new Color(31, 119, 180),
            new Color(214, 39, 40),
            new Color(44, 160, 44),
            new Color(255, 127, 14),
            new Color(148, 103, 189),
            new Color(140, 86, 75)
    };

    /** Linha do CSV: Algoritmo,Representacao,Vertices,Arestas,Densidade,Execucao,Tempo_ms */
    static class Linha {
        String algoritmo, representacao;
        int vertices, arestas, execucao;
        double densidade, tempo;
    }

    // ========================================================
    //  API publica
    // ========================================================

    public static void gerarTodos(String csv, String dirSaida) throws IOException {
        List<Linha> linhas = lerCSV(csv);
        if (linhas.isEmpty()) {
            System.out.println("CSV vazio ou inexistente: " + csv);
            return;
        }

        Files.createDirectories(Paths.get(dirSaida));

        gerarLinhas(linhas, dirSaida + "/linha_tempo_medio.png");
        gerarBoxplots(linhas, dirSaida + "/boxplot_tempo.png");
    }

    public static List<Linha> lerCSV(String caminho) throws IOException {
        List<Linha> out = new ArrayList<>();
        File f = new File(caminho);
        if (!f.exists()) return out;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String cab = br.readLine();
            if (cab == null) return out;
            String l;
            while ((l = br.readLine()) != null) {
                if (l.trim().isEmpty()) continue;
                String[] p = l.split(",");
                if (p.length < 7) continue;
                Linha li = new Linha();
                li.algoritmo = p[0];
                li.representacao = p[1];
                li.vertices = Integer.parseInt(p[2]);
                li.arestas = Integer.parseInt(p[3]);
                li.densidade = Double.parseDouble(p[4]);
                li.execucao = Integer.parseInt(p[5]);
                li.tempo = Double.parseDouble(p[6]);
                out.add(li);
            }
        }
        return out;
    }

    // ========================================================
    //  Grafico de linhas
    // ========================================================

    public static void gerarLinhas(List<Linha> linhas, String saida) throws IOException {
        if (linhas.isEmpty()) {
            System.out.println("Sem dados para grafico de linhas.");
            return;
        }

        Map<String, Map<Integer, double[]>> dados = new TreeMap<>();
        for (Linha l : linhas) {
            String chave = l.algoritmo + " / " + l.representacao;
            dados.computeIfAbsent(chave, k -> new TreeMap<>());
            Map<Integer, double[]> porV = dados.get(chave);
            double[] acc = porV.computeIfAbsent(l.vertices, k -> new double[2]);
            acc[0] += l.tempo;
            acc[1] += 1;
        }

        double maxY = 0;
        Set<Integer> xsSet = new TreeSet<>();
        for (Map<Integer, double[]> m : dados.values()) {
            for (Map.Entry<Integer, double[]> e : m.entrySet()) {
                xsSet.add(e.getKey());
                maxY = Math.max(maxY, e.getValue()[0] / e.getValue()[1]);
            }
        }
        List<Integer> xs = new ArrayList<>(xsSet);
        if (xs.isEmpty() || maxY == 0) maxY = 1;
        maxY *= 1.15;

        int W = 1000, H = 600;
        int mL = 80, mR = 200, mT = 60, mB = 70;
        int pw = W - mL - mR, ph = H - mT - mB;

        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(FUNDO); g.fillRect(0, 0, W, H);

        g.setColor(EIXO);
        g.drawLine(mL, mT, mL, mT + ph);
        g.drawLine(mL, mT + ph, mL + pw, mT + ph);

        g.setFont(new Font("SansSerif", Font.PLAIN, 11));
        int nGrad = 6;
        for (int i = 0; i <= nGrad; i++) {
            int y = mT + ph - (int) (ph * i / (double) nGrad);
            g.setColor(GRADE); g.drawLine(mL, y, mL + pw, y);
            g.setColor(EIXO);
            String lbl = String.format("%.3f", maxY * i / nGrad);
            FontMetrics fm = g.getFontMetrics();
            g.drawString(lbl, mL - 8 - fm.stringWidth(lbl), y + 4);
        }

        for (int i = 0; i < xs.size(); i++) {
            int x = xPos(i, xs.size(), mL, pw);
            g.setColor(EIXO);
            String lbl = String.valueOf(xs.get(i));
            FontMetrics fm = g.getFontMetrics();
            g.drawString(lbl, x - fm.stringWidth(lbl) / 2, mT + ph + 18);
        }
        g.drawString("Vertices", mL + pw / 2 - 20, mT + ph + 45);
        g.drawString("Tempo medio (ms)", 5, mT - 15);

        int corIdx = 0;
        for (Map.Entry<String, Map<Integer, double[]>> e : dados.entrySet()) {
            g.setColor(CORES[corIdx % CORES.length]);
            g.setStroke(new BasicStroke(2f));

            List<Integer> xOrd = new ArrayList<>(e.getValue().keySet());
            Collections.sort(xOrd);

            int prevX = -1, prevY = -1;
            for (int i = 0; i < xs.size(); i++) {
                Integer vx = xs.get(i);
                double[] acc = e.getValue().get(vx);
                if (acc == null) continue;
                double media = acc[0] / acc[1];
                int x = xPos(i, xs.size(), mL, pw);
                int y = mT + ph - (int) (ph * media / maxY);

                g.fillOval(x - 4, y - 4, 8, 8);
                if (prevX >= 0) g.drawLine(prevX, prevY, x, y);
                prevX = x; prevY = y;
            }
            corIdx++;
        }

        int ly = mT + 10;
        corIdx = 0;
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        for (String k : dados.keySet()) {
            g.setColor(CORES[corIdx % CORES.length]);
            g.fillRect(mL + pw + 15, ly, 14, 10);
            g.setColor(EIXO);
            g.drawString(k, mL + pw + 35, ly + 10);
            ly += 20;
            corIdx++;
        }

        g.dispose();
        ImageIO.write(img, "png", new File(saida));
        System.out.println("Gerado: " + saida);
    }

    static int xPos(int i, int n, int mL, int pw) {
        if (n <= 1) return mL + pw / 2;
        return mL + (int) ((double) i / (n - 1) * pw);
    }

    // ========================================================
    //  Boxplot
    // ========================================================

    public static void gerarBoxplots(List<Linha> linhas, String saida) throws IOException {
        if (linhas.isEmpty()) {
            System.out.println("Sem dados para boxplot.");
            return;
        }

        Map<String, Map<Integer, List<Double>>> dados = new TreeMap<>();
        for (Linha l : linhas) {
            String k = l.algoritmo + " / " + l.representacao;
            dados.computeIfAbsent(k, x -> new TreeMap<>());
            dados.get(k).computeIfAbsent(l.vertices, x -> new ArrayList<>()).add(l.tempo);
        }

        Set<Integer> xsSet = new TreeSet<>();
        for (Map<Integer, List<Double>> m : dados.values()) xsSet.addAll(m.keySet());
        List<Integer> xs = new ArrayList<>(xsSet);

        double maxY = 0;
        for (Map<Integer, List<Double>> m : dados.values())
            for (List<Double> l : m.values())
                for (double v : l) maxY = Math.max(maxY, v);
        if (maxY == 0) maxY = 1;
        maxY *= 1.2;

        int W = 1100, H = 600;
        int mL = 80, mR = 220, mT = 60, mB = 70;
        int pw = W - mL - mR, ph = H - mT - mB;

        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(FUNDO); g.fillRect(0, 0, W, H);

        g.setColor(EIXO);
        g.drawLine(mL, mT, mL, mT + ph);
        g.drawLine(mL, mT + ph, mL + pw, mT + ph);

        g.setFont(new Font("SansSerif", Font.PLAIN, 11));
        int nGrad = 6;
        for (int i = 0; i <= nGrad; i++) {
            int y = mT + ph - (int) (ph * i / (double) nGrad);
            g.setColor(GRADE); g.drawLine(mL, y, mL + pw, y);
            g.setColor(EIXO);
            String lbl = String.format("%.3f", maxY * i / nGrad);
            FontMetrics fm = g.getFontMetrics();
            g.drawString(lbl, mL - 8 - fm.stringWidth(lbl), y + 4);
        }

        int nSeries = dados.size();
        int colW = pw / Math.max(1, xs.size());
        int boxW = Math.max(6, (colW - 10) / Math.max(1, nSeries));

        for (int i = 0; i < xs.size(); i++) {
            int cx = mL + colW * i + colW / 2;
            g.setColor(EIXO);
            String lbl = String.valueOf(xs.get(i));
            FontMetrics fm = g.getFontMetrics();
            g.drawString(lbl, cx - fm.stringWidth(lbl) / 2, mT + ph + 18);

            int s = 0;
            for (Map.Entry<String, Map<Integer, List<Double>>> e : dados.entrySet()) {
                List<Double> vals = e.getValue().get(xs.get(i));
                if (vals == null || vals.isEmpty()) { s++; continue; }

                double[] arr = vals.stream().mapToDouble(Double::doubleValue).toArray();
                Arrays.sort(arr);
                double q1 = quantil(arr, 0.25);
                double q2 = quantil(arr, 0.50);
                double q3 = quantil(arr, 0.75);
                double min = arr[0];
                double max = arr[arr.length - 1];

                int xLeft = cx - (nSeries * boxW) / 2 + s * boxW + 2;
                int xRight = xLeft + boxW - 4;

                int yMin = yOf(min, maxY, mT, ph);
                int yMax = yOf(max, maxY, mT, ph);
                int yQ1  = yOf(q1,  maxY, mT, ph);
                int yQ2  = yOf(q2,  maxY, mT, ph);
                int yQ3  = yOf(q3,  maxY, mT, ph);

                Color c = CORES[s % CORES.length];
                g.setColor(c);

                g.drawLine((xLeft + xRight) / 2, yMax, (xLeft + xRight) / 2, yQ3);
                g.drawLine((xLeft + xRight) / 2, yMin, (xLeft + xRight) / 2, yQ1);
                g.drawLine(xLeft + 2, yMax, xRight - 2, yMax);
                g.drawLine(xLeft + 2, yMin, xRight - 2, yMin);

                g.fillRect(xLeft, yQ3, Math.max(1, xRight - xLeft), Math.max(1, yQ1 - yQ3));
                g.setColor(Color.BLACK);
                g.drawRect(xLeft, yQ3, Math.max(1, xRight - xLeft), Math.max(1, yQ1 - yQ3));

                g.setColor(c.darker());
                g.setStroke(new BasicStroke(2f));
                g.drawLine(xLeft, yQ2, xRight, yQ2);
                g.setStroke(new BasicStroke(1f));

                s++;
            }
        }

        g.setColor(EIXO);
        g.drawString("Vertices", mL + pw / 2 - 20, mT + ph + 45);
        g.drawString("Tempo (ms)", 5, mT - 15);

        int ly = mT + 10;
        int idx = 0;
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        for (String k : dados.keySet()) {
            g.setColor(CORES[idx % CORES.length]);
            g.fillRect(mL + pw + 15, ly, 14, 10);
            g.setColor(EIXO);
            g.drawString(k, mL + pw + 35, ly + 10);
            ly += 20;
            idx++;
        }

        g.dispose();
        ImageIO.write(img, "png", new File(saida));
        System.out.println("Gerado: " + saida);
    }

    static int yOf(double v, double maxY, int mT, int ph) {
        return mT + ph - (int) (ph * v / maxY);
    }

    static double quantil(double[] arr, double p) {
        if (arr.length == 1) return arr[0];
        double h = (arr.length - 1) * p;
        int lo = (int) Math.floor(h);
        int hi = (int) Math.ceil(h);
        if (lo == hi) return arr[lo];
        return arr[lo] + (h - lo) * (arr[hi] - arr[lo]);
    }
}