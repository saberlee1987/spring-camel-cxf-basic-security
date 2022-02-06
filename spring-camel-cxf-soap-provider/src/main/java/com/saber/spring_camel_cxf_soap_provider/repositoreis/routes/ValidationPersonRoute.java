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
                    
                    ErrorSoapResponse errorSoapResponse = new ErrorSoapResponse();
                    errorSoapResponse.setCode(HttpStatus.BAD_REQUEST.value());
                    errorSoapResponse.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                    
                    PersonSoapDto personSoapDto = exchange.getIn().getBody(PersonSoapDto.class);
                    if (personSoapDto!=null){
                        Set<ConstraintViolation<PersonSoapDto>> constraintViolations = validatorAdapter.validate(personSoapDto);
                        if (constraintViolations.size() > 0) {
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
                    }
                    /// check nationalCode in header
                    if ( exchange.getIn().getHeaders().containsKey(Headers.nationalCode)){
                        
                        String nationalCode = exchange.getIn().getHeader(Headers.nationalCode,String.class);
                        if (nationalCode==null || nationalCode.trim().length()<10 || !nationalCode.trim().matches("\\d+")){
                            ValidationSoapDto validationSoapDto = new ValidationSoapDto();
                            validationSoapDto.setFieldName("nationalCode");
                            validationSoapDto.setDetailMessage("Please Enter valid nationalCode");
                            
                            if (exchange.getIn().getBody()!=null && exchange.getIn().getBody() instanceof ErrorSoapResponse){
                                ErrorSoapResponse error = exchange.getIn().getBody(ErrorSoapResponse.class);
                                error.getValidations().add(validationSoapDto);
                                exchange.getIn().setBody(error);
                            }else {
                                List<ValidationSoapDto> validationSoapDtoList = new ArrayList<>();
                                validationSoapDtoList.add(validationSoapDto);
                                errorSoapResponse.setValidations(validationSoapDtoList);
                                exchange.getIn().setBody(errorSoapResponse);
                            }
                        }
                    }
                });
    }
}
