package data.structures;

public class Build {
    // TODO: temporarily hard-coded for CircleCI, but will need to build a base Build class or something
    public String vcs_url;
    public String build_url;
    public int build_num;
    public String branch;
    public String vcs_revision;
    public String committer_name;
    public String comitter_email;
    public String subject;
    public String body;
    public String why;
    public String dont_build;
    public String queued_at;
    public String start_time;
    public String stop_time;
    public int build_time_millis;
    public String username;
    public String reponame;
    public String lifecycle;
    public String outcome;
    public String status;
    public int retry_of;

    static class Previous {
        public String status;
        public int build_num;
    }
}
