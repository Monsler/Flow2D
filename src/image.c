#pragma once
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
#include <raylib.h>
#include <stdlib.h>

/**
 * This file is a part of Flow2D
 * @author https://github.com/Monsler
 */

 typedef struct {
    Texture2D texture;
} TextureWrapper;

int read_image_flow(lua_State* L) {
    const char* file_path = luaL_checkstring(L, 1);
    TextureWrapper* wrapper = (TextureWrapper*)malloc(sizeof(TextureWrapper));
    wrapper->texture = LoadTexture(file_path);
    lua_pushlightuserdata(L, (void*)wrapper);
    return 1;
}

void init_image_lib(lua_State* L) {
    lua_newtable(L);
    lua_pushcfunction(L, read_image_flow);
    lua_setfield(L, -2, "read");

    lua_setglobal(L, "image");
}