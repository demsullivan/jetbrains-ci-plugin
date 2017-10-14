package data.structures.circleci;

import data.structures.Build;

public class CircleCiBuild implements Build {
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

    public String getVcsUrl() {
        return vcs_url;
    }

    public String getBuildUrl() {
        return build_url;
    }

    public String getBuildNum() {
        return Integer.toString(build_num);
    }

    public String getBranch() {
        return branch;
    }

    public String getRevision() {
        return vcs_revision;
    }

    public String getRevisionTitle() {
        return subject;
    }

    public int getBuildTime() {
        return build_time_millis;
    }

    public String getBuildStatus() {
        return status;
    }

    public String getBuildResult() {
        return outcome;
    }
}
