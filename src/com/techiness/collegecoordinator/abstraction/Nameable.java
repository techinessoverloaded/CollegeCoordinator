package com.techiness.collegecoordinator.abstraction;

/**
 * Denotes that an implementing class has a name and defines accessors and mutators for the same.
 * Extends the {@link Identifiable} interface.
 */
public interface Nameable extends Identifiable
{
    String getName();
    void setName(String name);
}
