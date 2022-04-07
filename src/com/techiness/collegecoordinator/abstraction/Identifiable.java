package com.techiness.collegecoordinator.abstraction;

/**
 *  A Marker Interface that denotes that an implementing class has a unique ID and ensures that it defines accessors and mutators for the same.
 */
public interface Identifiable
{
    /**
     * @return The Unique ID associated with the {@link Identifiable} implementation.
     */
    String getId();

    /**
     * Used to set the Unique ID associated with the {@link Identifiable} implementation.
     * @param id The Unique {@link String} to be set as ID.
     */
    void setId(String id);
}