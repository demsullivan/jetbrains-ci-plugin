package data.structures;

public interface Build {
    public String getVcsUrl();
    public String getBuildUrl();
    public String getBuildNum();
    public String getBranch();
    public String getRevision(); // TODO: build a Revision class?
    public String getRevisionTitle();
    public int getBuildTime();
    public String getBuildStatus();
    public String getBuildResult();
}
