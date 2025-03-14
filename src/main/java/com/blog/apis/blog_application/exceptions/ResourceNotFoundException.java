package com.blog.apis.blog_application.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    
    String resourceName;
    String fieldName;
    long fieldValue;

    String fieldVal;

    public ResourceNotFoundException(String resourceName, String fieldName,long fieldValue) {
        super(String.format("%s not found with %s: %s",resourceName,fieldName,fieldValue));
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }

    public ResourceNotFoundException(String resourceName, String fieldName,String fieldVal) {
        super(String.format("%s not found with %s: %s",resourceName,fieldName,fieldVal));
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldVal=fieldVal;
    }
}
