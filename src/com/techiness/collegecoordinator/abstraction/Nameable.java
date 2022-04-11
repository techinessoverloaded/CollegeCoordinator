package com.techiness.collegecoordinator.abstraction;

/**
 * An Interface that denotes that an implementing class has a name and ensures that it defines accessors and mutators for the same.
 * It extends the {@link Identifiable} interface.
 */
public interface Nameable extends Identifiable
{
    /**
     * @return The {@link String} Name associated with the {@link Nameable} implementation.
     */
    String getName();

    /**
     * Used to set the Name associated with the {@link Nameable} implementation.
     * @param name The {@link String} to be set as ID.
     */
    void setName(String name);
}
