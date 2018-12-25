package HurricaneEvacuation;

import java.io.*;
import java.util.*;

class Graph {

    private static final String edgeEncoding = "#E";
    private static final String vertexEncoding = "#V";
    private static final String ignoreNonDigitsRegex = "[^0-9]*";

    private Scanner sc;
    private HashMap<Integer, Vertex> vertices = new HashMap<>();
    private HashMap<Integer, Edge> edges = new HashMap<>();


    Graph(File file) {

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    void constructGraph(){

        readNumOfVertices();

        while(sc.hasNextLine()){


            if (sc.hasNext(edgeEncoding)){

                readEdge();

            }
            else if (sc.hasNext(vertexEncoding)){

                readVertex();

            }
            else{

                throw new InputMismatchException("Unknown input parameter");

            }
            sc.nextLine();
        }

        sc.close();

    }

    private void readVertex() {
        sc.skip(vertexEncoding);
        int vertexNum = sc.nextInt();
        if (sc.hasNext("F")){
            sc.skip(ignoreNonDigitsRegex);
            float floodProb = sc.nextFloat();
            vertices.get(vertexNum).setFloodProb(floodProb);

        } else {
            vertices.get(vertexNum).setFloodProb(0);
        }
    }

    private void readEdge() {
        sc.skip(edgeEncoding);
        int edgeNum = sc.nextInt();
        int in = sc.nextInt();
        int out = sc.nextInt();
        sc.skip(ignoreNonDigitsRegex);
        int weight = sc.nextInt();

        edges.put(edgeNum,new Edge(vertices.get(in), vertices.get(out),weight));
    }

    private void readNumOfVertices(){

        if (sc.hasNext(vertexEncoding)){

            sc.skip(vertexEncoding);
            int numOfVertices = sc.nextInt();
            for (int i = 1; i <= numOfVertices; i++) {
                vertices.put(i,new Vertex(i));
            }

            sc.nextLine();

        }
        else{
            throw new InputMismatchException("Missing number of vertices");
        }
    }

    void displayGraphState(){

        for (Vertex v: vertices.values()) {

            System.out.printf("Vertex No. %d: Evacuees: %b",
                    v.getId(), v.getEvacuees());
            System.out.print("\nNeighbours: {");

            Iterator<Integer> neighboursIterator = v.getNeighbours().iterator();

            while (neighboursIterator.hasNext()){

                int neighbourId = neighboursIterator.next();

                System.out.printf("%d", neighbourId);

                if (v.getEdges().get(neighbourId).isBlocked()) {
                    System.out.print(" - blocked");
                }
                if (neighboursIterator.hasNext())
                    System.out.print(", ");
            }

            System.out.println("}\n");

        }
    }

    int getNumberOfVertices(){

        return vertices.size();

    }

    int getNumberOfEdges(){
        return edges.size();
    }

    HashMap<Integer, Vertex> getVertices() {
        return vertices;
    }

    Vertex getVertex(int index){
        return vertices.get(index);
    }

    Edge getEdge(int index){
        return edges.get(index);
    }

}
