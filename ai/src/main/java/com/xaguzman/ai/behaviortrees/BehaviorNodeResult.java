package com.xaguzman.ai.behaviortrees;

public enum BehaviorNodeResult {
    /** The node executed successfully */
    Success,
    /** The node is not yet finished, will be checked next call  */
    Running,
    /** The node conditions are unfulfilled */
    Failure
}
