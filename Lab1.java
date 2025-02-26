/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab1;

/**
 *
 * @author Seby
 */
public class Lab1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println();

        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};

        for (String language : languages) {
            System.out.println(language);
        }
        System.out.println();
        
        int n = (int) (Math.random() * 1_000_000);
        System.out.println("Numarul ales: " + n);
        
        int nr = n * 3;              
        nr = nr + 0b10101;      
        nr = nr + 0xFF;         
        nr = nr * 6;   
        
        System.out.println("Rezultat final: " + nr);
        
        while (nr > 9) { 
            int cnr=nr;
            int sum = 0;
            
            while (cnr > 0) {
                sum += cnr % 10; 
                cnr /= 10;   
            }
            nr = sum;
        }
        
        System.out.println("Suma cifrelor: " + nr);
        
        System.out.println("Willy-nilly, this semester I will learn " + languages[nr] + ".");
        
    }
}
