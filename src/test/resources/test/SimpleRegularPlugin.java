package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmken.oss.yapf.BasePlugin;

public class SimpleRegularPlugin extends BasePlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRegularPlugin.class);

    public int testRegular() {
        try {
            Class.forName("test.SimpleSpecPlugin", false, SimpleRegularPlugin.class.getClassLoader());
        } catch (ClassNotFoundException | ClassCircularityError cause) {
            // ClassCircularityError: This happens in test mode if the class was not found.

            throw new AssertionError("Regular plugin should have access to spec plugin!", cause);
        }

        try {
            Class.forName("test.SimpleImplPlugin", false, SimpleRegularPlugin.class.getClassLoader());

            throw new AssertionError("Regular plugin should not have access to impl plugin!");
        } catch (ClassNotFoundException | ClassCircularityError dummy) {
            // ClassCircularityError: This happens in test mode if the class was not found.

            // Nothing to do.
        }

        return 42;
    }
}
