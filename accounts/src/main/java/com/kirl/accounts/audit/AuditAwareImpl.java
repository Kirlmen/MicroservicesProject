package com.kirl.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditAwareImpl implements AuditorAware<String> {

	/**
	 * Returns the current auditor of the application
	 * @return the current auditor
	 */
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of("ACCOUNTS_MS"); //hardcoded value till spring security impl
	}
}
