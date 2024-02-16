// AgenteAnalisisDatos.java
package sinergia;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import weka.core.DenseInstance;
import weka.core.Instance;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class AgenteAnalisisDatos extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new AnalisisComunicacionBehaviour());
    }

    private class AnalisisComunicacionBehaviour extends CyclicBehaviour {

        public String tipoPersona = "";
        public String nombreUsuario = "";

        @Override
        public void action() {
            try {
                // Solicitar el nombre al usuario
                nombreUsuario = JOptionPane.showInputDialog("Bienvenido al sistema SinergIA \n"
                        + "Ingrese su nombre: ");

                // Obtener preguntas desde el archivo
                List<String> preguntas = obtenerPreguntasDesdeArchivo("preguntas.txt");

                // Inicializar lista para las respuestas del usuario
                List<Integer> respuestasUsuario = new ArrayList<>();

                // Realizar preguntas cerradas
                for (int i = 0; i < preguntas.size(); i++) {
                    int respuesta = JOptionPane.showConfirmDialog(null, preguntas.get(i), "Pregunta " + (i + 1), JOptionPane.YES_NO_OPTION);
                    respuestasUsuario.add((respuesta == JOptionPane.YES_OPTION) ? 1 : 0);
                }
                double[] respuestasUsuarioArray = respuestasUsuario.stream()
                        .mapToDouble(Integer::doubleValue)
                        .toArray();
                try {
                    // Realizar la predicción
                    tipoPersona = prediccion(respuestasUsuarioArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Guardar la información en un archivo de texto
                String rutaArchivo = guardarEnArchivo(nombreUsuario, tipoPersona);
                //Enviar el tipo de personalidad al AgenteRecomendador
                enviarTipoPersonalidad(tipoPersona);
                // Finalizar el agente después de enviar el mensaje
                doDelete();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String prediccion(double[] respuestasUsuario) throws Exception {
            // Ruta del archivo ARFF
            String arffFilePath = "dataset.arff";

            // Cargar el conjunto de datos ARFF
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(arffFilePath);
            Instances data = source.getDataSet();

            // Establecer el índice de la clase
            data.setClassIndex(data.numAttributes() - 1);

            // Crear una instancia con las respuestas del usuario
            Instance usuarioInstance = new DenseInstance(data.numAttributes());
            usuarioInstance.setDataset(data);

            // Cargar el modelo SVM entrenado desde SVMTrainer
            Classifier svm = SVMTrainer.cargarModeloSVM();

            // Establecer los valores de los atributos según las respuestas del usuario
            for (int i = 0; i < respuestasUsuario.length; i++) {
                usuarioInstance.setValue(i, respuestasUsuario[i]);
            }

            // Configurar la clase (último atributo) como desconocida
            usuarioInstance.setMissing(usuarioInstance.numAttributes() - 1);

            // Realizar la predicción
            double predicciones[] = svm.distributionForInstance(usuarioInstance);

            // Obtener la clase predicha (índice de la clase con la probabilidad más alta)
            int clasePredicha = (int) svm.classifyInstance(usuarioInstance);

            // Devolver la clase predicha como string
            return data.classAttribute().value(clasePredicha);
        }
    }
    // Obtener preguntas desde el archivo de texto

    private List<String> obtenerPreguntasDesdeArchivo(String nombreArchivo) {
        List<String> preguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                preguntas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return preguntas;
    }

    // Guardar la información en un archivo de texto y devolver la ruta del archivo
    private String guardarEnArchivo(String nombreUsuario, String tipoPersona) {
        String rutaArchivo = "informacion_usuarios.txt";
        try {
            // Crear o abrir el archivo "informacion_usuarios.txt" en modo de escritura
            FileWriter fileWriter = new FileWriter(rutaArchivo, true); // true para modo de apendizaje
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Escribir la información en el archivo
            printWriter.println(nombreUsuario + "," + tipoPersona);

            // Cerrar el flujo de escritura
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rutaArchivo;
    }

    // Enviar el tipo de personalidad al AgenteRecomendador
    private void enviarTipoPersonalidad(String tipoPersona) {
        ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
        mensaje.setContent(tipoPersona);
        mensaje.addReceiver(getAID("AgenteRecomendador"));
        send(mensaje);
    }

}
