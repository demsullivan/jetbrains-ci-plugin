package data.structures.circleci;

import data.structures.BuildStep;
import data.structures.circleci.CircleCiBuildStepAction;

public class CircleCiBuildStep implements BuildStep {
    String name;

    CircleCiBuildStepAction[] actions;

    public String getStepName() {
        return name;
    }
}
