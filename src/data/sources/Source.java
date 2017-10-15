package data.sources;

import data.structures.*;

public interface Source {
    Build[] getBuilds();
    BuildStep[] getStepsForBuild(Build build);
    String[] getProjects();
}
