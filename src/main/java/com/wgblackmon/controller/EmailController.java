package com.esrx.services.auth.controller;

import com.esrx.inf.chaos.ChaosAnnotations.EnableChaos; import com.esrx.services.auth.exception.InvalidRequest; import com.esrx.services.auth.model.EmailRequest; import com.esrx.services.auth.model.EmailResponse; import com.esrx.services.auth.service.EmailService; import com.esrx.services.auth.service.CookieService; import com.express_scripts.inf.logging.Loggable; import io.swagger.v3.oas.annotations.Operation; import io.swagger.v3.oas.annotations.Parameter; import io.swagger.v3.oas.annotations.responses.ApiResponse; import io.swagger.v3.oas.annotations.responses.ApiResponses; import io.swagger.v3.oas.annotations.tags.Tag; import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils; import org.springframework.http.HttpEntity; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.PostMapping; import org.springframework.web.bind.annotation.RequestBody; import org.springframework.web.bind.annotation.RequestMapping; import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource; import javax.servlet.http.HttpServletRequest; import javax.validation.Valid; import javax.ws.rs.core.MediaType;

import static com.esrx.services.auth.util.Constants.*; 
import static com.esrx.services.auth.util.CookieUtils.auditLog;

@Slf4j 
@Loggable 
@RestController 
@RequestMapping("/cws/{version}/authservices/client") @Tag(name = "Contact Us Post", description = "Submit Client Website Contact Us Form") 

public class EmailController { 

@Resource EmailService emailService;

@Resource
private CookieService cookieService;

@PostMapping(path = "/postBatchLogins", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
@Operation(summary = "Batch Logins", description = "Process batch logins for EmailHistoryModel.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Batch logins processed successfully."),
        @ApiResponse(responseCode = "400", description = "Bad request."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
})
public HttpEntity<String> postBatchLogins(
        @Parameter(name = "emailHistoryModels", description = "List of EmailHistoryModels", required = true)
        @Valid @RequestBody ArrayList<EmailHistoryModel> emailHistoryModels) {
    // Implement your logic here
    return new ResponseEntity<>("Batch logins processed successfully", HttpStatus.OK);
}

// end postBatchLogins()

@PostMapping(path = "/contactUs", consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON)
@EnableChaos
@Operation(summary = "Report A Problem", description = "Report a Problem Form in Evernorth Control Center(ECC) portal.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ContactUs page post was successful."),
        @ApiResponse(responseCode = "400", description = "Bad request."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
})
public HttpEntity<EmailResponse> postEmail(
        @Parameter(name = "request", description = "ContactUs form fields", required = true)
        @Valid @RequestBody EmailRequest emailRequest, HttpServletRequest httpRequest) throws InvalidRequest {
    String userid = cookieService.fetchUserId(httpRequest);
    if (StringUtils.isEmpty(userid)) {
        log.info("Invalid session to invoke ContactUs service!");
        throw new InvalidRequest("INVALID SESSION");
    }

    emailRequest.setUserid(userid);
    EmailResponse emailResponse = new EmailResponse();
    if (!emailService.validate(emailRequest, emailResponse))
        return new ResponseEntity<>(emailResponse, HttpStatus.BAD_REQUEST);

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    try {
        boolean retVal = emailService.sendEmail(emailRequest);
        if (retVal) {
            emailResponse.setMessage("Email sent successfully");
            httpStatus = HttpStatus.OK;
            auditLog(ECCCONTACTUS, "Information", SUCCESS, userid, "ContactUs Form submitted successfully");
        } else {
            emailResponse.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            auditLog(ECCCONTACTUS, "Error", FAILURE, userid, "ContactUs Form failed to submit");
        }
    } catch (Exception ex) {
        emailResponse.setMessage("Other error: " + ex.getMessage());
        auditLog(ECCCONTACTUS, "Error", FAILURE, userid, "Unexpected error while submitting ContactUs form");
    }

    emailResponse.setCode(httpStatus.value());
    return new ResponseEntity<>(emailResponse, httpStatus);
}


@PostMapping(path = "/contactUs", consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON)
@EnableChaos
@Operation(summary = "Report A Problem", description = "Report a Problem Form in Evernorth Control Center(ECC) portal.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ContactUs page post was successful."),
        @ApiResponse(responseCode = "400", description = "Bad request."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
})
public HttpEntity<EmailResponse> postEmail(
        @Parameter(name = "request", description = "ContactUs form fields", required = true)
        @Valid @RequestBody EmailRequest emailRequest, HttpServletRequest httpRequest) throws InvalidRequest {
    String userid = cookieService.fetchUserId(httpRequest);
    if (StringUtils.isEmpty(userid)) {
        log.info("Invalid session to invoke ContactUs service!");
        throw new InvalidRequest("INVALID SESSION");
    }

    emailRequest.setUserid(userid);
    EmailResponse emailResponse = new EmailResponse();
    if (!emailService.validate(emailRequest, emailResponse))
        return new ResponseEntity<>(emailResponse, HttpStatus.BAD_REQUEST);

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    try {
        boolean retVal = emailService.sendEmail(emailRequest);
        if (retVal) {
            emailResponse.setMessage("Email sent successfully");
            httpStatus = HttpStatus.OK;
            auditLog(ECCCONTACTUS, "Information", SUCCESS, userid, "ContactUs Form submitted successfully");
        } else {
            emailResponse.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            auditLog(ECCCONTACTUS, "Error", FAILURE, userid, "ContactUs Form failed to submit");
        }
    } catch (Exception ex) {
        emailResponse.setMessage("Other error: " + ex.getMessage());
        auditLog(ECCCONTACTUS, "Error", FAILURE, userid, "Unexpected error while submitting ContactUs form");
    }

    emailResponse.setCode(httpStatus.value());
    return new ResponseEntity<>(emailResponse, httpStatus);
}