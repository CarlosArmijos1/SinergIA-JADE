package sinergia;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class SVMTrainer {

    private Classifier classifier;

    public SVMTrainer() {
        try {
            // Ruta del archivo ARFF
            String arffFilePath = "dataset.arff";

            // Cargar el conjunto de datos ARFF
            
            DataSource source = new DataSource(arffFilePath);
            Instances data = source.getDataSet();

            // Establecer la clase objetivo (última columna)
            data.setClassIndex(data.numAttributes() - 1);

            // Inicializar el clasificador SMO
            classifier = new SMO();

            // Entrenar el clasificador
            Carga();
            classifier.buildClassifier(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Classifier getClassifier() {
        return classifier;
    }

    // Nuevo método para obtener el clasificador entrenado
    public static Classifier cargarModeloSVM() {
        System.out.println("");
        System.err.println("USANDO EL MODELO SMO DE WEKA!!");
        System.out.println("");
        return new SVMTrainer().getClassifier();
    }
    
    public static void Carga() {
        System.out.println("Cargando modelo de clasificación...");

        // Duración de la simulación en segundos
        int duracion = 5;

        for (int segundo = 1; segundo <= duracion; segundo++) {
            System.out.print(".");
            // Esperar un segundo (1000 milisegundos) antes de imprimir el siguiente punto
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nCarga completa");
        System.out.println("");
    }
}
