#pragma once
#include <raylib.h>
#include "icon.h"
#include <lua.h>

/**
 * This file is a part of Flow2D
 * @author https://github.com/Monsler
 */
void initSimulator() {
    InitWindow(500, 400, "Flow2D Simulator");
    unsigned int iconSize = sizeof(src_icon_ico);
    Image icon = LoadImageFromMemory(".ico", src_icon_ico, iconSize);
    SetWindowIcon(icon);
    while (!WindowShouldClose()) {
        BeginDrawing();
        DrawRectangle(0, 0, 200, 200, RED);
        EndDrawing();
    }

    CloseWindow();
}