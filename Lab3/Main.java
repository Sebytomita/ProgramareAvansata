/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

/**
 *
 * @author Seby
 */
public class Main {
    public static void main(String[] args) {
        
        Airliner airliner1 = new Airliner("Boeing 100A", "BA123", 39.5, 120);
        Freighter freighter1 = new Freighter("Boeing 200F", "FX456", 10000);
        Drone drone1 = new Drone("Drone 1", "DR789", 45.0);

        CargoCapable[] cargoCarriers = {freighter1, drone1};

        System.out.println("Toate aeronavele:");
        System.out.println(airliner1);
        System.out.println(freighter1);
        System.out.println(drone1);

        System.out.println("\nAeronave care transporta marfuri:");
        for (CargoCapable cargo : cargoCarriers) {
            System.out.println(cargo);
        }
    }
}
