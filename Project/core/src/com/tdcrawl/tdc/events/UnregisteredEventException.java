package com.tdcrawl.tdc.events;

public class UnregisteredEventException extends RuntimeException
{
	/**
	 * Eclipse generated ID
	 */
	private static final long serialVersionUID = 5607789355554706913L;
	
	private String message;
	
	public UnregisteredEventException()
	{
		this("Event unregistered!");
	}
	
	public UnregisteredEventException(String message)
	{
		this.message = message;
	}
	
	@Override
	public String getMessage() { return message; }
}
