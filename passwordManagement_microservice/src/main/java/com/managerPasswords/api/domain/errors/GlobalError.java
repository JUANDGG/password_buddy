package com.managerPasswords.api.domain.errors;

public class GlobalError extends Exception{
    private String msg ;
    public  GlobalError(String msg){
        super(msg);
    }
}
