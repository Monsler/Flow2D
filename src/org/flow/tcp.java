package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class tcp extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        LuaValue lib = tableOf();
        lib.set("connectTo", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
                System.out.println("Connecting to "+luaValue.tojstring()+":"+luaValue1.tojstring());
                LuaValue lib_ = tableOf();
                Socket client = null;
                try {
                    client = new Socket(luaValue.tojstring(), luaValue1.toint());
                    Thread.sleep(500);
                    if (client.isConnected()) {
                        System.out.println("Connected to "+luaValue.tojstring()+":"+luaValue1.tojstring());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                Socket finalClient = client;
                lib_.set("read", new ZeroArgFunction() {
                    @Override
                    public LuaValue call() {
                        InputStream is;
                        try {
                            is = finalClient.getInputStream();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        byte[] bytes;
                        try {
                            bytes = new byte[is.available()];
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            is.read(bytes);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return valueOf( new String(bytes, StandardCharsets.UTF_8));
                    }
                });

                Socket finalClient2 = client;
                lib_.set("write", new OneArgFunction() {
                    @Override
                    public LuaValue call(LuaValue luaValue) {
                        OutputStream outToServer;
                        try {
                            outToServer = finalClient2.getOutputStream();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            outToServer.write(luaValue.tojstring().getBytes());
                            outToServer.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return null;
                    }
                });

                Socket finalClient1 = client;
                lib_.set("close", new ZeroArgFunction() {
                    @Override
                    public LuaValue call() {
                        try {
                            finalClient1.close();
                        } catch (IOException e) {
                            System.exit(200);
                        }
                        return null;
                    }
                });

                return lib_;
            }
        });
        return lib;
    }
}
