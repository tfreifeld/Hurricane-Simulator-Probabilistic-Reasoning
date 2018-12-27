package HurricaneEvacuation;

import java.io.File;
import java.util.*;

public class Simulator {

    private static Graph graph;

    private static Scanner sc = new Scanner(System.in);
    private static Evidence evidence = new Evidence();
    private static BayesNetwork bayesNetwork = new BayesNetwork();


    public static void main(String[] args) {

        graph = new Graph(new File(args[0]));
        graph.constructGraph();

        readFloods();
        readEvacuees();
        readBlockages();

        bayesNetwork.construct();

        sc.close();


    }

    private static void readFloods() {

        String floodMessage = "Enter vertex numbers where flood is reported, or " +
                "negative numbers where the absence of a flood is reported: ";

        for (Integer number : readReports(floodMessage, graph.getNumberOfVertices())) {
            Vertex vertex = graph.getVertex(Math.abs(number));
            State state = number > 0 ? State.TRUE : State.FALSE;
            vertex.setFlood(state);
            evidence.submitFlood(vertex);
        }
    }

    private static void readEvacuees() {

        String evacueesMessage = "Enter vertex numbers where the " +
                "presence of evacuees is reported, or " +
                "negative numbers where the absence of them is reported: ";

        for (Integer number : readReports(evacueesMessage, graph.getNumberOfVertices())) {
            Vertex vertex = graph.getVertex(Math.abs(number));
            State state = number > 0 ? State.TRUE : State.FALSE;
            vertex.setEvacuees(state);
            evidence.submitEvacuees(vertex);
        }
    }

    private static void readBlockages() {

        String blockageMessage = "Enter edge numbers where a blockage is reported, or " +
                "negative numbers where the absence of a blockage is reported: ";

        for (Integer number : readReports(blockageMessage, graph.getNumberOfEdges())) {
            Edge edge = graph.getEdge(Math.abs(number));
            State state = number > 0 ? State.TRUE : State.FALSE;
            edge.setBlocked(state);
            evidence.submitBlockage(edge);
        }

    }

    private static ArrayList<Integer> readReports(String message, int upperBound) {

        ArrayList<Integer> result;

        while (true) {
            try {
                System.out.println(message);

                String input = sc.nextLine();
                String[] numbersStrings = input.split(" ");

                result = new ArrayList<>();

                for (String numberString : numbersStrings) {
                    int number = Integer.parseInt(numberString);
                    if (Math.abs(number) > upperBound) {
                        throw new RuntimeException("Invalid vertex number:" + number);
                    } else {
                        result.add(number);
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. try again.");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    /*private static void displayWorldState() {

//        System.out.println("----------------");
//        graph.displayGraphState();


        for (Agent agent : agents) {
            System.out.println("Agent " + agent.getAgentNum() + ":");
            System.out.println(agent.toString());
            System.out.println();
        }
        System.out.println("----------------");

        if (safeCount == 1) {
            System.out.println(safeCount + " person is safe");
        } else {
            System.out.println(safeCount + " people are safe");
        }

        System.out.println("Time: " + time);
        System.out.println("\n\n");

    }*/

    static Graph getGraph() {
        return graph;
    }

}
