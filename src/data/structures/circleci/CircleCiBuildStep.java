package data.structures.circleci;

import data.structures.BuildResult;
import data.structures.BuildStep;
import data.structures.circleci.CircleCiBuildStepAction;

public class CircleCiBuildStep implements BuildStep {
    String name;

    CircleCiBuildStepAction[] actions;

    public String getStepName() {
        return name;
    }

    public int getStatus() {
        String result = actions[0].status;
        if (result.equals("success")) {
            return BuildResult.Success;
        } else if (result.equals("failed")) {
            return BuildResult.Failed;
        } else {
            return BuildResult.Unknown;
        }
    }
}
