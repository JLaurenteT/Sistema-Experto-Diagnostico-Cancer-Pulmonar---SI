package sistemaexpertodiagnosticocancerpulmonar;

import java.util.ArrayList;
import java.util.Scanner;
import org.jpl7.*;
public class SistemaExpertoDiagnosticoCancerPulmonar {

 public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> sintomasUsuario = new ArrayList<>();
        ArrayList<String> factoresRiesgoUsuario = new ArrayList<>();
        int contadorSintomas = 0;
        int contadorFactoresRiesgo = 0;

        // Carga el archivo Prolog
        Query q1 = new Query("consult", new Term[] {new org.jpl7.Atom("src/experto.pl")});
        System.out.println("consult " + (q1.hasSolution() ? "succeeded" : "failed"));

        // Captura los síntomas del usuario y verifica si son conocidos por el sistema
        System.out.println("Ingrese sus síntomas, uno por línea. Escriba 'fin' para terminar:");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if ("fin".equalsIgnoreCase(input)) {
                break;
            }
            sintomasUsuario.add(input);
            Query q2 = new Query("sintoma", new Term[] {new org.jpl7.Atom(input)});
            System.out.println("Síntoma: " + input);
            if (q2.hasSolution()) {
                System.out.println("El síntoma es conocido por el sistema.");
                contadorSintomas++; // Incrementa el contador si el síntoma es conocido
            } else {
                System.out.println("El síntoma no es conocido por el sistema.");
            }
        }

        // Captura los factores de riesgo del usuario y verifica si están asociados con el cáncer de pulmón
        System.out.println("Ingrese los factores de riesgo a los que ha estado expuesto, uno por línea. Escriba 'fin' para terminar:");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if ("fin".equalsIgnoreCase(input)) {
                break;
            }
            factoresRiesgoUsuario.add(input);
            Query q3 = new Query("causa", new Term[] {new org.jpl7.Atom(input), new org.jpl7.Atom("cancer_pulmonar")});
            System.out.println("Factor de riesgo: " + input);
            if (q3.hasSolution()) {
                System.out.println("El factor de riesgo está asociado con el cáncer de pulmón.");
                contadorFactoresRiesgo++; // Incrementa el contador si el factor de riesgo está asociado
            } else {
                System.out.println("El factor de riesgo no está asociado con el cáncer de pulmón.");
            }
        }

        // Calcular el porcentaje adicional por factores de riesgo
        double porcentajeFactoresRiesgo = contadorFactoresRiesgo * 8.0;

        // Calcular el porcentaje final
        double porcentajeFinal = contadorSintomas * 16.5 + porcentajeFactoresRiesgo;

        // Imprimir el porcentaje final si supera el 50%
        if (porcentajeFinal > 50) {
            System.out.println("Porcentaje final: " + porcentajeFinal + "%");

            // Imprimir los diagnósticos recomendados
            System.out.println("Diagnósticos recomendados:");
            Variable X = new Variable("X");
            Query q4 = new Query("requiere_diagnostico", new Term[] {new org.jpl7.Atom("cancer_pulmonar"), X} );

            while (q4.hasMoreSolutions()) {
                java.util.Map<String, Term> solution = q4.nextSolution();
                System.out.println(solution.get("X"));
            }

            // Si supera el 80%, también mostramos los tratamientos
            if (porcentajeFinal > 80) {
                System.out.println("Tratamientos recomendados:");
                Query q5 = new Query("recibe_tratamiento", new Term[] {new org.jpl7.Atom("cancer_pulmonar"), X} );

                while (q5.hasMoreSolutions()) {
                    java.util.Map<String, Term> solution = q5.nextSolution();
                    System.out.println(solution.get("X"));
                }
            }
        }
    }
}

