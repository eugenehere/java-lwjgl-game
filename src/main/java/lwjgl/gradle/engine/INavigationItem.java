package lwjgl.gradle.engine;

import lwjgl.gradle.lambda.Lambda;

public interface INavigationItem extends IElement {
    boolean isActive = false;
    Lambda callback = null;

    boolean isActive();

    INavigationItem setIsActive(boolean value);

    INavigationItem submit();

    INavigationItem setCallback(Lambda callback);
}
