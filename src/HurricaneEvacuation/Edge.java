package HurricaneEvacuation;

import java.util.ArrayList;
import java.util.List;

class Edge {

    private int id;
    private List<Vertex> vertices = new ArrayList<>(2);
    private int weight;
    private State blocked = State.UNKNOWN;

    Edge(int id, Vertex in, Vertex out, int weight) {

        this.id = id;
        vertices.add(in);
        vertices.add(out);
        this.weight = weight;

        in.submitEdge(this);
        out.submitEdge(this);
    }

    List<Vertex> getVertices() {

        return vertices;
    }



    State getBlocked() {
        return blocked;
    }

    Vertex getNeighbour(Vertex vertex) {
        if (vertex.equals(vertices.get(0))) {
            return vertices.get(1);
        } else {
            return vertices.get(0);
        }
    }

    int getWeight() {
        return weight;
    }

    void setBlocked(State blocked) {
        this.blocked = blocked;
    }

    int getId() {
        return id;
    }
}
