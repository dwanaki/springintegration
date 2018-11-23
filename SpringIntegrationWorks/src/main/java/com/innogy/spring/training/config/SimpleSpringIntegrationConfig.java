package com.innogy.spring.training.config;

import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SimpleSpringIntegrationConfig {

	@Bean
	public PublishSubscribeChannel basicChannel() {
		return new PublishSubscribeChannel();
	}
	
	@Bean
	public PublishSubscribeChannel anotherChannel() {
		return new PublishSubscribeChannel();
	}
	
	@Bean
	public IntegrationFlow forwardMessageToAnotherQueue() {
		return IntegrationFlows.from("basicChannel")
		.log()
		.handle( (payload,headers) -> this.handleMessageIntermediate(payload))
		.channel("anotherChannel")
		.get();
	}
	
	@InboundChannelAdapter(channel="basicChannel",poller=@Poller(fixedRate="1000"))
	public Message<String> inbound() {
		return new GenericMessage<String>("It is now: "+new Date());
	}
	
	//@ServiceActivator(inputChannel="basicChannel")
	public void outbound(Message<String> message) {
		log.info("##################################################################");
		log.info("--- outbound -----");
		log.info("message: "+message.getPayload());
		log.info("##################################################################");
	}
	
	@ServiceActivator(inputChannel="anotherChannel")
	public void outboundAnother(Message<String> message) {
		log.info("##################################################################");
		log.info("--- outbound another -----");
		log.info("message: "+message.getPayload());
		log.info("##################################################################");
	}
	
	public Object handleMessageIntermediate(Object payload) {
		log.warn("---> Payload: "+payload);
		return payload;
	}
	
//	@Bean(name = PollerMetadata.DEFAULT_POLLER)
//	public PollerMetadata defaultPoller() {
//		PollerMetadata pollerMetadata = new PollerMetadata();
//	    pollerMetadata.setTrigger(new PeriodicTrigger(10));
//	    return pollerMetadata;
//	}
	
}
