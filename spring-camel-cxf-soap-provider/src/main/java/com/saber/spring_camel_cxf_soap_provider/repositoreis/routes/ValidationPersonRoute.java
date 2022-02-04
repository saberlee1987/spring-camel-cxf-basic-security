package com.saber.spring_camel_cxf_soap_provider.repositoreis.routes;

import com.saber.spring_camel_cxf_soap_provider.soap.services.ErrorSoapResponse;
import com.saber.spring_camel_cxf_soap_provider.soap.services.PersonSoapDto;
import com.saber.spring_camel_cxf_soap_provider.soap.services.ValidationSoapDto;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ValidationPersonRoute extends AbstractRestRouteBuilder {
    @Autowired
    private SpringValidatorAdapter validatorAdapter;

    @Override
    public void configure() throws Exception {
        super.configure();

        from(String.format("direct:%s", Routes.CHECK_BEAN_VALIDATION_ROUTE))
                .routeId(Routes.CHECK_BEAN_VALIDATION_ROUTE)
                .routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
                .process(exchange -> {
                    PersonSoapDto personSoapDto = exchange.getIn().getBody(PersonSoapDto.class);
                    Set<ConstraintViolation<PersonSoapDto>> constraintViolations = validatorAdapter.validate(personSoapDto);
                    if (constraintViolations.size() > 0) {
                        ErrorSoapResponse errorSoapResponse = new ErrorSoapResponse();
                        errorSoapResponse.setCode(HttpStatus.BAD_REQUEST.value());
                        errorSoapResponse.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                        for (ConstraintViolation<PersonSoapDto> constraintViolation : constraintViolations) {
                            List<ValidationSoapDto> validationSoapDtoList = new ArrayList<>();
                            ValidationSoapDto validationSoapDto = new ValidationSoapDto();
                            validationSoapDto.setFieldName(constraintViolation.getPropertyPath().toString());
                            validationSoapDto.setDetailMessage(constraintViolation.getMessage());
                            validationSoapDtoList.add(validationSoapDto);
                            errorSoapResponse.setValidations(validationSoapDtoList);
                        }
                        exchange.getIn().setBody(errorSoapResponse);
                        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST.value()));
                    }
                });
    }
}
