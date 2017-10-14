package data.structures;

public interface Build {
    public String getVcsUrl();
    public String getBuildUrl();
    public int getBuildNum();
    public String getBranch();
    public String getRevision(); // TODO: build a Revision class?
    public int getBuildTime();
    public String getBuildStatus();
    public String getBuildResult();
}
