#pragma once
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
#include <raylib.h>

/**
 * This file is a part of Flow2D
 * @author https://github.com/Monsler
 */
int set_title_flow(lua_State* L) {
    const char* title = luaL_checkstring(L, 1);
    SetWindowTitle(title);
    return 1;
}

int is_key_down_flow(lua_State* L) {
    int key_num = luaL_checkinteger(L, 1);
    lua_pushboolean(L, IsKeyDown(key_num));
}

int is_key_pressed_flow(lua_State* L) {
    int key_num = luaL_checkinteger(L, 1);
    lua_pushboolean(L, IsKeyPressed(key_num));
}

int get_pressedkey_flow(lua_State* L) {
    char key = GetCharPressed();
    lua_pushstring(L, &key);
}

int set_fps_cap_flow(lua_State* L) {
    int fps = luaL_checkinteger(L, 1);
    SetTargetFPS(fps);
    return 1;
}

int set_icon_flow(lua_State* L) {
    const char* pth = luaL_checkstring(L, 1);
    Image img = LoadImage(pth);
    SetWindowIcon(img);
}

void init_system_lib(lua_State* L) {
    lua_newtable(L);
    lua_pushcfunction(L, set_title_flow);
    lua_setfield(L, -2, "setWindowTitle");
    lua_pushcfunction(L, set_fps_cap_flow);
    lua_setfield(L, -2, "setFpsCap");
    lua_pushcfunction(L, set_icon_flow);
    lua_setfield(L, -2, "setWindowIcon");
    lua_pushcfunction(L, get_pressedkey_flow);
    lua_setfield(L, -2, "getPressedKey");
    lua_pushcfunction(L, is_key_pressed_flow);
    lua_setfield(L, -2, "isKeyPressed");
    lua_pushcfunction(L, is_key_down_flow);
    lua_setfield(L, -2, "isKeyDown");
    lua_setglobal(L, "system");
}