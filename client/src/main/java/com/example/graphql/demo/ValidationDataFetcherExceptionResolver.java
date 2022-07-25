package com.example.graphql.demo;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;

// see here for another example https://github.com/spring-projects/spring-graphql/issues/160#issuecomment-979043065
@Component
public class ValidationDataFetcherExceptionResolver extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment environment) {
        if (ex instanceof ConstraintViolationException e) {
            return GraphqlErrorBuilder.newError(environment).message("Validation failure: "+ e.getMessage()).build();
        }
        return null;
    }

}
