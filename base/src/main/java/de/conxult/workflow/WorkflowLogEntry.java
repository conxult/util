/*
 * Copyright by https://conxult.de
 */
package de.conxult.workflow;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author joerg
 */

@Getter @Setter @Accessors(chain = true)
public class WorkflowLogEntry {

    OffsetDateTime time;
    String         workflowJob;
    String         message;
    Object         parameter;

    public WorkflowLogEntry(String workflowJob, String message, Object... parameter) {
        this.time        = OffsetDateTime.now();
        this.workflowJob = workflowJob;
        this.message     = message;
        this.parameter   = parameter;
    }

    public WorkflowLogEntry(String workflowJob, String message, Object parameter) {
        this.time        = OffsetDateTime.now();
        this.workflowJob = workflowJob;
        this.message     = message;
        this.parameter   = parameter;
    }

    @Override
    public String toString() {
        return "WorkflowLogEntry{" + "time=" + time + ", workflowJob=" + workflowJob + ", message=" + message + ", parameter=" + parameter + '}';
    }

}
