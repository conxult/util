/*
 * Copyright by https://conxult.de
 */
package de.conxult.workflow;

import jakarta.enterprise.inject.spi.CDI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author joerg
 */
public class Workflow<W extends Workflow> {

    @Getter @Setter
    OffsetDateTime         startedAt;

    @Getter
    List<WorkflowLogEntry> logEntries   = new ArrayList<>();

    LinkedList<String>     workflowTasks = new LinkedList<>();

    public W log(String message, Object... parameters) {
        var workflowLogEntry = new WorkflowLogEntry(workflowTasks.getFirst(), message, parameters);
        logEntries.add(workflowLogEntry);
        return (W) this;
    }

    public W pushTask(String workflowTask) {
        workflowTasks.addFirst(workflowTask);
        return (W) this;
    }

    public W popTask() {
        workflowTasks.removeFirst();
        return (W) this;
    }

    public static <W extends Workflow> W subOf(Workflow workflow, Class<W> clazz) {
        var subWorkflow = CDI.current().select(clazz).get();
        subWorkflow.logEntries = workflow.logEntries;
        subWorkflow.workflowTasks = workflow.workflowTasks;
        return subWorkflow;
    }

    public static <W extends Workflow> W of(Class<W> clazz) {
        return (W) CDI.current().select(clazz).get()
            .pushTask(clazz.getSimpleName())
            .log("created");
    }

}
