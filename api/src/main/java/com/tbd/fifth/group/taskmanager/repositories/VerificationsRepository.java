package com.tbd.fifth.group.taskmanager.repositories;

public interface VerificationsRepository {
    //validateInput
    //input:
    //input: Input introduced that will be verified for security measures
    //output:
    // Boolean: Authorization boolean
    public Boolean validateInput(String input);
}
