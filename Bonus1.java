/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package bonus1;

/**
 * @author Seby
 */

public class Bonus1 {
    private boolean[][] Matrix;
    private int n, k; 

    public Bonus1(int n, int k) {
        this.n = n;
        this.k = k;
        this.Matrix = new boolean[n][n];
    }

    public void generateRandomGraph(double p) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.random() < p) {
                    Matrix[i][j] = true;
                    Matrix[j][i] = true;
                }
            }
        }
    }

    ///check clique pt nodurile mele
    private boolean isClique(int[] subset) {
        int size = subset.length;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (!Matrix[subset[i]][subset[j]]) {
                    return false; 
                }
            }
        }
        return true;
    }

    ///bkt
    private boolean findClique(int currentVertex, int verticesNeeded, int[] currentClique) {
        ///complet
        if (verticesNeeded == 0) {
            return isClique(currentClique);
        }

        ///invalid
        if (currentVertex >= n || n - currentVertex < verticesNeeded) {
            return false;
        }

        ///curr
        currentClique[currentClique.length - verticesNeeded] = currentVertex;
        if (findClique(currentVertex + 1, verticesNeeded - 1, currentClique)) {
            return true;
        }

        ///urm
        if (findClique(currentVertex + 1, verticesNeeded, currentClique)) {
            return true;
        }

        return false;
    }

    public boolean hasCliqueOfSizeAtLeastK() {
        int[] currentClique = new int[k];
        return findClique(0, k, currentClique);
    }

    ///set
    public boolean hasStableSetOfSizeAtLeastK() {
        boolean[][] compMatrix = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                compMatrix[i][j] = !Matrix[i][j];
                compMatrix[j][i] = !Matrix[j][i];
            }
        }

        boolean[][] tempMatrix = Matrix;
        Matrix = compMatrix;
        boolean result = hasCliqueOfSizeAtLeastK();
        Matrix = tempMatrix;
        return result;
    }

    public void displayMatrix() {
        System.out.println("\n Matrix (" + n + "x" + n + "):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(Matrix[i][j] ? "1 " : "0 ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[] sizes = {6, 7, 8}; 
        int[] ks = {2, 3, 4}; 
        ///p
        double probability = 0.5;

        for (int n : sizes) {
            for (int k : ks) {
                if (k > n) continue;

                System.out.println("\nGraf cu n = " + n + " si k = " + k);
                Bonus1 finder = new Bonus1(n, k);
                finder.generateRandomGraph(probability);

                finder.displayMatrix();

                ///clique
                //long startTimeClique = System.currentTimeMillis();
                boolean hasClique = finder.hasCliqueOfSizeAtLeastK();
                //long endTimeClique = System.currentTimeMillis();
                System.out.println("Are clique de size minim " + k + ": " + hasClique);
                //System.out.println("Timp pentru clique check: " + (endTimeClique - startTimeClique) + " ms");

                ///set
                //long startTimeStable = System.currentTimeMillis();
                boolean hasStableSet = finder.hasStableSetOfSizeAtLeastK();
                //long endTimeStable = System.currentTimeMillis();
                System.out.println("Are set de size minim " + k + ": " + hasStableSet);
                //System.out.println("Timp pentru set check: " + (endTimeStable - startTimeStable) + " ms");
            }
        }
    }
}
