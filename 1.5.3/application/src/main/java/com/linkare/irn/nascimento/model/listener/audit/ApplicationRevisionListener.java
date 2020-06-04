package com.linkare.irn.nascimento.model.listener.audit;

import org.hibernate.envers.RevisionListener;

import com.linkare.irn.nascimento.model.audit.Revision;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ApplicationRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
	final Revision revision = (Revision) revisionEntity;

	final AuditInfo auditInfo = AuditInfoThreadLocal.get().orElse(AuditInfo.EMPTY);

	revision.setAuditInfo(auditInfo);
    }
}