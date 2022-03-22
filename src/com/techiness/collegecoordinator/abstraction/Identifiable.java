package com.techiness.collegecoordinator.abstraction;

/**
 * Denotes that an implementing class has a unique ID and defines accessors and mutators for the same.
 */
public interface Identifiable
{
    String getId();
    void setId(String id);
}
