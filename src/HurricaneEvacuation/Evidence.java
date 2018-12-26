package HurricaneEvacuation;

import java.util.HashMap;

class Evidence {

    private HashMap<Integer, Vertex> floodList;
    private HashMap<Integer, Vertex> evacueesList;
    private HashMap<Integer, Edge> blockageList;

    Evidence() {

        this.floodList = new HashMap<>();
        this.evacueesList = new HashMap<>();
        this.blockageList = new HashMap<>();
    }

    void submitFlood(Vertex vertex){

        if (!floodList.containsKey(vertex.getId())){
            floodList.put(vertex.getId(), vertex);
        }
    }

    void submitEvacuees(Vertex vertex){

        if (!evacueesList.containsKey(vertex.getId())){
            evacueesList.put(vertex.getId(), vertex);
        }
    }

    void submitBlockage(Edge edge){

        if (!blockageList.containsKey(edge.getId())){
            blockageList.put(edge.getId(), edge);
        }
    }

    HashMap<Integer, Vertex> getFloodList() {
        return floodList;
    }

    HashMap<Integer, Vertex> getEvacueesList() {
        return evacueesList;
    }

    HashMap<Integer, Edge> getBlockageList() {
        return blockageList;
    }
}
