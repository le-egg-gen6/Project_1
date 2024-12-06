package org.myproject.project1.exception.custom;

/**
 * @author nguyenle
 * @since 4:15 PM Fri 12/6/2024
 */
public class ResourceUsedException extends RuntimeException {

	public ResourceUsedException(String message) {
		super(message);
	}
}
