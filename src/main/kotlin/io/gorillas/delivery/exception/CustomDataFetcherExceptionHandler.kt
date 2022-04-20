package io.gorillas.delivery.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import graphql.ErrorType
import graphql.ExceptionWhileDataFetching
import graphql.GraphQLError
import graphql.GraphqlErrorException
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import graphql.execution.ResultPath
import graphql.language.SourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CustomDataFetcherExceptionHandler: DataFetcherExceptionHandler {
    private val logger: Logger = LoggerFactory.getLogger(CustomDataFetcherExceptionHandler::class.java)

    override fun onException(handlerParameters: DataFetcherExceptionHandlerParameters): DataFetcherExceptionHandlerResult {
        val exception = handlerParameters.exception
        val sourceLocation = handlerParameters.sourceLocation
        val path = handlerParameters.path
        val error: GraphQLError = when (exception) {
            is ValidationException -> ValidationDataFetchingGraphQLError(path, exception, sourceLocation)
            else ->
                GraphqlErrorException.newErrorException()
                        .cause(exception)
                        .message(exception.message)
                        .sourceLocation(sourceLocation)
                        .path(path.toList())
                        .build()
        }
        logger.warn("Error occurred while processing request", exception)
        return DataFetcherExceptionHandlerResult.newResult().error(error).build()
    }
}

@JsonIgnoreProperties("exception")
class ValidationDataFetchingGraphQLError(
        path: ResultPath,
        exception: Throwable,
        sourceLocation: SourceLocation
) : ExceptionWhileDataFetching(
        path,
        exception,
        sourceLocation
) {
    override fun getErrorType(): ErrorType = ErrorType.ValidationError
}
