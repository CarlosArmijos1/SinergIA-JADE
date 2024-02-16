// AgenteRecomendador.java
package sinergia;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AgenteRecomendador extends Agent {

    private static final String RUTA_ARCHIVO_RECOMENDACIONES_DEFENSOR = "recomendaciones_defensor.txt";
    private static final String RUTA_ARCHIVO_RECOMENDACIONES_COMPROMETIDO = "recomendaciones_comprometido.txt";
    private static final String RUTA_ARCHIVO_RECOMENDACIONES_INDIFERENTE = "recomendaciones_indiferente.txt";
    private static final String RUTA_ARCHIVO_RECOMENDACIONES_BENEFICIARIO = "recomendaciones_beneficiario.txt";
    private static final String RUTA_ARCHIVO_RECOMENDACIONES_AGRESSOR = "recomendaciones_agressor.txt";

    private static final String SEPARADOR_TIPO_PERSONALIDAD = ":";

    private Map<String, String> recomendacionesPorPersonalidad = new HashMap<>();

    @Override
    protected void setup() {
        cargarRecomendaciones();
        addBehaviour(new RecomendacionComportamiento());
    }

    private class RecomendacionComportamiento extends CyclicBehaviour {

        @Override
        public void action() {
            ACLMessage mensaje = receive();
            if (mensaje != null) {
                String tipoPersonalidad = mensaje.getContent();

                String recomendacion = obtenerRecomendacion(tipoPersonalidad);
                System.out.println("Segun nuestro sistema reconocemos que usted tiene un tipo de "
                        + "personalidad: "+ tipoPersonalidad);
                System.out.println("");
                System.out.println("\nLe recomendamos realizar las siguientes actividades"  + ":\n" + recomendacion);

            } else {
                block();
            }
        }

        
        private String obtenerRecomendacion(String tipoPersonalidad) {
            switch (tipoPersonalidad) {
                case "Defensor_de_la_Justicia_Social":
                    return obtenerRecomendacionDesdeArchivo(RUTA_ARCHIVO_RECOMENDACIONES_DEFENSOR);
                case "CiudadanoComprometido":
                    return obtenerRecomendacionDesdeArchivo(RUTA_ARCHIVO_RECOMENDACIONES_COMPROMETIDO);
                case "Indiferente":
                    return obtenerRecomendacionDesdeArchivo(RUTA_ARCHIVO_RECOMENDACIONES_INDIFERENTE);
                case "Beneficiario_de_la_Desigualdad":
                    return obtenerRecomendacionDesdeArchivo(RUTA_ARCHIVO_RECOMENDACIONES_BENEFICIARIO);
                case "Perpetuador_de_Injusticias":
                    return obtenerRecomendacionDesdeArchivo(RUTA_ARCHIVO_RECOMENDACIONES_AGRESSOR);
                default:
                    return "Recomendación no encontrada";
            }
        }

        private String obtenerRecomendacionDesdeArchivo(String rutaArchivo) {
            try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
                StringBuilder recomendacion = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    recomendacion.append(linea).append("\n");
                }
                return recomendacion.toString().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Recomendación no encontrada";
        }
    }

    private void cargarRecomendaciones() {
        cargarRecomendacionDesdeArchivo(RUTA_ARCHIVO_RECOMENDACIONES_DEFENSOR);
        cargarRecomendacionDesdeArchivo(RUTA_ARCHIVO_RECOMENDACIONES_COMPROMETIDO);
        cargarRecomendacionDesdeArchivo(RUTA_ARCHIVO_RECOMENDACIONES_INDIFERENTE);
        cargarRecomendacionDesdeArchivo(RUTA_ARCHIVO_RECOMENDACIONES_BENEFICIARIO);
        cargarRecomendacionDesdeArchivo(RUTA_ARCHIVO_RECOMENDACIONES_AGRESSOR);
    }

    private void cargarRecomendacionDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            StringBuilder recomendacion = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                recomendacion.append(linea).append("\n");
            }
            recomendacionesPorPersonalidad.put(rutaArchivo, recomendacion.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
