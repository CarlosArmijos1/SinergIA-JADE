package sinergia;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class MainSinergIA {

    public static void main(String[] args) {

        System.out.println(
                "¡Bienvenido al Sistema SinergIA!");
        System.out.println(
                "Objetivo: Motivar a las personas para que sean agentes de cambio en la creación de una sociedad más justa,\n"
                + " equitativa y empática, utilizando "
                + "herramientas tecnológicas como la IA para identificar formas de injusticia\n y desigualdad que "
                + "son difíciles de detectar para los humanos, generar recomendaciones personalizadas sobre \n cómo las personas pueden contribuir a la creación de un mundo más justo y equitativo.");

        Profile profile
                = new ProfileImpl();

        profile.setParameter(Profile.GUI,
                "true");

        jade.core.Runtime runtime = jade.core.Runtime.instance();
        AgentContainer mainContainer = runtime.createMainContainer(profile);

        try {
            AgentController analisisController = mainContainer.createNewAgent("AgenteAnalisisDatos", AgenteAnalisisDatos.class.getName(), new Object[]{});
            analisisController.start();

            AgentController recomendadorController = mainContainer.createNewAgent("AgenteRecomendador", AgenteRecomendador.class.getName(), new Object[]{});
            recomendadorController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
