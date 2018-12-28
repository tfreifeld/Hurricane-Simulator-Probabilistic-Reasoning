package HurricaneEvacuation;

class Probability {

    private double probability;

    Probability(double probability) {

        if (probability < 0 || probability > 1) {
            throw new NotAProbability(probability);
        }
        this.probability = probability;
    }

    double getProbability() {
        return probability;
    }

    class NotAProbability extends RuntimeException{

        NotAProbability(double probability) {
            super("Not a probability:" + probability);
        }
    }
}
