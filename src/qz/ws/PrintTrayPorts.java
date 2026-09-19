package qz.ws;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import qz.common.Constants;
import qz.utils.ArgValue;

import java.util.Collections;

/**
 * Aplica as portas fixas do projeto e defaults de segurança (localhost / silent).
 */
public final class PrintTrayPorts {
    private static final Logger log = LogManager.getLogger(PrintTrayPorts.class);

    private PrintTrayPorts() {}

    public static WebsocketPorts resolve() {
        applySystemProperties();
        log.info("PrintTray fixed ports insecure={} secure={}", Constants.WEBSOCKET_PORT, Constants.WEBSOCKET_SECURE_PORT);
        return WebsocketPorts.fromList(
                Collections.singletonList(Constants.WEBSOCKET_SECURE_PORT),
                Collections.singletonList(Constants.WEBSOCKET_PORT)
        );
    }

    public static int getInsecurePort() {
        return Constants.WEBSOCKET_PORT;
    }

    public static int getSecurePort() {
        return Constants.WEBSOCKET_SECURE_PORT;
    }

    private static void applySystemProperties() {
        System.setProperty(ArgValue.WEBSOCKET_INSECURE_PORTS.getMatch(), Integer.toString(Constants.WEBSOCKET_PORT));
        System.setProperty(ArgValue.WEBSOCKET_SECURE_PORTS.getMatch(), Integer.toString(Constants.WEBSOCKET_SECURE_PORT));
        System.setProperty(ArgValue.SECURITY_WSS_HOST.getMatch(), "127.0.0.1");
        System.setProperty(ArgValue.SECURITY_WSS_HTTPSONLY.getMatch(), "false");
        System.setProperty(ArgValue.SECURITY_FILE_ENABLED.getMatch(), "false");
        System.setProperty(ArgValue.SECURITY_PRINT_TOHOST.getMatch(), "false");
        System.setProperty(ArgValue.SECURITY_PRINT_TOFILE.getMatch(), "false");
    }
}
