package com.linkare.irn.nascimento.model.listener.audit;

import java.util.Optional;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public final class AuditInfoThreadLocal {

    private static final ThreadLocal<AuditInfo> AUDIT_MAP = new InheritableThreadLocal<>();

    private AuditInfoThreadLocal() {
    }

    public static Optional<AuditInfo> get() {
	return Optional.ofNullable(AUDIT_MAP.get());
    }

    public static void set(final AuditInfo auditInfo) {
	AUDIT_MAP.set(auditInfo);
    }

    public static void remove() {
	AUDIT_MAP.remove();
    }
}