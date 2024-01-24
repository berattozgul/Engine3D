package Core;

import Core.Managers.MouseInput;
/**
 * The ILogic interface represents the logic component of a game or application.
 * Classes implementing this interface are responsible for managing the initialization,
 * input handling, updating, rendering, and cleanup operations.
 */
public interface ILogic {

    /**
     * Initializes the logic component.
     *
     * @throws Exception If an error occurs during initialization.
     */
    void init() throws Exception;

    /**
     * Handles user input for the logic component.
     */
    void input();

    /**
     * Updates the logic component based on the specified mouse input.
     *
     * @param mouseInput The mouse input used for updating.
     */
    void update(MouseInput mouseInput);

    /**
     * Renders the current state of the logic component.
     */
    void render();

    /**
     * Cleans up resources and performs necessary cleanup operations for the logic component.
     */
    void cleanup();
}
