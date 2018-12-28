package HurricaneEvacuation;

import java.util.*;

class BayesNetwork{

    private ArrayList<BayesNode> nodes;

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

        nodes.forEach(BayesNode::constructCpt);
    }
}

abstract class BayesNode<T> {

    ArrayList<BayesNode> parents;
    T element;
    Map<Map<BayesNode,Boolean>, Probability> cpt = new HashMap<>();


    abstract void setParents(List<BayesNode> list);

    void constructCpt() {

        constructEntries(new HashMap<>(), new LinkedList<>(this.parents));
    }

    private void constructEntries(Map<BayesNode, Boolean> tableEntry, Queue<BayesNode> left){

        if (left.size() == 0){
            this.cpt.put(new HashMap<>(tableEntry), computeConditionalProbability(tableEntry));
        }
        else{
            BayesNode nextParent = left.poll();

            tableEntry.put(nextParent,true);
            constructEntries(tableEntry, new LinkedList<>(left));

            tableEntry.put(nextParent, false);
            constructEntries(tableEntry, new LinkedList<>(left));

        }
    }

    static <C extends BayesNode> ArrayList<C> filterList
            (List<BayesNode> list,Class<C> subclass){

        ArrayList<C> result = new ArrayList<>();

        for (BayesNode node : list) {
            if(subclass.isInstance(node))
                result.add(subclass.cast(node));
        }

        return result;

    }

    abstract Probability computeConditionalProbability(Map<BayesNode, Boolean> tableEntry);
}

class FloodBayesNode extends BayesNode<Vertex>{

    FloodBayesNode(Vertex vertex) {
        this.element = vertex;
    }

    @Override
    void setParents(List<BayesNode> list) {
        this.parents = null;
    }

    @Override
    void constructCpt() {

        cpt.put(null, computeConditionalProbability(null));

    }

    @Override
    Probability computeConditionalProbability(Map<BayesNode, Boolean> tableEntry) {
        return this.element.getFloodProb();
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

    @Override
    Probability computeConditionalProbability(Map<BayesNode, Boolean> tableEntry) {

        final double inhibitedProb = 1 - 0.6 / this.element.getWeight();
        int trueCounter = 0;

        for (Map.Entry<BayesNode, Boolean> entry : tableEntry.entrySet()) {
            Boolean aBoolean = entry.getValue();
            if (aBoolean)
                trueCounter++;
        }

        if (trueCounter == 0){
            return new Probability(0.001);
        }
        else{
            return new Probability(1 - Math.pow(inhibitedProb, trueCounter));
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

    @Override
    Probability computeConditionalProbability(Map<BayesNode, Boolean> tableEntry) {

        final double inhibitedProbGreatThan4 = 1 - 0.8;
        final double inhibitedProbLessThan4 = 1 - 0.4;
        int trueGreatThan4Counter = 0;
        int trueLessThan4Counter = 0;

        for (Map.Entry<BayesNode, Boolean> entry : tableEntry.entrySet()) {

            BlockageBayesNode parent = (BlockageBayesNode)entry.getKey();
            Boolean aBoolean = entry.getValue();
            if (aBoolean){
                if (parent.element.getWeight() > 4){
                    trueGreatThan4Counter++;
                }
                else{
                    trueLessThan4Counter++;
                }
            }
        }

        if (trueGreatThan4Counter + trueLessThan4Counter == 0){
            return new Probability(0.001);
        }
        else if (trueLessThan4Counter == 0){
            return new Probability(1 -
                    Math.pow(inhibitedProbGreatThan4, trueGreatThan4Counter));
        }
        else if (trueGreatThan4Counter == 0){
            return new Probability(1 -
                    Math.pow(inhibitedProbLessThan4, trueLessThan4Counter));
        }
        else{
            return new Probability(1 -
                    (Math.pow(inhibitedProbGreatThan4, trueGreatThan4Counter) *
                            Math.pow(inhibitedProbLessThan4, trueLessThan4Counter)));
        }
    }
}
