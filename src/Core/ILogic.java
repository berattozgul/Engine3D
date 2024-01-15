package Core;

import Core.Managers.MouseInput;

public interface ILogic {

    void init() throws Exception;

    void input();

    void update(MouseInput mouseInput);

    void render();

    void cleanup();
}
