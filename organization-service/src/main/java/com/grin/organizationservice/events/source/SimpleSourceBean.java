package com.grin.organizationservice.events.source;

import com.grin.organizationservice.events.model.OrganizationChangeModel;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleSourceBean {

    @Setter(onMethod = @__({@Autowired}))
    private Source source;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    public void publishOrganizationChange(String action, String organizationId){
        logger.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);
        OrganizationChangeModel change =  new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                organizationId);

        source.output().send(MessageBuilder.withPayload(change).build());
    }
}
