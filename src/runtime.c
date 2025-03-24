#pragma once
#include <raylib.h>
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
#include <string.h>
#include <ctype.h>
#include "system.c"
#include "image.c"

/**
 * This file is a part of Flow2D
 * @author https://github.com/Monsler
 */
Color current_color = RED;

int fill_rect_flow(lua_State* L) {
    int x = luaL_checkinteger(L, 1);
    int y = luaL_checkinteger(L, 2);
    int width = luaL_checkinteger(L, 3);
    int height = luaL_checkinteger(L, 4);
    DrawRectangle(x, y, width, height, current_color);
    return 1;
}

int draw_fps_flow(lua_State* L) {
    int x = luaL_checkinteger(L, 1);
    int y = luaL_checkinteger(L, 2);
    DrawFPS(x, y);
}

int h2d(char c) {
    if ( c >= '0' && c <= '9' ) return c - '0';
    if ( c >= 'A' && c <= 'F' ) return c - 'A' + 10;
    if ( c >= 'a' && c <= 'f' ) return c - 'a' + 10;
    return 0;
}

Color hex_to_rgb(const char* s) {
    Color c = {0, 0, 0, 255};
    if (strlen(s) == 8) {
        c.r = (h2d(s[0])<<4)+h2d(s[1]);
        c.g = (h2d(s[2])<<4)+h2d(s[3]);
        c.b = (h2d(s[4])<<4)+h2d(s[5]);
        c.a = (h2d(s[6])<<4)+h2d(s[7]);
    }
    return c;
}

int set_color_flow(lua_State* L) {
    int nargs = lua_gettop(L);
    const char* hex = luaL_checkstring(L, 1);
    Color c = hex_to_rgb(hex);
    current_color = c;
    return 0;
}

void init_simulator() {
    InitWindow(500, 400, "Flow2D Simulator");
    lua_State *L = luaL_newstate();
    luaL_openlibs(L);
    lua_newtable(L);
    lua_setglobal(L, "flow");
    lua_newtable(L);
    lua_pushcfunction(L, fill_rect_flow);
    lua_setfield(L, -2, "fillRect");
    lua_pushcfunction(L, set_color_flow);
    lua_setfield(L, -2, "setColor");
    lua_pushcfunction(L, draw_fps_flow);
    lua_setfield(L, -2, "drawFps");
    lua_setglobal(L, "graphics");
    init_system_lib(L);

    if (luaL_dofile(L, "main.lua") != LUA_OK) {
        printf("Error: %s\n", lua_tostring(L, -1));
        lua_pop(L, 1);
    }

    lua_getglobal(L, "flow");
    lua_getfield(L, -1, "start");
    if (lua_pcall(L, 0, 0, 0) != LUA_OK) {
        printf("Lua error: %s\n", lua_tostring(L, -1));
        lua_pop(L, 1);
    }

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(BLACK);
        lua_getglobal(L, "flow");
        lua_getfield(L, -1, "draw");
        if (lua_pcall(L, 0, 0, 0) != LUA_OK) {
            printf("Lua error: %s\n", lua_tostring(L, -1));
            lua_pop(L, 1);
        }
        EndDrawing();
    }
    CloseWindow();
}