package com.demo.rest.controller.servlet;

import com.demo.rest.modules.player.controller.api.PlayerController;
import com.demo.rest.modules.player.dto.PatchPlayerRequest;
import com.demo.rest.modules.player.dto.PutPlayerRequest;
import com.demo.rest.modules.weapon.controller.api.WeaponController;
import com.demo.rest.modules.weapon.dto.PatchWeaponRequest;
import com.demo.rest.modules.weapon.dto.PutWeaponRequest;
import com.demo.rest.modules.weapontype.controller.api.WeaponTypeController;
import com.demo.rest.modules.weapontype.dto.PatchWeaponTypeRequest;
import com.demo.rest.modules.weapontype.dto.PutWeaponTypeRequest;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Central API servlet for fetching all request from the client and preparing responses. Servlet API does not allow
 * named path parameters so wildcard is used.
 */
@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    private final PlayerController playerController;
    private final WeaponController weaponController;
    private final WeaponTypeController weaponTypeController;


    @Inject
    public ApiServlet(PlayerController playerController, WeaponController weaponController, WeaponTypeController weaponTypeController) {
        this.playerController = playerController;
        this.weaponController = weaponController;
        this.weaponTypeController = weaponTypeController;
    }


    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static final class Paths {

        public static final String API = "/api";

    }

    /**
     * Patterns used for checking servlet path.
     */
    public static final class Patterns {

        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern PLAYERS = Pattern.compile("/players/?");
        public static final Pattern PLAYER = Pattern.compile("/players/(%s)".formatted(UUID.pattern()));
        public static final Pattern PLAYER_PICTURE = Pattern.compile("/players/(%s)/picture".formatted(UUID.pattern()));

        public static final Pattern WEAPONS = Pattern.compile("/weapons/?");
        public static final Pattern WEAPON = Pattern.compile("/weapons/(%s)".formatted(UUID.pattern()));

        public static final Pattern WEAPON_TYPES = Pattern.compile("/weapontypes/?");
        public static final Pattern WEAPON_TYPE = Pattern.compile("/weapontypes/(%s)".formatted(UUID.pattern()));

        public static final Pattern WEAPON_TYPE_WEAPONS = Pattern.compile("/weapontypes/(%s)/weapons/?".formatted(UUID.pattern()));
        public static final Pattern PLAYER_WEAPONS = Pattern.compile("/players/(%s)/weapons/?".formatted(UUID.pattern()));

    }

    /**
     * JSON-B mapping object. According to open liberty documentation creating this is expensive. The JSON-B is only one
     * of many solutions. JSON strings can be built by hand {@link StringBuilder} or with JSON-P API. Both JSON-B and
     * JSON-P are part of Jakarta EE whereas JSON-B is newer standard.
     */
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            // player
            if (path.matches(Patterns.PLAYERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(playerController.getPlayers()));
                return;
            }
            else if (path.matches(Patterns.PLAYER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PLAYER, path);
                response.getWriter().write(jsonb.toJson(playerController.getPlayer(uuid)));
                return;
            }
            else if (path.matches(Patterns.PLAYER_PICTURE.pattern())) {
                response.setContentType("image/png");//could be dynamic but atm we support only one format
                UUID uuid = extractUuid(Patterns.PLAYER_PICTURE, path);
                byte[] picture = playerController.getPicture(uuid);
                response.setContentLength(picture.length);
                response.getOutputStream().write(picture);
                return;
            }
            // weapon
            else if (path.matches(Patterns.WEAPONS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(weaponController.getWeapons()));
                return;
            }
            else if (path.matches(Patterns.WEAPON.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.WEAPON, path);
                response.getWriter().write(jsonb.toJson(weaponController.getWeapon(uuid)));
                return;
            }
            // weapon type
            else if (path.matches(Patterns.WEAPON_TYPES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(weaponTypeController.getWeaponTypes()));
                return;
            }
            else if (path.matches(Patterns.WEAPON_TYPE.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.WEAPON_TYPE, path);
                response.getWriter().write(jsonb.toJson(weaponTypeController.getWeaponType(uuid)));
                return;
            }
            // other
            else if (path.matches(Patterns.PLAYER_WEAPONS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PLAYER_WEAPONS, path);
                response.getWriter().write(jsonb.toJson(weaponController.getPlayerWeapons(uuid)));
                return;
            }
            else if (path.matches(Patterns.WEAPON_TYPE_WEAPONS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.WEAPON_TYPE_WEAPONS, path);
                response.getWriter().write(jsonb.toJson(weaponController.getWeaponTypeWeapons(uuid)));
                return;
            }

        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            // player
            if (path.matches(Patterns.PLAYER.pattern())) {
                UUID uuid = extractUuid(Patterns.PLAYER, path);
                playerController.putPlayer(uuid, jsonb.fromJson(request.getReader(), PutPlayerRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "characters", uuid.toString()));
                return;
            } else if (path.matches(Patterns.PLAYER_PICTURE.pattern())) {
                UUID uuid = extractUuid(Patterns.PLAYER_PICTURE, path);
                playerController.putPicture(uuid, request.getPart("picture").getInputStream());
                return;
            }
            // weapon
            else if (path.matches(Patterns.WEAPON.pattern())) {
                UUID uuid = extractUuid(Patterns.WEAPON, path);
                weaponController.putWeapon(uuid, jsonb.fromJson(request.getReader(), PutWeaponRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "weapons", uuid.toString()));
                return;
            }
            // weapon Type
            else if (path.matches(Patterns.WEAPON_TYPE.pattern())) {
                UUID uuid = extractUuid(Patterns.WEAPON_TYPE, path);
                weaponTypeController.putWeaponType(uuid, jsonb.fromJson(request.getReader(), PutWeaponTypeRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "weapon_types", uuid.toString()));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }


    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            // PLayer
            if (path.matches(Patterns.PLAYER.pattern())) {
                UUID uuid = extractUuid(Patterns.PLAYER, path);
                playerController.patchPlayer(uuid, jsonb.fromJson(request.getReader(), PatchPlayerRequest.class));
                return;
            }
            // Weapon
            else if (path.matches(Patterns.WEAPON.pattern())) {
                UUID uuid = extractUuid(Patterns.WEAPON, path);
                weaponController.patchWeapon(uuid, jsonb.fromJson(request.getReader(), PatchWeaponRequest.class));
                return;
            }
            // Weapon Type
            else if (path.matches(Patterns.WEAPON_TYPE.pattern())) {
                UUID uuid = extractUuid(Patterns.WEAPON_TYPE, path);
                weaponTypeController.patchWeaponType(uuid, jsonb.fromJson(request.getReader(), PatchWeaponTypeRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);

    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            // Player
            if (path.matches(Patterns.PLAYER.pattern())) {
                UUID uuid = extractUuid(Patterns.PLAYER, path);
                playerController.deletePlayer(uuid);
                return;
            }
            else if (path.matches(Patterns.PLAYER_PICTURE.pattern())) {
                UUID uuid = extractUuid(Patterns.PLAYER_PICTURE, path);
                playerController.deletePicture(uuid);
                return;
            }
            // Weapon
            else if (path.matches(Patterns.WEAPON.pattern())) {
                UUID uuid = extractUuid(Patterns.WEAPON, path);
                weaponController.deleteWeapon(uuid);
                return;
            }
            // Weapon Type
            else if (path.matches(Patterns.WEAPON_TYPE.pattern())) {
                UUID uuid = extractUuid(Patterns.WEAPON_TYPE, path);
                weaponTypeController.deleteWeaponType(uuid);
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Extracts UUID from path using provided pattern. Pattern needs to contain UUID in first regular expression group.
     */
    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    /**
     * Gets path info from the request and returns it. No null is returned, instead empty string is used.
     */
    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    /**
     * Creates URL using host, port and context root from servlet request and any number of path elements. If any of
     * path elements starts or ends with '/' character, that character is removed.
     */
    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

}
