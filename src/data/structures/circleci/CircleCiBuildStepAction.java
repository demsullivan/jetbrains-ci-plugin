package data.structures.circleci;

public class CircleCiBuildStepAction {
    String bash_command;
    int run_time_millis;
    String start_time;
    String end_time;
    String name;
    int exit_code;
    String type;
    int step;
    int index;
    String status;
    String source;
    Boolean failed;
    Boolean timedout;
    Boolean infrastructure_fail;
    Boolean parallel;
//    Boolean continue;
}
