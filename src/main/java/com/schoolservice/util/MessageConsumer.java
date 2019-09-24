/*
 * package com.schoolservice.util;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.jms.annotation.EnableJms; import
 * org.springframework.jms.annotation.JmsListener; import
 * org.springframework.stereotype.Component;
 * 
 * @Component
 * 
 * @EnableJms public class MessageConsumer {
 * 
 * Logger logger = LoggerFactory.getLogger(this.getClass());
 * 
 * private String message;
 * 
 * @JmsListener(destination = "student-jms-queue") public void
 * receiveQueue(String text) {
 * logger.info("Reading MQ Messages {}:::::::::::::::::::",text);
 * setMessage(text); }
 * 
 * public String getMessage() { return message; }
 * 
 * public void setMessage(String message) { this.message = message; }
 * 
 * 
 * 
 * }
 */