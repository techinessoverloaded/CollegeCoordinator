package com.techiness.collegecoordinator.abstraction;

/**
 * An Interface used for representing the behaviour of UI which is shown at the Entry Point of the Application.
 * It extends the {@link UserInterface} interface. So, all the implementations of this interface must implement the {@link #displayUIForFirstTimeAndExecuteActions()} method of the {@link UserInterface} interface.
 */
public interface EntryPointUI extends UserInterface
{
    /**
     * This method is used to display first-time UI and perform one-time actions based on the User's requirements.
     */
    void displayUIForFirstTimeAndExecuteActions();
}
