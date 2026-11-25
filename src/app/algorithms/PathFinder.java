package pathfinding;

import model.Path;

public interface PathFinder {
    Path findPath(PathRequest request);
}