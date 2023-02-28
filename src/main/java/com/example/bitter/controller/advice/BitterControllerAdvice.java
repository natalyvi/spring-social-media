package com.example.bitter.controller.advice;

import com.example.bitter.dto.ErrorDto;
import com.example.bitter.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = {"com.example.bitter.controller"})
@ResponseBody
public class BitterControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
   public ErrorDto handleBadRequestException(HttpServletRequest request, BadRequestException badRequestException) {
       return new ErrorDto(badRequestException.getMessage());
   }

   // TODO: add other exceptions here
}
