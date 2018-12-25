package HurricaneEvacuation;

class Probability {

    private float probability;

    Probability(float probability) {

        if (probability < 0 || probability > 1) {
            throw new NotAProbability(probability);
        }
        this.probability = probability;
    }

    float getProbability() {
        return probability;
    }

    class NotAProbability extends RuntimeException{

        NotAProbability(float probability) {
            super("Not a probability:" + probability);
        }
    }
}
