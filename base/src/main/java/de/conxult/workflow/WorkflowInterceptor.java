/*
 * Copyright by https://conxult.de
 */
package de.conxult.workflow;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.time.OffsetDateTime;

/**
 *
 * @author joerg
 */

@Interceptor
@Priority(0)
@WorkflowTask
public class WorkflowInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        var ctxMethod = ctx.getMethod();
        var ctxClass  = ctxMethod.getDeclaringClass();

        if (Workflow.class.isAssignableFrom(ctxClass)) {
            Workflow workflowTask = (Workflow) ctx.getTarget();
            workflowTask.workflowTasks.addFirst(ctxClass.getSimpleName() + "." + ctx.getMethod().getName());
            try {
                if (workflowTask.getStartedAt() == null) {
                    workflowTask.setStartedAt(OffsetDateTime.now());
                }
                workflowTask.log("enter");
                return ctx.proceed();
            } catch (Exception exception) {
                workflowTask.log("exception", exception.getMessage());
                throw exception;
            } finally {
                workflowTask.log("leave");
                workflowTask.workflowTasks.remove(0);
            }

        } else {
            return ctx.proceed();
        }

    }

}