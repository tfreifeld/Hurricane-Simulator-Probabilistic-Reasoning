package HurricaneEvacuation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BayesNetwork{

    private static ArrayList<BayesNode> nodes;

    BayesNetwork() {
        nodes = new ArrayList<>();
    }

    void construct() {

        createNodes();
        addLinks();
        createCPTs();

    }

    private void createNodes() {
        Simulator.getGraph().getVertices().forEach(((id, vertex) ->
                nodes.add(new FloodBayesNode(vertex))));

        Simulator.getGraph().getEdges().forEach((id, edge) ->
                nodes.add(new BlockageBayesNode(edge)));

        Simulator.getGraph().getVertices().forEach((id, vertex) ->
                nodes.add(new EvacueesBayesNode(vertex)));
    }

    private void addLinks() {
        for (int i = 0; i < nodes.size(); i++) {

            nodes.get(i).setParents(nodes.subList(0, i));

        }
    }

    private void createCPTs() {
    }
}

abstract class BayesNode<T> {

    ArrayList<BayesNode> parents;
    T element;
    HashMap<ArrayList<Boolean>, Probability> cpt = new HashMap<>();


    abstract void setParents(List<BayesNode> list);

    static <C extends BayesNode> ArrayList<C> filterList
            (List<BayesNode> list,Class<C> subclass){

        ArrayList<C> result = new ArrayList<>();

        for (BayesNode node : list) {
            if(subclass.isInstance(node))
                result.add(subclass.cast(node));
        }

        return result;

    }
}

class FloodBayesNode extends BayesNode<Vertex>{

    FloodBayesNode(Vertex vertex) {
        this.element = vertex;
    }

    @Override
    void setParents(List<BayesNode> list) {
        this.parents = null;
    }


}

class BlockageBayesNode extends BayesNode<Edge>{

    BlockageBayesNode(Edge edge) {
        this.element = edge;
    }

    @Override
    void setParents(List<BayesNode> list) {

        this.parents = new ArrayList<>(2);


        List<FloodBayesNode> filteredList =
                filterList(list , FloodBayesNode.class);

        List<Vertex> incidentVertices = this.element.getVertices();

        for (FloodBayesNode node : filteredList) {
            for (Vertex vertex : incidentVertices) {
                if(node.element.equals(vertex)){
                    parents.add(node);
                    break;
                }
            }
            if(this.parents.size() == 2)
                break;
        }

    }

}

class EvacueesBayesNode extends BayesNode<Vertex>{


    EvacueesBayesNode(Vertex vertex) {
        this.element = vertex;
    }

    @Override
    void setParents(List<BayesNode> list) {

        this.parents = new ArrayList<>();

        List<BlockageBayesNode> filteredList =
                filterList(list , BlockageBayesNode.class);

        Map<Integer,Edge> incidentEdges = this.element.getEdges();

        for (BlockageBayesNode node : filteredList) {

            if (incidentEdges.containsKey(node.element.getId())) {
                parents.add(node);
            }
        }
    }
}
