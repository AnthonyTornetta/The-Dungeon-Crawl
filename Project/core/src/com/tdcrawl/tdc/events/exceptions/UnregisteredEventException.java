package com.tdcrawl.tdc.events.exceptions;

public class UnregisteredEventException extends RuntimeException
{
	/**
	 * Eclipse generated ID
	 */
	private static final long serialVersionUID = 5607789355554706913L;
	
	private String message;
	
	/**
	 * Thrown whenever an unregistered event is referenced
	 */
	public UnregisteredEventException()
	{
		this("Event unregistered!");
	}
	
	/**
	 * Thrown whenever an unregistered event is referenced
	 * @param message What to say
	 */
	public UnregisteredEventException(String message)
	{
		this.message = message;
	}
	
	@Override
	public String getMessage() { return message; }
}
