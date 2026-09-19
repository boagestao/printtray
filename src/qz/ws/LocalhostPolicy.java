package qz.ws;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * Restricts PrintTray to loopback-only clients and enables silent print for those clients.
 */
public final class LocalhostPolicy {
    private static final Logger log = LogManager.getLogger(LocalhostPolicy.class);

    private LocalhostPolicy() {}

    public static boolean isLoopbackHost(String host) {
        if (host == null || host.isBlank()) {
            return false;
        }
        String normalized = host.trim().toLowerCase(Locale.ROOT);
        if ("localhost".equals(normalized)
                || "127.0.0.1".equals(normalized)
                || "0.0.0.0".equals(normalized)
                || "::1".equals(normalized)
                || "::".equals(normalized)
                || "::0".equals(normalized)
                || "[::1]".equals(normalized)
                || "[::]".equals(normalized)) {
            return true;
        }
        try {
            return InetAddress.getByName(host).isLoopbackAddress();
        } catch(UnknownHostException e) {
            return false;
        }
    }

    public static boolean isLoopback(Session session) {
        if (session == null || session.getRemoteAddress() == null) {
            return false;
        }
        if (!(session.getRemoteAddress() instanceof InetSocketAddress)) {
            return false;
        }
        InetSocketAddress remote = (InetSocketAddress)session.getRemoteAddress();
        InetAddress address = remote.getAddress();
        if (address == null) {
            return isLoopbackHost(remote.getHostString());
        }
        return address.isLoopbackAddress() || address.isAnyLocalAddress();
    }

    public static boolean rejectUnlessLoopback(Session session) {
        if (isLoopback(session)) {
            return false;
        }
        log.warn("Rejecting non-localhost WebSocket from {}", session.getRemoteAddress());
        try {
            session.close(4003, "PrintTray only accepts localhost connections");
        } catch(Exception ignore) {}
        return true;
    }

    /**
     * Force bind address to loopback. Treats 0.0.0.0 / :: as "all interfaces" and remaps to 127.0.0.1.
     */
    public static String resolveBindHost(String configured) {
        if (configured == null || configured.isBlank()) {
            return "127.0.0.1";
        }
        String host = configured.trim();
        if ("0.0.0.0".equals(host) || "::".equals(host) || "::0".equals(host) || "[::]".equals(host)) {
            log.info("Remapping bind host {} to 127.0.0.1 (localhost-only mode)", host);
            return "127.0.0.1";
        }
        if (!isLoopbackHost(host)) {
            log.warn("Non-loopback bind host '{}' ignored; using 127.0.0.1", host);
            return "127.0.0.1";
        }
        if ("localhost".equalsIgnoreCase(host) || "::1".equals(host) || "[::1]".equals(host)) {
            return "127.0.0.1";
        }
        return host;
    }
}
