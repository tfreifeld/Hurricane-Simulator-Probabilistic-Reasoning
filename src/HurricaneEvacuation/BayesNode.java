package HurricaneEvacuation;

import java.util.ArrayList;
import java.util.List;

abstract class BayesNode<T> {

    ArrayList<BayesNode> parents;
    T element;

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
        parents = null;
    }


}

class BlockageBayesNode extends BayesNode<Edge>{

    BlockageBayesNode(Edge edge) {
        this.element = edge;
    }

    @Override
    void setParents(List<BayesNode> list) {

        List<Vertex> incidentVertices = element.getVertices();

        List<FloodBayesNode> filteredList =
                filterList(list , FloodBayesNode.class);

        for (FloodBayesNode node : filteredList) {
            for (Vertex vertex : incidentVertices) {
                if(node.element.equals(vertex)){
                    parents.add(node);
                    break;
                }
            }
        }

    }

}

class EvacueesBayesNode extends BayesNode<Vertex>{


    EvacueesBayesNode(Vertex vertex) {
        this.element = vertex;
    }

    @Override
    void setParents(List<BayesNode> list) {

    }
}
