package HurricaneEvacuation;

import java.util.*;

class Vertex {

    private int id;

    private State evacuees = State.UNKNOWN;
    private State flood = State.UNKNOWN;
    private Probability floodProb;

    private HashMap<Integer, Edge> edges;

    Vertex(int id) {
        this.id = id;
        this.edges = new HashMap<>();
    }

    void submitEdge(Edge edge) {

        this.edges.put(edge.getId(), edge);

    }

    public float getFloodProb() {
        return floodProb.getProbability();
    }

    void setFloodProb(float floodProb) {
        this.floodProb = new Probability(floodProb);
    }

    void setEvacuees(State evacuees) {
        this.evacuees = evacuees;

    }

    State getFlood() {
        return flood;
    }

    void setFlood(State flood) {
        this.flood = flood;
    }

    State getEvacuees() {
        return evacuees;
    }

    HashMap<Integer, Edge> getEdges() {
        return edges;
    }

    int getId() {
        return id;
    }

    Set<Integer> getNeighbours() {

        return edges.keySet();

    }

    String getNeighboursToString() {

        Iterator<Integer> iterator = getNeighbours().iterator();

        StringBuilder ans = new StringBuilder("[");

        while (iterator.hasNext()) {
            ans.append(iterator.next());
            if (iterator.hasNext()) {
                ans.append(", ");
            }
        }

        ans.append("]");
        return ans.toString();
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }


}

