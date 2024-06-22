package interfaces;

import java.io.File;

@FunctionalInterface
public interface IFileInputEvent {
    void onItemClick(File file);
}
