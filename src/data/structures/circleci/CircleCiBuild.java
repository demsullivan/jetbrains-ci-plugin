package data.structures.circleci;

import data.structures.Build;
import data.structures.BuildResult;

public class CircleCiBuild implements Build {
    public String vcs_url;
    public String build_url;
    public int build_num;
    public String branch;
    public String vcs_revision;
    public String committer_name;
    public String committer_email;
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
        return vcs_revision.substring(0, 7);
    }

    public String getRevisionUrl() {
        if (vcs_url.indexOf("github") > -1) {
            return getGithubCommitUrl();
        } else {
            return getBitbucketCommitUrl();
        }
    }

    public String getGithubCommitUrl() {
        return vcs_url + "/commit/" + vcs_revision;
    }

    public String getBitbucketCommitUrl() {
        return "";
    }

    public String getRevisionTitle() {
        return subject;
    }

    public String getCommitter() {
        return committer_email;
    }

    public int getBuildTime() {
        return build_time_millis;
    }

    public String getBuildStatus() {
        return status;
    }

    public int getBuildResult() {
        if (outcome.equals("success")) {
            return BuildResult.Success;
        } else if (outcome.equals("failed")) {
            return BuildResult.Failed;
        } else {
            return BuildResult.Unknown;
        }

    }

    public String toString() {
        return subject;
    }
}
