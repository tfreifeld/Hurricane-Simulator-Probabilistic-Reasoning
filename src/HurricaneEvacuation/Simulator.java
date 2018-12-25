package HurricaneEvacuation;

import java.io.File;
import java.util.*;

public class Simulator {

    private static Graph graph;

    private static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {

        graph = new Graph(new File(args[0]));
        graph.constructGraph();

        readFloods();
        readEvacuees();
        readBlockages();

        getGraph().displayGraphState();
        System.out.println();


        sc.close();


    }
/*
    private static void makeMove(Move move) {

        if (move.getEdge() == null) {
            *//*NoOp*//*
            System.out.println("NoOp");
            time++;
        } else if (move.getEdge().isBlocked()) {
            System.out.println("traverse failed - edge blocked");
            *//*Edge is blocked*//*
            time++;
        } else {
            double tempTime =
                    time + computeTraverseTime(move.getAgent().getCarrying(), move.getEdge().getWeight());
            if (!(tempTime > getDeadline())) {
                *//*If deadline isn't breached*//*
                System.out.println("traverse - " + move.getAgent().getLocation().getId()
                        + " to " + move.getTarget().getId());
                move.getAgent().traverse(move.getTarget());
                time = tempTime;

            } else {
                System.out.println("traverse failed - will breach deadline");
                *//*If deadline is breached, traverse fails*//*
                time++;
            }
        }

        move.getAgent().increaseMoves();

    }*/

    private static void readFloods() {

        String floodMessage = "Please enter the vertex numbers" +
                " in which a flood is reported:";

        for (Integer vertexNum : readReports(floodMessage, graph.getNumberOfVertices())) {
            graph.getVertex(vertexNum).setFlood();
        }
    }

    private static void readEvacuees(){

        String evacueesMessage = "Please enter the vertex numbers" +
                " in which there are evacuees:";

        for (Integer vertexNum : readReports(evacueesMessage, graph.getNumberOfVertices())) {
            graph.getVertex(vertexNum).setEvacuees();
        }
    }

    private static void readBlockages(){

        String blockageMessage = "Please enter the edge numbers" +
                " which are blocked:";

        for (Integer edgeNum : readReports(blockageMessage, graph.getNumberOfEdges())) {
            graph.getEdge(edgeNum).setBlocked();
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

                for (String numberString: numbersStrings){
                    int number = Integer.parseInt(numberString);
                    if(number < 1 || number > upperBound){
                        throw new RuntimeException("Invalid vertex number:" + number);
                    }
                    else{
                        result.add(number);
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. try again.");
            } catch (RuntimeException e){
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

    private static Graph getGraph() {
        return graph;
    }

}
