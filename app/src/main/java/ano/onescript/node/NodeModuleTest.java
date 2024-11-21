package ano.onescript.node;

import android.content.Context;
import com.caoccao.javet.interception.logging.JavetStandardConsoleInterceptor;
import com.caoccao.javet.interop.NodeRuntime;
import com.caoccao.javet.interop.V8Host;

import java.io.File;

public class NodeModuleTest {
    public static void test(Context context) {
        try {
            NodeRuntime engine = V8Host.getNodeInstance().createV8Runtime();

            JavetStandardConsoleInterceptor console = new JavetStandardConsoleInterceptor(engine);
            console.register(engine.getGlobalObject());

            engine.getExecutor(new File(context.getFilesDir(), "node-project/index.js")).setModule(true).executeVoid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
