package com.yostocks.service.accounting.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

   /*@Override
   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
       String error = "Malformed JSON request";
       return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
   }

   private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
       return new ResponseEntity<>(apiError, apiError.getStatus());
   }


   // DataIntegrityViolationException - occurs when balance is initiated for existing balance (should b unique) etc.
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
       ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    protected ResponseEntity<Object> handleNullPointerException(OptimisticLockingFailureException ex) {
        ApiError apiError = new ApiError(CONFLICT);
        apiError.setMessage("optimistic locking failed");
        return buildResponseEntity(apiError);
    }

    // ie addFraction
    *//*
     Class InterruptedException. Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread
     is interrupted, either before or during the activity. Occasionally a method may wish to test whether the current
     thread has been interrupted, and if so, to immediately throw this exception
     https://docs.oracle.com/javase/8/docs/api/index.html?java/lang/InterruptedException.html
     *//*
    @ExceptionHandler(InterruptedException.class)
    protected ResponseEntity<Object> handleInterruptedException(InterruptedException ex) {
        ApiError apiError = new ApiError(CONFLICT);
        apiError.setMessage("InterruptedException occurred");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    protected ResponseEntity<Object> handleHttpClientErrorException(HttpServerErrorException ex){
        ApiError apiError = new ApiError(ex.getStatusCode());
        apiError.setMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }*/
    // ie. endpoint 'registerTransaction' throws this exception if 'balance' for the user_id is not initialized
   /* @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(MethodArgumentNotValidException ex){
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }*/

}