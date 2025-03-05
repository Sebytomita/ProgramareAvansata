/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package tema1;

/**
 * @author Seby
 */

public class Tema1 {
    private boolean[][] Matrix;
    private int n, k;

    public Tema1(int n, int k) {
        this.n = n;
        this.k = k;
        this.Matrix = new boolean[n][n];
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Sintaxa: java Tema1 n k");
            return;
        }

        int n = Integer.parseInt(args[0]); 
        int k = Integer.parseInt(args[1]);

        if (n <= 0 || k <= 0 || k > n) {
            System.out.println("Invalid: n si k pozitive si k <= n");
            return;
        }

        Tema1 generator = new Tema1(n, k);
        
        
        if (n > 30000) {
        System.out.println("n prea mare, doar running time este afisat.");
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        System.out.println("Running time: " + (endTime - startTime) + " ms");
        return;
        } 
        else 
        {
            generator.generateGraph();
            generator.displayResults();
        }
    }

    private void generateGraph() {
        ///init
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Matrix[i][j] = false;
            }
        }

        ///clique de k
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k; j++) {
                Matrix[i][j] = true;
                Matrix[j][i] = true;
            }
        }

        ///set de k
        if (2 * k <= n) {
            for (int i = k; i < 2 * k; i++) {
                for (int j = k; j < 2 * k; j++) {
                    if (i != j) {
                        Matrix[i][j] = false;
                        Matrix[j][i] = false;
                    }
                }
            }
        }
        
        /// muchii random
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                ///ambele in set
                if ((i >= k && i < 2 * k) && (j >= k && j < 2 * k)) 
                {
                    continue;
                }
                if (Math.random() < 0.5) 
                  {
                    Matrix[i][j] = true;
                    Matrix[j][i] = true;
                }
            }
        }
    }
 
        
    private void displayMatrix() {
        String box = "■";
        String empty = "○";

        System.out.println("\nMatrix (" + n + "x" + n + "):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(Matrix[i][j] ? box + " " : empty + " ");
            }
            System.out.println();
        }
    }

    private void displayResults() {
        long startTime = System.currentTimeMillis();

        if (n <= 100) {
            displayMatrix();
        } else {
            System.out.println("Matricea prea mare pentru afisare (n > 100)");
        }

        ///nr muchii
        int edges = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Matrix[i][j]) {
                    edges++;
                }
            }
        }

        ///grade
        int[] degrees = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (Matrix[i][j]) {
                    degrees[i]++;
                }
            }
        }

        ///
        int maxDegree = 0, minDegree = n - 1;
        int sumDegrees = 0;
        for (int degree : degrees) {
            maxDegree = Math.max(maxDegree, degree);
            minDegree = Math.min(minDegree, degree);
            sumDegrees += degree;
        }

        ///afisare
        if(n<=30000)
        {
            System.out.println("\nNr muchii (m): " + edges);
            System.out.println("Grad maxim (Δ(G)): " + maxDegree);
            System.out.println("Grad minim (δ(G)): " + minDegree);
            System.out.println("Suma gardelor: " + sumDegrees);
            System.out.println("2 * m: " + (2 * edges));
            if (sumDegrees == 2 * edges) {
                System.out.println("Adevarat: Suma gradelor e 2 * m.");
            } else {
                System.out.println("Fals: Suma gradelro nu e 2 * m.");
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Running time: " + (endTime - startTime) + " ms");

        if (n > 30000) {
            System.out.println("n prea mare, doar running time este afisat.");
        }
    }
}
