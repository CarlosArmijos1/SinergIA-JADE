# SinergIA SISTEMA MULTIAGENTES
Un sistema inteligente que tiene como objetivo: Motivar a las personas para que sean agentes de cambio en la creación de una sociedad más justa equitativa y empática, utilizando herramientas tecnológicas como la IA para identificar formas de injusticia y desigualdad que
son difíciles de detectar para los humanos, generar recomendaciones personalizadas sobre cómo las personas pueden contribuir a la creación de un mundo más justo y equitativo.
Para ello se implementa un algoritmo de clasificación que es en este caso SVM y funciona de la siguiente manera: Support Vector Machine (SVM) es un algoritmo de aprendizaje automático utilizado para la clasificación de datos. En su aplicación para clasificación, SVM busca encontrar un hiperplano en un espacio de características que mejor divide dos o más clases de datos. Este hiperplano se elige de manera que maximice la distancia entre los puntos más cercanos de cada clase, conocidos como vectores de soporte.

La forma en que SVM clasifica nuevos datos es determinar en qué lado del hiperplano se encuentran. Si están en un lado, se asignan a una clase; si están en el otro lado, se asignan a otra clase. SVM también puede manejar conjuntos de datos no linealmente separables mediante el uso de funciones de kernel, que transforman los datos a un espacio de características de mayor dimensión.

# Modelo Conceptual:


![image](https://github.com/CarlosArmijos1/SinergIA-JADE/assets/67163179/8c17a000-8ef8-4e8e-8517-94dfd64def41)

# ESTRUCTURA DEL PROYECTO EN JAVA-JADE

![image](https://github.com/CarlosArmijos1/SinergIA-JADE/assets/67163179/1ac25725-3f41-4ca6-99a5-5205b6883114)

### La clase AgenteAnalisisDatos:
Es donde el sistema recolecta los datos del usuario y los clasifica haciendo uso del algoritmo SVM para realizar está actividad, para posteriormente
enviar el tipo de persona en el que se haya clasificado al Agente Recomendador.

### La clase AgenteRecomendador:
Se encarga de realizar recomendaciones personalizadas dependiendo del tipo de persona que haya enviado el Agente Analista de Datos.

### La clase Main:
Es donde se van a compilar estos Agentes.

### La clase SVMTrainer

Es donde se va a entrenar el algoritmo SVM para realizar la clasificación

### Librerias
Este sistema usa la librería Jade para realizar el uso de agentes y la librería Weka que es de donde se importa el algoritmo de maquinas de soporte vectorial

## Reporte tecnico
https://www.overleaf.com/project/65ae6fc20856a7904c48f16e

