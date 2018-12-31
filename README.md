# Hurricane-Simulator-Probabilistic-Reasoning
An assigmnet I made for an intro course on Artificial Intelligence.
The program constructs a Bayesian Network from a graph, which represents an hurricane evacuation scenario.
The network is composed of Flood and Evacuees nodes for every vertex (which represent whether there is a flood or evacuees in that vertex),
and a Blockage node for every edge. The user can input evidence,
and based on that run a probabilistic reasoning algorithm to get the probabilities of floods, blockages, evacuees or open paths in the graph.

The reasoning algorithm is the simple enumeration presented in "Artificial Intelligence: A Modern Approach" by Stuart Russel and Peter Norvig.
